package com.kilobolt.attackongeeks;

import android.graphics.Paint;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * This class is to hold the tool bar.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class ToolBar extends Sprite {
	public ToolBar(Image tex, Point pos, int totalFrame, int refreshSpeed) {
		super(tex, pos, totalFrame, refreshSpeed);
	}

    public void Draw(Graphics g, Player player, int waveNumber, int totalWaveNumber)
    {
    	super.Draw(g);
    	String text = new String("Gold : "+player.GetMoney()+" Lives : "+player.GetLives());
    	String help = new String("Press double click the screen to get help!");
        String text1 = new String("15");
        String text2 = new String("25");
        String text3 = new String("40");
        String swaveNumber = new String("Round: "+waveNumber+"/"+(totalWaveNumber));
    	Paint paint = new Paint();
        g.drawString(text, 610, 620, paint);
    	g.drawString(help, 610, 635,  paint);
    	 g.drawString(text1, 47, 620,  paint);
    	 g.drawString(text1, 111, 620,  paint);
    	 g.drawString(text1, 175, 620,  paint);
    	 g.drawString(text1, 239, 620,  paint);
    	 g.drawString(text2, 303, 620, paint);
    	 g.drawString(text3, 368, 620,  paint);
    
         g.drawString(swaveNumber, 450, 635,  new Paint());
    	
    	}

}
