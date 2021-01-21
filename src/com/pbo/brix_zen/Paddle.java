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
	private int width, height;
	public final int positionY = Commons.HEIGHT - 100;
	
	//konstruktor
	public Paddle(int width, int height) {
		this.width=width;
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
	
}