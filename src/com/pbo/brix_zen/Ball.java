package com.pbo.brix_zen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.pbo.brix_util.Commons;

public class Ball {

	//Fields
	private double x, y, directionX, directionY;
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	private int ballSize = 30;
	
	public Ball() {
		x = 200;
		y = Commons.HEIGHT - (130);
	}
	
	public void update() {
		setPosition();
	}
	
	public void move(){		
		this.setX(this.getX() + this.getDX());
        this.setY(this.getY() + this.getDY());
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
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(3, 125, 80));
		g.setStroke(new BasicStroke(4));
		g.fillOval((int)x, (int)y, ballSize, ballSize);
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
	
}