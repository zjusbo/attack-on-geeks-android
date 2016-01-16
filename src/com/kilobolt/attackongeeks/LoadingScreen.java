package com.kilobolt.attackongeeks;

import java.util.concurrent.locks.Lock;

import android.util.Log;

import com.kilobolt.framework.Audio;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Screen;

/**
 * This class is to load all used resources.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class LoadingScreen extends Screen {
	private int count = 0;
	private int a = 255;
	public LoadingScreen(Game game) {
		
		super(game);
		Init();
	}
	/**
	 * Load all resources.
	 */
	private void Init(){
		Graphics g = game.getGraphics();
		Audio a = game.getAudio();
		Assets.teacher = new Image[7];
		//Warning: teacher[0] is not used.
        Assets.teacher[1] = g.newImage("Teachers/t1teacher.png", ImageFormat.ARGB8888);
        Assets.teacher[2] = g.newImage("Teachers/t2teacher.png", ImageFormat.ARGB8888);
        Assets.teacher[3] = g.newImage("Teachers/t3teacher.png", ImageFormat.ARGB8888);
        Assets.teacher[4] = g.newImage("Teachers/t4teacher.png", ImageFormat.ARGB8888);
        Assets.teacher[5] = g.newImage("Teachers/t5teacher.png", ImageFormat.ARGB8888);
        Assets.teacher[6] = g.newImage("Teachers/t6teacher.png", ImageFormat.ARGB8888);

        Assets.student = new Image[5];
        
        Assets.student[1] = g.newImage("Students/student1.png", ImageFormat.ARGB8888);
        Assets.student[2] = g.newImage("Students/student2.png", ImageFormat.ARGB8888);
        Assets.student[3] = g.newImage("Students/student3.png", ImageFormat.ARGB8888);
        Assets.student[4] = g.newImage("Students/student4.png", ImageFormat.ARGB8888);
	
        Assets.gui = new Image[6];
        Assets.gui[0] = g.newImage("GUI/credits.png", ImageFormat.ARGB8888);
        Assets.gui[1] = g.newImage("GUI/exit.png", ImageFormat.ARGB8888);
        
        Assets.gui[3] = g.newImage("GUI/tool bar.png", ImageFormat.ARGB8888);
        Assets.gui[4] = g.newImage("GUI/basicgame.png", ImageFormat.ARGB8888);//basic
        Assets.gui[5] = g.newImage("GUI/advancedgame.png", ImageFormat.ARGB8888);//advance
        
        Assets.gameHelp = new Image[3];
        Assets.gameHelp[0] = g.newImage("help/help1.png", ImageFormat.ARGB8888);
        Assets.gameHelp[1] = g.newImage("help/help2.png", ImageFormat.ARGB8888);
        Assets.gameHelp[2] = g.newImage("help/help3.png", ImageFormat.ARGB8888);
	
        Assets.gameOver = new Image[2];
        Assets.gameOver[0] = g.newImage("Movies/gameover.png", ImageFormat.ARGB8888);
        Assets.gameOver[1] = g.newImage("Movies/gamewin.png", ImageFormat.ARGB8888);
    	Assets.gamewait = g.newImage("Movies/gamewait.png", ImageFormat.ARGB8888);
        
        Assets.map = g.newImage("map.jpg", ImageFormat.RGB565);
        
        Assets.paper = g.newImage("paper.png", ImageFormat.ARGB8888);
        Assets.paperbreak = g.newImage("paperbreak.png", ImageFormat.ARGB8888);
	
        Assets.set = a.createSound("Audio/set.wav");
        Assets.spawn = a.createSound("Audio/spawn.wav");
	}
	@Override
	public void update(float deltaTime) {
		
        if(count >= 510)        	
        	game.setScreen(new GameScreen(game));
        else{
        	count+=2;
        	a = count < 255?255-count:count - 255;
        }
	}
	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.loadingImage, 0, 0);
		g.drawARGB(a,0,0,0);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}