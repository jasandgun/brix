package com.pbo.brix_ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pbo.brix_normal.HUD;
import com.pbo.brix_util.ButtonMaker;
import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;

public class LevelSelectPanel extends JPanel{

	private Image imSrc;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	private JPanel upperPanel;
	public static int[] scores = new int[5];
	private LevelBanner[] banners = new LevelBanner[5];
	
	public LevelSelectPanel() {
		Commons.lvselMusic.playMusic(Commons.lvselMusicPath);
    	Commons.lvselMusic.musicLoop();
		setPreferredSize(Commons.frameSize);
		this.imSrc = new ImageIcon(Commons.lvselBGPath).getImage();
		this.setLayout(new GridLayout(6,1));
		createPanel();
		this.setOpaque(false);
	}
	
	private void createPanel() {
		upperPanel = new JPanel();
		upperPanel.setLayout(null);
		upperPanel.setOpaque(false);
		levelLabel(); exitButton();
		this.add(upperPanel);
		String[] nums = {"zero", "one", "two", "three", "four"};
		for (int i = 0; i < nums.length; i++) {
			banners[i] = new LevelBanner("level " + nums[i], i);
			this.add(banners[i].getPanel());			
		}
	}

	private void levelLabel() {
		JLabel levelLabel = new JLabel("LEVELS");
		levelLabel.setFont(font.deriveFont(75f));
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setHorizontalAlignment(JLabel.CENTER);
		levelLabel.setBounds(125, 15, 250, 75);
		this.upperPanel.add(levelLabel);
	}
	
	private void exitButton() {
		// Creating and customizing the button
		Rectangle rect = new Rectangle(10, 20, 75, 50);
		JButton button = new ButtonMaker(Commons.backButton, rect).getButton();
		button.setFont(font);
		// Adding the button to the panel with actionListener
		this.upperPanel.add(button);
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Commons.clickBackward.playMusic(Commons.backwardClick);
	        	Commons.lvselMusic.stopMusic();
	        	BRIX.main_frame.setContentPane(new TitlePanel());
	        	BRIX.main_frame.pack();
	         }
		});
	}

	public static void updateHS() {
		LevelSelectPanel.scores[HUD.currId] = Math.max(LevelSelectPanel.scores[HUD.currId], HUD.lastScore);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	    int x = (this.getWidth() - this.imSrc.getWidth(null)) / 2;
	    int y = (this.getHeight() - this.imSrc.getHeight(null)) / 2;
	    g2d.drawImage(this.imSrc, x, y, null);
	}
	
}
