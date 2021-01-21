package com.pbo.brix_normal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.pbo.brix_util.Commons;

public class Skill {
	
	//Fields
	private int x,y,directionY, type, width, height;
	private boolean isOnScreen; 
	private boolean wasUsed;
	private Color color;
	
	public final static int widePaddle = 4;
	public final static int narrowPaddle = 5;
	public final static int fastBall = 6;
	public final static int extraLife = 7;
	public final static int multiplier = 8;
	public final static  Color wideColor = Color.GREEN;
	public final static Color narrowColor = Color.PINK;
	public final static Color fastColor = Color.RED;
	public final static Color lifeColor = Color.BLUE;
	public final static Color multiColor = Color.YELLOW;
	
	//konstruktor
	public Skill(int x, int y, int type, int width, int height) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.width = width;
		this.height = height;
		
		if(type < 4) {
			type = 4;
		}
		if(type > 8) {
			type = 8;
		}
		if(type == widePaddle) {
			color = wideColor;
		}
		if(type == narrowPaddle) {
			color = narrowColor;
		}

		if(type == fastBall) { 
			color = fastColor; 
		}
		if(type == extraLife) { 
			color = lifeColor; 
		}
		if(type == multiplier) { 
			color = multiColor; 
		}
		directionY = (int)(Math.random()*6+1);
		
		setWasUsed(false);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public void Update() {
		y += directionY;
		if(y > Commons.HEIGHT) {
			isOnScreen = false;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirectionY() {
		return directionY;
	}

	public void setDirectionY(int directionY) {
		this.directionY = directionY;
	}

	public int getType() {
		return type;
	}

	public boolean getIsOnScreen() {
		return isOnScreen;
	}

	public void setIsOnScreen(boolean isOnScreen) {
		this.isOnScreen = isOnScreen;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,width,height);
	}

	public boolean getWasUsed() {
		return wasUsed;
	}

	public void setWasUsed(boolean wasUsed) {
		this.wasUsed = wasUsed;
	}


	
}
