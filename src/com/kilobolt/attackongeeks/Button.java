package com.kilobolt.attackongeeks;

import java.util.List;


import com.kilobolt.framework.Game;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.*;

/**
 * This Class is the button class.
 * @author Wang Yue / Song Bo / Xie Fan 
 *
 */
public class Button extends Sprite {

	public enum ButtonStatus
	{
	    Normal,
	    Pressed,
	}

    // The the different state textures.
    // A rectangle that covers the button.
    // Store the current state of the button.
    private ButtonStatus state = ButtonStatus.Normal;
    private int buttonType;
    private Player player;
    /**
     * Set The button state 
     * @param value The button state
     */
    public void setButtonState(ButtonStatus value)
    {
    	state = value;
    }
    /**
     * The button state.
     * @return The button state.
     */
    public ButtonStatus State()
    {
        return state; 
    }
	public Button(Game game, Player player, Image tex, Point pos, int totalFrame, int refreshSpeed, int buttonType) {
		super(tex, pos, totalFrame, refreshSpeed);
		this.player = player;
		this.buttonType = buttonType;
	}
	/**
	 * If the touch event occurs in the bound of button.
	 * @param event Touch Event
	 * @param x Bound's x coordinator
	 * @param y Bound's y coordinator
	 * @param width Bound's width
	 * @param height Bound's height
	 * @return
	 */
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			 int height) {
	        if (event.x > x && event.x < x + width - 1 && event.y > y
	                && event.y < y + height - 1)
	            return true;
	        else
	            return false;
	    }
	/**
	 * Update all status.
	 * @param gameTime Gametime
	 * @param touchEvents Touch Events
	 * @param player The player
	 */
	public  void Update(float gameTime, List<TouchEvent> touchEvents, Player player){
        int len = touchEvents.size();
        this.player = player;
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
            	if(inBounds(event, (int)position.x, (int)position.y, texture.getWidth(), texture.getHeight())){
            		state = ButtonStatus.Pressed;
            		player.NewTeacherType(buttonType);
            		break;
            	}
            	else
            	{
            		state = ButtonStatus.Normal;
            	}
            }
            else
            {
            	state = ButtonStatus.Normal;
            }
        }
	}
}

