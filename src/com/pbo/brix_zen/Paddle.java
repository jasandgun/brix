package com.pbo.brix_zen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.pbo.brix_util.Commons;

public class Paddle extends JLabel{
	//Fields
	private double x;
	private int width, height, startWidth;
	private long widthTimer;
	private boolean altWidth;
	public final int positionY = Commons.HEIGHT - 100;
	
	//konstruktor
	public Paddle(int width, int height) {
		altWidth = false;
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
			altWidth = false;
		}
	}
	
	//draw
	public void draw(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int)x, positionY, width, height);
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, positionY, width, height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getX(){
		return (int)x;
	}
	
	public void setX(int i){
		x = i;
	}

	public void resetPaddle() { 
		x = (Commons.WIDTH - width)/2;
	}
	
	public void setWidthTimer() {
		widthTimer = System.nanoTime();
	}
	
}