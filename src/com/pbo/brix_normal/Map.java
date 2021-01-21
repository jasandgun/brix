package com.pbo.brix_normal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import com.pbo.brix_util.Commons;

public class Map {

	//Fields
	private int [][] theMap;
	private int secondsPassed = Commons.timeLimit;
	private int brickHeight, brickWidth;
	public final int paddleHOR = 80, paddleVER = 50;
	boolean hasil = false;
	public static Timer timer;
	public static boolean timeout = true, timerStarted = false;
	
	
	public Map(int row, int col, int x) {
		if(x == 1) {
			initMap1(row, col);
		}
		else if (x == 2) {
			initMap2(row,col);
		}
		else if (x == 3) {
			initMap3(row,col);
		}
		else if (x == 4){
			initMap4(row,col);
		}
		else if (x == 5) {
			initMap5(row,col);
			timeout = false;
		}
		
		
		brickWidth = (Commons.WIDTH - 2 * paddleHOR) / col;
		brickHeight = (Commons.HEIGHT/3 - paddleVER) / row;
	}
	
	TimerTask task = new TimerTask() {
		public void run() {
			timerStarted = true;
			HUD.seconds = secondsPassed;
			secondsPassed--;
		}
	};
	
    class RemindTask extends TimerTask {
        public void run() {
        	timeout = true;
            timer.cancel(); //Terminate the timer thread
            
        }
    }
    
    public void startTimer() {
    	timer = new Timer();
        timer.schedule(new RemindTask(), Commons.timeLimit*1000);
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    
    public static void stopTimer() {
    	timer.cancel();
    	timerStarted = false;
    }
    
	public void initMap1(int row, int col) {
		theMap = new int[row][col];
		for(int i=0; i<theMap.length; i++) {
			for(int j=0;j<theMap[0].length; j++) {
				int r = 1;
				theMap[i][j] = r;
			}
		}
	}
	
	public void initMap2(int row, int col) {
		theMap = new int[row][col];
		for(int i=0; i<theMap.length; i++) {
			for(int j=0;j<theMap[0].length; j++) {
				int r = (int)(Math.random() * 3 + 1);
				theMap[i][j] = r;
			}
		}
	}
	
	public void initMap3(int row, int col) {
		theMap = new int[row][col];
		for(int i=0; i<theMap.length; i++) {
			for(int j=0;j<theMap[0].length; j++) {
				int r = (int)(Math.random() * 3 + 1);
				theMap[i][j] = r;
			}
		}
		theMap[2][2] = 4;
		theMap[3][4] = 5;
		theMap[3][1] = 6;
		theMap[1][3] = 7;
		theMap[5][4] = 8;
	}
	
	public void initMap4(int row, int col) {
		theMap = new int[row][col];
		for(int i=0; i<theMap.length; i++) {
			for(int j=0;j<theMap[0].length; j++) {
				int r = (int)(Math.random() * 3 + 1);
				theMap[i][j] = r;
			}
		}
		theMap[3][2] = 4;
		theMap[2][4] = 5;
		theMap[4][1] = 6;
		theMap[2][1] = 7;
		theMap[5][3] = 8;
	}
	
	public void initMap5(int row, int col) {
		theMap = new int[row][col];
		for(int i=0; i<theMap.length; i++) {
			for(int j=0;j<theMap[0].length; j++) {
				int r = (int)(Math.random() * 3 + 1);
				theMap[i][j] = r;
			}
		}
		theMap[2][3] = 4;
		theMap[4][2] = 5;
		theMap[1][4] = 6;
		theMap[1][3] = 7;
		theMap[5][4] = 8;
	}
	
	public void draw(Graphics2D g) {
		for(int row = 0; row < theMap.length; row++) {
			for(int col = 0; col < theMap[0].length;col++) {
				if(theMap[row][col] > 0) {
					if(theMap[row][col] == 1) {
						g.setColor(new Color(0, 200, 200));
					}
					if(theMap[row][col] == 2) {
						g.setColor(new Color(0, 150, 150));
					}
					if(theMap[row][col] == 3) {
						g.setColor(new Color(0, 100, 100));
					}
					if(theMap[row][col] == Skill.widePaddle) {
						g.setColor(Skill.wideColor);
					}
					if(theMap[row][col] == Skill.narrowPaddle) {
						g.setColor(Skill.narrowColor);
					}
					if(theMap[row][col] == Skill.fastBall) {
						g.setColor(Skill.fastColor);
					}
					if(theMap[row][col] == Skill.extraLife) {
						g.setColor(Skill.lifeColor);
					}
					if(theMap[row][col] == Skill.multiplier) {
						g.setColor(Skill.multiColor);
					}
					g.fillRect(col * (brickWidth+5) + paddleHOR , row * (brickHeight+5) + paddleVER, brickWidth, brickHeight);
					g.setStroke(new BasicStroke(2));
					g.drawRect(col * (brickWidth+5) + paddleHOR , row * (brickHeight+5) + paddleVER, brickWidth , brickHeight);
					
				}
			}
		}
	}
	
	public boolean result() {
		int bricksRemaining = 0;
		for(int row = 0; row < theMap.length; row++) {
			for(int col = 0; col < theMap[0].length; col++) {
				bricksRemaining += theMap[row][col];
			}
		}
		if(bricksRemaining == 0) {
			hasil = true;
		}
		return hasil;
	}
	
	public void resetResult() {
		hasil = false;
	}
	
	public int[][] getMapArray(){
		return theMap;
	}
	
	public void setBrick(int row, int col, int value) {
		theMap[row][col] = value;
	}
	
	public int getBrickWidth() {
		return brickWidth;
	}
	
	public int getBrickHeight() {
		return brickHeight;
	}
	
	public void hitBrick(int row, int col) {
		theMap[row][col] -= 1;
		if(theMap[row][col]<0) {
			theMap[row][col] = 0;
		}
	}
	
}
