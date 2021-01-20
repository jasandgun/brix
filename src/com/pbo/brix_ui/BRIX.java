package com.pbo.brix_ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BRIX extends JFrame {

	public static JFrame main_frame;
	
	private static void initUI() {
		
		main_frame = new JFrame("BRIX");
		main_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		main_frame.setContentPane(new TitlePanel());
		main_frame.pack();
		main_frame.setLocationRelativeTo(null);
		main_frame.setResizable(false);
		
		// Appear on center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
		main_frame.setLocation(dim.width/2-main_frame.getSize().width/2, dim.height/2-main_frame.getSize().height/2);
		
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initUI();
				main_frame.setVisible(true);
			}
		});
	}
	
}
