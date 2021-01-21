package com.pbo.brix_normal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;

public class HUD {

	//Fields
	private int score, lives;
	private long scoreTimer, livesTimer;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	public static int lastScore = 0, currId = 0, seconds;
	
	//konstruktor
	public HUD() {
		init();
	}
	
	public void init() {
		score = 0;
		if (currId == 3) 
			lives = 1;
		else 
			lives = 3;
		lastScore = 0;
	}
	
	public void draw(Graphics2D g) {
		g.setFont(font.deriveFont(20f));
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 20, 20);
		g.drawString("Lives: " +lives, 20, 40);
		if(currId + 1 == 5) {
			g.drawString("Time Left: " +seconds, 370, 20);
		}
	}
	
	public void bonusScore() {
		if(lives > 0) {
			score += lives * 100;
		}
		if(seconds > 0 && currId == 4) {
			score += seconds * 20;
		}
		lastScore = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int nilai) {
		score += nilai;
		lastScore = score;
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void lostLives() {
		--lives;
	}
	
	public void extraLive() {
		++lives;
		setLivesTimer();
	}
	
	public void Multiplier() {
		score *= (int)(Math.random() * 10);
		setScoreTimer();
	}
	
	public void setScoreTimer() {
		scoreTimer = System.nanoTime();
	}

	public long getScoreTimer() {
		return scoreTimer;
	}

	public long getLivesTimer() {
		return livesTimer;
	}

	public void setLivesTimer() {
		livesTimer = System.nanoTime();
	}
}
