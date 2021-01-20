package com.pbo.brix_zen;

import java.awt.Color;
import java.awt.Graphics2D;

public class BrickPiece {
	
	//Fields
	private int x,y;
	private double dx,dy,gravity;
	private Color color;
	private int size;
	
	//konstruktor
	public BrickPiece(int x, int y, Map theMap) {
		this.x = x + theMap.getBrickWidth() / 2;
		this.y = y + theMap.getBrickHeight() / 2;
		dx = (Math.random() * 30 - 15);
		dy = (Math.random() * 30 - 15);
		size = (int)(Math.random() * 15 + 2);
		color = new Color(2, 75, 48);
		gravity = .8;
	}
	
	public void update() {
		x += dx;
		y += dy;
		dy += gravity;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, size, size);
		
	}
	
	
}