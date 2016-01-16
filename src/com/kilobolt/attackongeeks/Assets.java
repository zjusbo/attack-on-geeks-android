package com.kilobolt.attackongeeks;

import com.kilobolt.framework.Image;
import com.kilobolt.framework.Music;
import com.kilobolt.framework.Sound;

/**
 * This class is to hold all resources.
 * @author Wang Yue / Song Bo / Xie Fan 
 * 
 */
public class Assets {
	
    public static Image []teacher;
    public static Image []student;
    public static Image []gameOver;
    public static Image gamewait;
    public static Image []gameHelp;
    public static Image []gui;
    public static Image loadingImage;
    public static Image map,paper,paperbreak;
    public static Sound set, spawn;
    public static Music background;
   
	public static void load(SampleGame sampleGame) {
		// TODO Auto-generated method stub
		
		set = sampleGame.getAudio().createSound("Audio/set.wav");
		spawn = sampleGame.getAudio().createSound("Audio/spawn.wav");		
		background = sampleGame.getAudio().createMusic("Audio/background.mp3");
		background.setLooping(true);
			
	}
	
}
