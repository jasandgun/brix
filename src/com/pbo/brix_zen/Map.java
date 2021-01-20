package com.pbo.brix_zen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.pbo.brix_util.Commons;

public class Map {

	//Fields
	private int [][] theMap;
	private int brickHeight, brickWidth;
	private boolean hasil = false;
	public final int paddleHOR = 80, paddleVER = 50;
	
	public Map(int row, int col) {
		initMap(row, col);
		brickWidth = (Commons.WIDTH - 2 * paddleHOR) / col;
		brickHeight = (Commons.HEIGHT/3 - paddleVER) / row;
	}
	
	public void initMap(int row, int col) {
		theMap = new int[row][col];
		for(int i=0; i<theMap.length; i++) {
			for(int j=0;j<theMap[0].length; j++) {
				theMap[i][j] = 1;
			}
		}
	}
	
	public void draw(Graphics2D g) {
		for(int row = 0; row < theMap.length; row++) {
			for(int col = 0; col < theMap[0].length;col++) {
				if(theMap[row][col] > 0) {
					if(theMap[row][col] == 1) {
						g.setColor(new Color(2, 75, 48));
					}
					g.fillRect(col * (brickWidth+5) + paddleHOR, row * (brickHeight+5) + paddleVER, brickWidth, brickHeight);
					g.setStroke(new BasicStroke(2));
					g.drawRect(col * (brickWidth+5) + paddleHOR, row * (brickHeight+5) + paddleVER, brickWidth, brickHeight);
					
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