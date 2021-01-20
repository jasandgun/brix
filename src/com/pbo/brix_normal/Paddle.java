package com.pbo.brix_normal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;

public class Paddle {
	//Fields
	private double x;
	private int width, height, startWidth;
	private long widthTimer;
	private boolean altWidthShrink, altWidthWide;
	public final int positionY = Commons.HEIGHT - 100;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	
	//konstruktor
	public Paddle(int width, int height) {
		altWidthShrink = false;
		altWidthWide = false;
		this.width=width;
		startWidth = width;
		this.height=height;
		x = (Commons.WIDTH - width)/2;
	}
	
	public void move(int code){
		
		if(code == 2){
 			if(this.getX() > 0) {
                this.setX(this.getX() - 7);
 			}
        }
        if(code == 1){
        	if(this.getX() + 60 < 500) {
            	this.setX(this.getX() + 7);
            }
        }

	}
	
	//update
	public void update () {
		if((System.nanoTime() - widthTimer) / 1000 > 4000000) {
			width = startWidth;
			altWidthShrink = false;
			altWidthWide = false;
		}
	}
	
	//draw
	public void draw(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int)x, positionY, width, height);
		if(altWidthShrink) {
			g.setColor(Color.PINK);
			g.setFont(font.deriveFont(40f));
			g.drawString("SHRINK PADDLE", 130, 360);
		}
		if(altWidthWide) {
			g.setColor(Color.GREEN);
			g.setFont(font.deriveFont(40f));
			g.drawString("WIDE PADDLE", 135, 450);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, positionY, width, height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		altWidthWide = true;
		this.width = width;
		setWidthTimer();
	}
	
	public void shrinkWidth() { 
		altWidthShrink = true;
		width = width / 2; 
		setWidthTimer();
	}
	
	
	public int getX(){
		return (int)x;
	}
	
	public void setX(int i){
		x = i;
	}
	
//	public void resetPaddle() {
//		altWidth = false;
//		this.width=width;
//		startWidth = width;
//		this.height=height;
//		x = (Main.WIDTH - width)/2;
//	}


	public void setWidthTimer() {
		widthTimer = System.nanoTime();
	}
	
}