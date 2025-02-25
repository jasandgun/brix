package com.pbo.brix_util;

import java.awt.Dimension;

// Store commonly used values

public interface Commons {

	// basic attributes
	int WIDTH = 500;
    int HEIGHT = 700;
    Dimension frameSize = new Dimension(WIDTH, HEIGHT);
    String backButton = "<-";
    String mainFont = "res/font/unlearn2.ttf";
    
    // button sfx
    String forwardClick = "res/sound/click_forward.wav";
    MusicPlay clickForward = new MusicPlay();
    String backwardClick = "res/sound/click_backward.wav";
    MusicPlay clickBackward = new MusicPlay();
    
    // title screen
    String iconPath = "res/image/brixIcon.png";
    String titlePath = "res/image/title.png";
    String titleBGPath = "res/image/bg_black.jpg";
    String titleMusicPath = "res/sound/TitleMusic.wav";
    String exitButton = "exit";
    MusicPlay titleMusic = new MusicPlay();
    
    // Standard values
    final int mapRow = 6, mapCol = 5;
    final int paddleWidth = 100, paddleHeight = 25;
    
    // zen mode
    String zenBGPath = "res/image/bg_zen.jpg";
    String zenMusicPath = "res/sound/ZenMode.wav";
    String zenButton = "ZEN";
    MusicPlay zenMusic = new MusicPlay();
    
    // level selector
    String levelLabel = "LEVELS";
    String lvselBGPath = "res/image/bg_lvselect.jpg";
    String lvselMusicPath = "res/sound/LevelSelectMusic.wav";
    MusicPlay lvselMusic = new MusicPlay();
    
    // normal mode
    final int timeLimit = 100;
    String normalButton = "NORMAL";
    String normalBGPath = "res/image/bg_level.jpg";
    MusicPlay normalMusic = new MusicPlay();
    String normalMusicPath = "res/sound/NormalMode.wav";
    String finalText = "<html><center>Congratulations!<br>"
    		+ "You have finished the game!";
    MusicPlay brickBreak = new MusicPlay();
    String brickBreakPath = "res/sound/brick-break.wav";
    
    // credits
    String creditsText = "<html><center>BRIX<br>"
    		+ "by JKDev:<br>"
    		+ "PBO - H<br>"
    		+ "Jason - 05111940000085<br>"
    		+ "Kevin - 05111940000157<br>"
    		+ "thanks for playing</center>";
    MusicPlay creditsMusic = new MusicPlay();
    String creditsMusicPath = "res/sound/CreditMusic.wav";
    
}