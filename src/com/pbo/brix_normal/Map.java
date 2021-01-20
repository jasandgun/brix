package com.pbo.brix_normal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.pbo.brix_util.Commons;

public class Map {

	//Fields
	private int [][] theMap;
	private int brickHeight, brickWidth;
	public final int paddleHOR = 80, paddleVER = 50;
	boolean hasil = false;
	
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
		
		brickWidth = (Commons.WIDTH - 2 * paddleHOR) / col;
		brickHeight = (Commons.HEIGHT/3 - paddleVER) / row;
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
		theMap[5][2] = 4;
		theMap[5][4] = 5;
		theMap[5][1] = 6;
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

