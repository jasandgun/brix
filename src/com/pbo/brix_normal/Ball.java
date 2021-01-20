package com.pbo.brix_normal;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;

public class Ball {

	//Fields
	private double x, y, directionX, directionY;
	private int ballSize = 30;
	private long speedTimer;
	private boolean altSpeed;
	private ArrayList<AlphaComposite> alphas;
	private int tailLength = 10;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	
	public Ball() {
		x = 235;
		y = Commons.HEIGHT - (130);
	}
	
	public void update() {
		if((System.nanoTime() - speedTimer) / 1000 > 2000000) {
			if(altSpeed) {
				altSpeed = false;
				directionY = directionY/2;
			}
		}
		setPosition();
	}
	
	public void initTail() {
		float alpha = 1f;
		AlphaComposite alcom;
		
		for(int i = 0; i < tailLength; i++) {
			double alphaD = i * (100 / tailLength);
			alpha = (float) alphaD / 100;
	        alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	        alphas.add(alcom);
		}
	}
	
	public void fastBall() { 
		altSpeed = true;
		directionY = directionY * 2;
		setWidthTimer();
	}
	
	public void setPosition() {
		x += directionX;
		y += directionY;
		if(x<0) {
			directionX = -directionX;
		}
		if(y<0) {
			directionY = -directionY;
		}
		if(x>Commons.WIDTH - ballSize) {
			directionX = -directionX;
		}
		if(y>Commons.HEIGHT - ballSize) {
			directionY = -directionY;
		}
	}
	
	public void move(){
		this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
	}
	
	
	public void draw(Graphics2D g) {
		if(altSpeed) {
			g.setColor(Color.RED);
			g.setFont(font.deriveFont(40f));
			g.drawString("FAST BALL", 140, 420);
			g.setStroke(new BasicStroke(4));
			g.fillOval((int)x, (int)y, ballSize, ballSize);
			
		}
		else {
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(4));
			g.fillOval((int)x, (int)y, ballSize, ballSize);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, ballSize, ballSize);
	}
	
	public void setDY(double dY) {
		directionY = dY;
	}
	
	public double getDY() {
		return directionY;
	}
	
	public void setDX(double dX) {
		directionX = dX;
	}
	
	public double getDX() {
		return directionX;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void resetBola() {
		x = 235;
		y = Commons.HEIGHT - (130);
		directionX = 0;
		directionY = 0;
	}
	
	public boolean isFall() {
		boolean fall = false;
		if(y > Commons.HEIGHT - 100 + Commons.paddleHeight/2 +1) {
			fall = true;
		}
		return fall;
	}
	
	public void setWidthTimer() {
		speedTimer = System.nanoTime();
	}
	
}