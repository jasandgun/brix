package com.pbo.brix_normal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.pbo.brix_normal.Map.RemindTask;
import com.pbo.brix_ui.BRIX;
import com.pbo.brix_ui.CreditsPanel;
import com.pbo.brix_ui.LevelSelectPanel;
import com.pbo.brix_ui.NormalPanel;
import com.pbo.brix_util.Background;
import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;

public class NormalGame extends JPanel {
	
	//Fields
	public static boolean running;
	private boolean started, localRun;
	private BufferedImage image;
	private Graphics2D g;
	private int currLevel;
	
	//entitas
	private Ball Bola;
	private Paddle Alas;
	private Map theMap;
	private HUD theHUD;
	private ArrayList<Skill> powerUp;
	private ArrayList<Explosion> explosions;
	
	//design
	private boolean screenShakeActive;
	private long screenShakeTimer;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	private Background bg;
	
	//konstruktor
	public NormalGame(int currLevel) {
		this.currLevel = currLevel;
		HUD.currId = currLevel - 1;
		init();
	}
	
	public void init() {
		Bola = new Ball();
		Alas = new Paddle(Commons.paddleWidth, Commons.paddleHeight);
		theMap = new Map(Commons.mapRow, Commons.mapCol, currLevel);
		theHUD = new HUD();
		
		screenShakeTimer = System.nanoTime();
		powerUp = new ArrayList<Skill>();
		explosions = new ArrayList<Explosion>();
        
		//boolean
		running = true; localRun = true;
		started = false;
		image = new BufferedImage(Commons.WIDTH, Commons.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bg = new Background(Commons.normalBGPath, .1);
	}

	private class MoveAction extends AbstractAction{
		int code;
		
		MoveAction(int code){
			this.code = code;
		}
		
		public void actionPerformed(ActionEvent e) {
			//start
			if(!started && this.code == 0) {
				if(currLevel == 5) {
					theMap.startTimer();
				}
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
				while (running && localRun) {
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
		
		//Power-Up Collisions
		for(int i = 0;i<powerUp.size();i++) {
			Rectangle puRect = powerUp.get(i).getRect();
			if(paddleRect.intersects(puRect)) {
				if(powerUp.get(i).getType() == Skill.widePaddle && !powerUp.get(i).getWasUsed()) {
					Alas.setWidth(Alas.getWidth() * 2);
					powerUp.get(i).setWasUsed(true);
				}
				if(powerUp.get(i).getType() == Skill.narrowPaddle && !powerUp.get(i).getWasUsed()) {
					Alas.shrinkWidth();
					powerUp.get(i).setWasUsed(true);
				}
				if(powerUp.get(i).getType() == Skill.fastBall && !powerUp.get(i).getWasUsed()) {
					Bola.fastBall();
					powerUp.get(i).setWasUsed(true);
				}
				if(powerUp.get(i).getType() == Skill.extraLife && !powerUp.get(i).getWasUsed()) {
					theHUD.extraLive();
					powerUp.get(i).setWasUsed(true);
				}
			}
		}
		
		//Paddle Collisions
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
						
						if(theMap.getMapArray()[row][col] > 3) {
							powerUp.add(new Skill(brickX, brickY,theMap.getMapArray()[row][col], brickWidth, brickHeight));
							theMap.setBrick(row, col, 3);
						}
						else {
							theMap.hitBrick(row, col);
						}
						
						theMap.hitBrick(row, col);
						Bola.setDY(-Bola.getDY());
						theHUD.addScore(50);
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
    	
		for(Skill pu : powerUp) {
			pu.Update();
		}
		
		if((System.nanoTime() - screenShakeTimer) / 1000000 > 300 && screenShakeActive) {
			screenShakeActive = false;
		}
		
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			
			if(!explosions.get(i).getIsActive()) {
				explosions.remove(i);
			}
		}
		
		// Win Condition
		if (theMap.result() == true) {
			theMap.resetResult();
			LevelSelectPanel.updateHS();
			currLevel++;
			localRun = false;
			winOptions();
		}
		// Lose Condition
		if(Bola.isFall()) {
			theHUD.lostLives();
			Bola.resetBola();
			Alas.resetPaddle();
			started = false;
			if(theHUD.getLives() == 0) {
				LevelSelectPanel.updateHS();
				localRun = false;
				loseOptions();
			}
		}
		if(Map.timeout && currLevel == 5) {
			LevelSelectPanel.updateHS();
			localRun = false; Map.timeout = false;
			loseOptions();
		}
		
	}
	
	private void winOptions() {
		if(currLevel <= 5) {
			int choose = JOptionPane.showConfirmDialog(null, 
					"Your score: " + HUD.lastScore + "\n"
					+ "Continue to level " + (currLevel - 1) + "?" ,"WELL DONE!", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			Commons.normalMusic.stopMusic();
			if(choose == JOptionPane.YES_OPTION) {
				NormalPanel.layargame = new NormalGame(currLevel);
	        	NormalPanel.layargame.playGame().start();
	        	BRIX.main_frame.setContentPane(new NormalPanel());
			} else {
	        	BRIX.main_frame.setContentPane(new LevelSelectPanel());
			}
			BRIX.main_frame.pack();
		} else {
			JLabel label = new JLabel(Commons.finalText);
			label.setHorizontalAlignment(SwingConstants.CENTER);
        	JOptionPane.showMessageDialog(BRIX.main_frame, label,
        			"thanks", JOptionPane.PLAIN_MESSAGE);
        	Commons.normalMusic.stopMusic();
        	BRIX.main_frame.setContentPane(new CreditsPanel());
        	BRIX.main_frame.pack();
		}
		return;
	}
	
	private void loseOptions() {
		Commons.normalMusic.stopMusic();
		int choose = JOptionPane.showConfirmDialog(null, 
				"Your score: " + HUD.lastScore + "\n" + 
				"You've lost, retry?" ,"LOSE", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if(choose == JOptionPane.YES_OPTION) {
			NormalPanel.layargame = new NormalGame(currLevel);
        	NormalPanel.layargame.playGame().start();
        	BRIX.main_frame.setContentPane(new NormalPanel());
		} else {
        	BRIX.main_frame.setContentPane(new LevelSelectPanel());
		}
		
		BRIX.main_frame.pack();
	}
	
	public void draw() {
		//background
		g.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
		bg.draw(g);
		theMap.draw(g);
		Bola.draw(g);
		Alas.draw(g);
		theHUD.draw(g);
		drawSkill();
		drawLevel();

		for(Explosion bs : explosions) {
			bs.draw(g);
		}
	}
	
	public void drawLevel() {
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("LEVEL " +(currLevel-1), 175, 40);
	}
	
	public void drawSkill() {
		for(Skill pu : powerUp) {
			pu.draw(g);
		}
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
