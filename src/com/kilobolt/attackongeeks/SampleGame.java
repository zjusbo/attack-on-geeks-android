package com.kilobolt.attackongeeks;


import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

/**
 * This class is the main game class extends a AndroidGame
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class SampleGame extends AndroidGame {
	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {

		if (firstTimeCreate) {
			Assets.load(this);
			firstTimeCreate = false;
		}

		return new SplashLoadingScreen(this);

	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	@Override
	public void onResume() {
		super.onResume();
		/*Assets.background.play(0.85f);*/
	}

	@Override
	public void onPause() {
		super.onPause();
		//Assets.background.dispose();

	}
	
	
}