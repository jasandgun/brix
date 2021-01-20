package com.pbo.brix_ui;

import java.awt.Color;
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
import javax.swing.JPanel;

import com.pbo.brix_util.ButtonMaker;
import com.pbo.brix_util.Commons;
import com.pbo.brix_util.FontMaker;

public class CreditsPanel extends JPanel implements ActionListener {

	private int labelHeight = Commons.HEIGHT, iconY;
	private Image imSrc, brixIcon;
	private Font font = new FontMaker(Commons.mainFont).getFont();
	private JButton backButton;
	private final JLabel creditLabel;
	
	public CreditsPanel() {
		setPreferredSize(Commons.frameSize);
		this.imSrc = new ImageIcon(Commons.titleBGPath).getImage();
		this.brixIcon = new ImageIcon(Commons.iconPath).getImage();
		this.setLayout(null);
		this.iconY = 700;
		
		// Creating and customizing the button
		Rectangle rect = new Rectangle(10, 10, 75, 50);
		backButton = new ButtonMaker(Commons.backButton, rect).getButton();
		backButton.setFont(font);
		backButton.setVisible(false);
		this.add(backButton);
		backButton.addActionListener(this);
		
		creditLabel = new JLabel();
		creditLabel.setText(Commons.creditsText);
		creditLabel.setForeground(Color.WHITE);
		creditLabel.setFont(font.deriveFont(35f));
		creditLabel.setBounds(0,labelHeight,Commons.WIDTH,Commons.HEIGHT);
		creditLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(creditLabel);
		
		startThread();
	}

	public void startThread() {
		Thread creditThread = new Thread() {
			public void run() {
				while(true) {
					labelHeight -= 1; iconY -= 1;
					creditLabel.setBounds(0,labelHeight,Commons.WIDTH,Commons.HEIGHT);
					try {
						if(labelHeight <= -(Commons.HEIGHT - 750)) {
							backButton.setVisible(true);
							return;
						}
						Thread.sleep(12);
					}
					catch (InterruptedException ex) { }
				}
			}
		};
		creditThread.start();
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			Commons.creditsMusic.stopMusic();
			Commons.clickBackward.playMusic(Commons.backwardClick);
        	BRIX.main_frame.setContentPane(new TitlePanel());
        	BRIX.main_frame.pack();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	    int x = (this.getWidth() - this.imSrc.getWidth(null)) / 2;
	    int y = (this.getHeight() - this.imSrc.getHeight(null)) / 2;
	    g2d.drawImage(this.imSrc, x, y, null);
	    int xi = (this.getWidth() - this.brixIcon.getWidth(null)) / 2;
	    g2d.drawImage(this.brixIcon, xi, iconY, null);
	}
	
}
