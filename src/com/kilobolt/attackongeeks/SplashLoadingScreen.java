package com.kilobolt.attackongeeks;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Graphics.ImageFormat;

/**
 * The splash loading screen.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.loadingImage= g.newImage("Movies/logo.jpg", ImageFormat.RGB565);
		
		game.setScreen(new LoadingScreen(game));

	}

	@Override
	public void paint(float deltaTime) {
		
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