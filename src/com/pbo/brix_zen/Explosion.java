package com.pbo.brix_zen;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Explosion {
	
	//Fields
	private ArrayList<BrickPiece> piece;
	private int x,y;
	private Map theMap;
	private boolean isActive;
	private long startTime;
	
	//konstruktor
	public Explosion(int x, int y, Map theMap) {
		this.x = x;
		this.y = y;
		this.theMap = theMap;
		isActive = true;
		startTime = System.nanoTime();
		piece = new ArrayList<BrickPiece>();
		init();
	}
	
	public void init() {
		int randNum = (int)(Math.random() * 20 + 5);
		
		for(int i=0; i < randNum; i++) {
			piece.add(new BrickPiece(x, y, theMap));
		}
	}
	
	public void update() {
		for(BrickPiece bp : piece) {
			bp.update();
		}
		
		if((System.nanoTime() - startTime) / 1000000 > 2000 && isActive) {
			isActive = false;
		}
	}
	
	public void draw(Graphics2D g) {
		for(BrickPiece bp : piece) {
			bp.draw(g);
		}
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
}