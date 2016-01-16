package com.kilobolt.attackongeeks;

import java.util.List;

import android.R.color;

import com.kilobolt.attackongeeks.Button.ButtonStatus;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;

/**
 * This class is to control the screen of the game
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class GameScreen extends Screen {
	/*From Game1.cs*/
	public enum GameState
    {
        GameWait,
        GameStart,
        GameWin,
        help1,
        help2,
        help3,
        GameOver
    }
    /*Indicate the paper state*/
    public enum PaperState
    {
        FLYING,
        HIT,
        MISS
    }
    GameState gameState;
    Sprite helpSprite1,helpSprite2,helpSprite3;
    Button basicGameButton,advancedGameButton,exitButton,creditVideo;
    Button t1Button,t2Button,t3Button,t4Button,t5Button,t6Button;
    ToolBar toolBar;
    Sprite gameoverScreen;
    Sprite gamewinScreen;
    Sprite gamewait;
    int type;
    Map map = new Map();
    Player player;
    TouchEvent oldEvent=null;
    TouchEvent oldoldEvent=null;
    private StudentWaweManager studentWaweManager;
    Image[] StudentTextureArray;
	public GameScreen(Game game) {
		super(game);
		go();
	}
	/**
	 * Initialize all sprite.
	 */
	public void go(){
		player = new Player(map, Assets.teacher, Assets.paper, Assets.paperbreak,game);
		helpSprite1 = new Sprite(Assets.gameHelp[0], new Point(0,0), 1, 0);
		helpSprite2 = new Sprite(Assets.gameHelp[1], new Point(0,0), 1, 0);
		helpSprite3 = new Sprite(Assets.gameHelp[2], new Point(0,0), 1, 0);
		exitButton = new Button(game, player, Assets.gui[1], new Point(700, map.GetHeight()*60), 1, 0, -2);
		basicGameButton = new Button(game, player, Assets.gui[4], new Point(700, map.GetHeight()*44), 1, 0, -1);
		advancedGameButton = new Button(game, player, Assets.gui[5], new Point(700, map.GetHeight()*52), 1, 0, -1);
		toolBar = new ToolBar(Assets.gui[3], new Point(0, 165), 1, 0);
		t1Button = new Button(game, player, Assets.teacher[1], new Point(5, map.GetHeight() * 60), 6, 0, 1);
		t2Button = new Button(game, player, Assets.teacher[2], new Point(65, map.GetHeight() * 60), 6, 0, 2);
		t3Button = new Button(game, player, Assets.teacher[3], new Point(65*2, map.GetHeight() * 60), 6, 0, 3);
		t4Button = new Button(game, player, Assets.teacher[4], new Point(65*3, map.GetHeight() * 60), 6, 0, 4);
		t5Button = new Button(game, player, Assets.teacher[5], new Point(65*4, map.GetHeight() * 60), 6, 0, 5);
		t6Button = new Button(game, player, Assets.teacher[6], new Point(65*5, map.GetHeight() * 60), 6, 0, 6);
		gameoverScreen = new Sprite(Assets.gameOver[0], new Point(200, 0), 1, 0);
		gamewinScreen = new Sprite(Assets.gameOver[1], new Point(200, 0), 1, 0);
		gamewait = new Sprite(Assets.gamewait, new Point(0, 0), 1, 0);
        StudentTextureArray = new Image[]
        {
             Assets.student[1],
             Assets.student[2],
             Assets.student[3],
             Assets.student[4],
        };
		studentWaweManager = new StudentWaweManager(player, map, 10, StudentTextureArray);
		gameState = GameState.GameWait;
		
		Assets.background.play();
	}
	/**
	 * Initialize a new game.
	 * @param type Basic or advanced game.
 	 */
	public void init(int type)
	{
		player = new Player(map, Assets.teacher, Assets.paper, Assets.paperbreak,game);
		studentWaweManager = new StudentWaweManager(player, map, type, StudentTextureArray);
	}
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		if(gameState == GameState.GameStart)
		{
			 studentWaweManager.Update(deltaTime);
             player.Update(deltaTime, studentWaweManager.Students(), touchEvents);
             t1Button.Update(deltaTime, touchEvents, player);
             t2Button.Update(deltaTime, touchEvents, player);
             t3Button.Update(deltaTime, touchEvents, player);
             t4Button.Update(deltaTime, touchEvents, player);
             t5Button.Update(deltaTime, touchEvents, player);
             t6Button.Update(deltaTime, touchEvents, player);
             toolBar.Update(deltaTime);
             if (player.GetLives() <= 0)
             {
                 gameState = GameState.GameOver;
             }
             if (studentWaweManager.Round() >= type)
             {
                 gameState = GameState.GameWin;
             }
             
             if (player.GetTeacherBeingSet() == true)
             {
                 Assets.set.play(0.7f);
                 player.SetTeacherBeginSet(false);
             }
             if (studentWaweManager.GetSoundFlag() == true)
             {
                 Assets.spawn.play(0.7f);
                 studentWaweManager.SetSoundFlag(false);
             }
             
             int len = touchEvents.size();
             for (int i = 0; i < len; i++) {
                 TouchEvent event = touchEvents.get(i);
                 if (event.type == TouchEvent.TOUCH_DOWN) {
                 	if(inBounds(event, (int)800, (int)0, 100, 100)){
                 		gameState = GameState.help1;
                 		break;
                 	}
                 }
             }
		}
		else if(gameState == GameState.GameWait){
            Assets.spawn.stop();
            Assets.set.stop();
            
			basicGameButton.Update(deltaTime, touchEvents, player);
			advancedGameButton.Update(deltaTime, touchEvents, player);
			exitButton.Update(deltaTime, touchEvents, player);
		     if(basicGameButton.State() == Button.ButtonStatus.Pressed)
		     {
		    	 gameState = GameState.GameStart;
		    	 basicGameButton.setButtonState(ButtonStatus.Normal);
		    	 try {
					Thread.sleep(100, 0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 init(5);
		    	 type=5;
		     }
		     else if(advancedGameButton.State() == Button.ButtonStatus.Pressed)
		     {
		    	 gameState = GameState.GameStart;
		    	 advancedGameButton.setButtonState(ButtonStatus.Normal);
		    	 try {
					Thread.sleep(100, 0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 init(20);
		    	 type=20;
		     }
		     else if(exitButton.State() == Button.ButtonStatus.Pressed)
		     {
		    	 android.os.Process.killProcess(android.os.Process.myPid());
		     }
		}
		else if (gameState == GameState.GameWin || gameState == GameState.GameOver)
        {
			basicGameButton.Update(deltaTime, touchEvents, player);
			advancedGameButton.Update(deltaTime, touchEvents, player);
			exitButton.Update(deltaTime, touchEvents, player);
		     if(basicGameButton.State() == Button.ButtonStatus.Pressed)
		     {
		    	 gameState = GameState.GameStart;
		    	 basicGameButton.setButtonState(ButtonStatus.Normal);
		    	 try {
					Thread.sleep(100, 0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 init(5);
		    	 type=5;
		     }
		     else if(advancedGameButton.State() == Button.ButtonStatus.Pressed)
		     {
		    	 gameState = GameState.GameStart;
		    	 advancedGameButton.setButtonState(ButtonStatus.Normal);
		    	 try {
					Thread.sleep(100, 0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 init(20);
		    	 type=20;
		     }
		     else if(exitButton.State() == Button.ButtonStatus.Pressed)
		     {
		    	 android.os.Process.killProcess(android.os.Process.myPid());
		     }
        }
		else if (gameState == GameState.help1)
        {
            Assets.spawn.stop();
            Assets.set.stop();
            
             int len = touchEvents.size();
			 for (int i = 0; i < len; i++) {
                 TouchEvent event = touchEvents.get(i);
                 if(event.type == TouchEvent.TOUCH_DRAGGED && oldEvent != null && oldEvent.type == TouchEvent.TOUCH_DOWN)
                 {
                	 gameState = GameState.help2;
                	 try {
     					Thread.sleep(100, 0);
     				} catch (InterruptedException e) {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     				}
                 }
                 oldEvent = event;
                 
             }
        }
		else if (gameState == GameState.help2)
        {
            int len = touchEvents.size();
			 for (int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_DRAGGED && oldEvent != null && oldEvent.type == TouchEvent.TOUCH_DOWN)
                {
               	 gameState = GameState.help3;
               	 try {
 					Thread.sleep(100, 0);
 				} catch (InterruptedException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
                }
                oldEvent = event;
            }
        }
		else if (gameState == GameState.help3)
        {
            int len = touchEvents.size();
			 for (int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_DRAGGED && oldEvent != null && oldEvent.type == TouchEvent.TOUCH_DOWN)
               	 gameState = GameState.GameStart;
               	 try {
 					Thread.sleep(100, 0);
 				} catch (InterruptedException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
  
                oldEvent = event;
            }
        }
	}
	
	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();
		if(gameState == GameState.GameWait)
		{
			g.clearScreen(color.background_light);
			gamewait.Draw(g);
			basicGameButton.Draw(g);
			advancedGameButton.Draw(g);
			exitButton.Draw(g);
		}
		else if(gameState == GameState.GameStart)
		{
			g.clearScreen(color.background_light);
			map.Draw(g);
			studentWaweManager.Draw(g);
			player.Draw(g);
            toolBar.Draw(g, player, studentWaweManager.Round(), studentWaweManager.TotalRounds());
            t1Button.Draw(g);
            t2Button.Draw(g);
            t3Button.Draw(g);
            t4Button.Draw(g);
            t5Button.Draw(g);
            t6Button.Draw(g);
            player.DrawPreview(g);
		}
		else if (gameState == GameState.GameOver)
        {
			g.clearScreen(color.background_light);
            gameoverScreen.Draw(g);
            basicGameButton.Draw(g);
            advancedGameButton.Draw(g);
            exitButton.Draw(g);
        }
        else if (gameState == GameState.GameWin)
        {
        	g.clearScreen(color.background_light);
            gamewinScreen.Draw(g);
            basicGameButton.Draw(g);
            advancedGameButton.Draw(g);
            exitButton.Draw(g);
        }
        else if (gameState == GameState.help1)
        {
        	 helpSprite1.Draw(g);
        }
        else if (gameState == GameState.help2)
        {
            helpSprite2.Draw(g);
        }
        else if (gameState == GameState.help3)
        {
            helpSprite3.Draw(g);
        }
	
	}
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			 int height) {
	        if (event.x > x && event.x < x + width - 1 && event.y > y
	                && event.y < y + height - 1)
	            return true;
	        else
	            return false;
	    }

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		if(gameState == GameState.GameWait)
		{
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		else
		{
			gameState = GameState.GameWait;
		}
	}

}
