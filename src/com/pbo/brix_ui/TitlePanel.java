package com.pbo.brix_ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.pbo.brix_util.ButtonMaker;
import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;
import com.pbo.brix_zen.ZenGame;


public class TitlePanel extends JPanel{
	
	private Image imSrc;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	
	public TitlePanel ()  {
		
		Commons.titleMusic.playMusic(Commons.titleMusicPath);
		Commons.titleMusic.musicLoop();
		setPreferredSize(Commons.frameSize);
		this.imSrc = new ImageIcon(Commons.titleBGPath).getImage();
		this.setLayout(null);
		
		drawIcon();
		drawTitle();
		normalButton();
		zenButton();
		creditButton();
		exitButton();
		
	}
	
	private void drawIcon() {
		ImageIcon logo = new ImageIcon(Commons.iconPath);
		JLabel logoLabel = new JLabel();
		logoLabel.setIcon(logo);
		logoLabel.setHorizontalAlignment(JLabel.LEFT);
		logoLabel.setBounds(-30, 0, 200, 200);
		this.add(logoLabel);
	}
	
	private void drawTitle() {
		ImageIcon logo = new ImageIcon(Commons.titlePath);
		JLabel logoLabel = new JLabel();
		logoLabel.setIcon(logo);
		logoLabel.setHorizontalAlignment(JLabel.RIGHT);
		logoLabel.setBounds(155,50, 311, 100);
		this.add(logoLabel);
	}
	
	private void normalButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(145, 275, 210, 50);
		JButton button = new ButtonMaker("NORMAL", rect).getButton();
		button.setFont(font);
		// Adding the button to the panel with actionListener
		this.add(button);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//Music change
	        	Commons.titleMusic.stopMusic();
	        	Commons.clickForward.playMusic(Commons.forwardClick);
	        	//Changing panel and start the game
	        	BRIX.main_frame.setContentPane(new LevelSelectPanel());
	        	BRIX.main_frame.pack();
	         }
		});
	}
	
	private void zenButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(175, 350, 150, 50);
		JButton button = new ButtonMaker(Commons.zenButton, rect).getButton();
		button.setFont(font);
		// Adding the button to the panel with actionListener
		this.add(button);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//Music change
	        	Commons.titleMusic.stopMusic();
	        	Commons.clickForward.playMusic(Commons.forwardClick);
//	        	Changing panel and start the game
	        	ZenPanel.layargame = new ZenGame();
	        	ZenPanel.layargame.playGame().start();
	        	BRIX.main_frame.setContentPane(new ZenPanel());
	        	BRIX.main_frame.pack();
	         }
		});
	}
	
	private void creditButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(130, 425, 240, 50);
		JButton button = new ButtonMaker("CREDITS", rect).getButton();
		button.setFont(font);
		// Adding the button to the panel with actionListener
		this.add(button);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Commons.clickForward.playMusic(Commons.forwardClick);
	        	Commons.titleMusic.stopMusic();
	        	Commons.creditsMusic.playMusic(Commons.creditsMusicPath);
	        	BRIX.main_frame.setContentPane(new CreditsPanel());
	        	BRIX.main_frame.pack();
	         }
		});
	}
	
	private void exitButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(15, 600, 115, 45);
		JButton button = new ButtonMaker(Commons.exitButton, rect).getButton();
		button.setFont(font.deriveFont(40f));
		// Adding the button to the panel with actionListener
		this.add(button);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Commons.clickBackward.playMusic(Commons.backwardClick);
	        	int choose = JOptionPane.showConfirmDialog(null, 
						"Exit BRIX?","EXIT", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if(choose == JOptionPane.YES_OPTION) {
					Commons.titleMusic.stopMusic();
					BRIX.main_frame.setVisible(false);
					BRIX.main_frame.dispose();
				}
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
