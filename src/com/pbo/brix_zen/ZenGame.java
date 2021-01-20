package com.pbo.brix_zen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.pbo.brix_util.Background;
import com.pbo.brix_util.Commons;


public class ZenGame extends JPanel{
	
	//Fields
	public static boolean running;
	private boolean started;
	private BufferedImage image;
	private Graphics2D g;
	
	//entitas
	private Ball Bola;
	private Paddle Alas;
	private Map theMap;
	private ArrayList<Explosion> explosions;
	
	private boolean screenShakeActive;
	private long screenShakeTimer;
	
	private Background bg;
	
	//konstruktor
	public ZenGame() {
		init();
	}

	public void init() {
		Bola = new Ball();
		Alas = new Paddle(Commons.paddleWidth, Commons.paddleHeight);
		theMap = new Map(Commons.mapRow, Commons.mapCol);
		setPreferredSize(Commons.frameSize);
		screenShakeTimer = System.nanoTime();
		explosions = new ArrayList<Explosion>();
		
		running = true;
		started = false;
		image = new BufferedImage(Commons.WIDTH, Commons.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bg = new Background(Commons.zenBGPath, .1);

	}

	private class MoveAction extends AbstractAction{
		int code;
		
		MoveAction(int code){
			this.code = code;
		} 
		public void actionPerformed(ActionEvent e) {
			//start
			if(!started && this.code == 0) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
    			Bola.setDX(randomNum);
    			Bola.setDY(-2);
    			started = true;
			} else if(started) {
				Alas.move(this.code);
			}
		}
	}
	
	public Thread playGame() {
		Thread play = new Thread() {
			public void run() {
				while (running) {
					//update
					update();
					
					//draw
					draw();
					
					//display image
					repaint();
					try {
						Thread.sleep(12);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		return play;
	}
	
	public void checkCollisions() {
		Rectangle ballRect = Bola.getRect();
		Rectangle paddleRect = Alas.getRect();
		
		if(ballRect.intersects(paddleRect)) {
			Bola.setDY(-Bola.getDY());
		}
		
		A: for(int row = 0; row<theMap.getMapArray().length; row++) {
			for(int col = 0; col < theMap.getMapArray()[0].length; col++) {
				if(theMap.getMapArray()[row][col]>0) {
					int brickX = col * (theMap.getBrickWidth()+5) + theMap.paddleHOR;
					int brickY = row * (theMap.getBrickHeight()+5) + theMap.paddleVER;
					int brickWidth = theMap.getBrickWidth();
					int brickHeight = theMap.getBrickHeight();
					Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					if(ballRect.intersects(brickRect)) {
						if(theMap.getMapArray()[row][col] == 1) {
							explosions.add(new Explosion(brickX, brickY, theMap));
							screenShakeActive = true;
							screenShakeTimer = System.nanoTime();
						}
						else {
							theMap.hitBrick(row, col);
						}
						
						theMap.hitBrick(row, col);
						Bola.setDY(-Bola.getDY());
						break A;
					}
				}
				
			}
		}
	}
	
	public void update() {
		checkCollisions();
		Bola.update();
		Alas.update();
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), "start");
		this.getActionMap().put("start", new MoveAction(0));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), "move left");
		this.getActionMap().put("move left", new MoveAction(1));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "move right");
		this.getActionMap().put("move right", new MoveAction(2));
				
		if((System.nanoTime() - screenShakeTimer) / 1000000 > 300 && screenShakeActive) {
			screenShakeActive = false;
		}
		
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			
			if(!explosions.get(i).getIsActive()) {
				explosions.remove(i);
			}
		}
		
		if(theMap.result() || Bola.isFall()) {
			if (theMap.result()) {
				theMap.resetResult();
				theMap = new Map(Commons.mapRow, Commons.mapCol);
			}
			Bola.resetBola();
			Alas.resetPaddle();
			started = false;
		}
	}
	
	public void draw() {
		//background	
		g.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
		bg.draw(g);
		theMap.draw(g);
		Bola.draw(g);
		Alas.draw(g);
		
		for(Explosion bs : explosions) {
			bs.draw(g);
		}
	}
	
	public void isEnd() {
		theMap.initMap(Commons.mapRow, Commons.mapCol);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;	
		int x = 0;
		int y = 0;
		
		if(screenShakeActive == true) {
			x=(int)(Math.random() * 10 - 5);
			y=(int)(Math.random() * 10 - 5);
		}
		
		g2.drawImage(image, x, y, Commons.WIDTH, Commons.HEIGHT, null);
		g2.dispose();
	}

	
}