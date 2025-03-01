package com.pbo.brix_ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.pbo.brix_util.ButtonMaker;
import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;
import com.pbo.brix_normal.HUD;
import com.pbo.brix_normal.Map;
import com.pbo.brix_normal.NormalGame;

public class NormalPanel extends JPanel {
	
	private Image imSrc;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	public static NormalGame layargame;

	public NormalPanel() {
    	Commons.normalMusic.playMusic(Commons.normalMusicPath);
    	Commons.normalMusic.musicLoop();
		
		setPreferredSize(Commons.frameSize);
		this.imSrc = new ImageIcon(Commons.normalBGPath).getImage();
		this.setLayout(new BorderLayout());
		
		backButton();
		this.add(layargame);
	}

	private void backButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(10, 10, 75, 50);
		JButton button = new ButtonMaker(Commons.backButton, rect).getButton();
		button.setFont(font);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		// Adding the button to the panel with actionListener
		this.add(button, BorderLayout.NORTH);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(HUD.currId == 4) {
		        	Map.stopTimer();
	        	}
	        	NormalGame.running = false;
	            LevelSelectPanel.updateHS();
	        	Commons.normalMusic.stopMusic();
	        	Commons.clickBackward.playMusic(Commons.backwardClick);
	        	BRIX.main_frame.setContentPane(new LevelSelectPanel());
	        	BRIX.main_frame.pack();
	         }
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	    int x = (this.getWidth() - this.imSrc.getWidth(null)) / 2;
	    int y = (this.getHeight() - this.imSrc.getHeight(null)) / 2;
	    g2d.drawImage(this.imSrc, x, y, null);
	}
	
}