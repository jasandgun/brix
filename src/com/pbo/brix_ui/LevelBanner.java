package com.pbo.brix_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pbo.brix_util.ButtonMaker;
import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;
import com.pbo.brix_normal.HUD;
import com.pbo.brix_normal.NormalGame;

public class LevelBanner {

	private JPanel mainPanel;
	private JButton button;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	private String whichLevel;
	public int id;
	
	public LevelBanner(String whichLevel, int id) {
		this.whichLevel = whichLevel;
		this.id = id;
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(Commons.HEIGHT/6, Commons.WIDTH));
		mainPanel.setLayout(null); mainPanel.setOpaque(false);
		levelButton(); levelScore();
	}
	
	public JPanel getPanel() {
		return mainPanel;
	}
	
	private void levelButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(100, 15, 300, 40);
		this.button = new ButtonMaker(this.whichLevel, rect).getButton();
		this.button.setFont(font.deriveFont(50f));
		// Adding the button
		this.mainPanel.add(this.button);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//Music change
	        	Commons.lvselMusic.stopMusic();
	        	Commons.clickForward.playMusic(Commons.forwardClick);
//	        	Changing panel and start the game
	        	NormalPanel.layargame = new NormalGame(id + 1);
	        	HUD.currId = id;
	        	NormalPanel.layargame.playGame().start();
	        	BRIX.main_frame.setContentPane(new NormalPanel());
	        	BRIX.main_frame.pack();
	         }
		});
	}
	
	private void levelScore() {
		JLabel levelLabel = new JLabel("High Score: " + LevelSelectPanel.scores[this.id]);
		levelLabel.setFont(font.deriveFont(20f));
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setHorizontalAlignment(JLabel.CENTER);
		levelLabel.setBounds(100, 50, 300, 25);
		this.mainPanel.add(levelLabel);
	}

}
