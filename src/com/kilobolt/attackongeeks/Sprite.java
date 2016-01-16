package com.kilobolt.attackongeeks;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * This Sprite
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Sprite {
	protected Image texture;

    protected Point position;
    protected Vector2 velocity;

    protected Point center;
    protected Point origin;

    protected float rotation;

    /*Sbo modified*/
    protected int count;
    protected int totalFrame, widthPerFrame;
    protected int height;
    protected int frame;
    private int refreshSpeed;//from 1(slow) to 10(fast) . set 0 if only show the first frame.
    /**/
    public int GetRefreshSpeed()
    {
        return refreshSpeed; 
    }
    public void SetRefreshSpeed(int value){
        refreshSpeed = value;
    }
    
    public Point GetPosition()
    {
        return position;
    }
    public Point GetCenter()
    {
        return center;
    }

    public Sprite(Image tex, Point pos, int totalFrame, int refreshSpeed)
    {
        texture = tex;

        position = pos;
        velocity = Vector2.Zero;

        this.totalFrame = totalFrame;
        this.height = tex.getHeight();
        this.widthPerFrame = tex.getWidth() / totalFrame;
        this.refreshSpeed = refreshSpeed;
        this.frame = 0;
        this.count = 0;
        center = new Point(position.x + widthPerFrame / 2, position.y + height / 2);
        origin = new Point(widthPerFrame / 2, height / 2);
    }

    public void Update(float gameTime)
    {
        this.center = new Point(position.x + widthPerFrame / 2,
            position.y + height / 2);
    }

    /**
     * The method is to draw the sprite.
     * @param g Graphics
     */
    public  void Draw(Graphics g)
    {
        count++;
        if (refreshSpeed != 0 && totalFrame != 1)
        {
            int n = count % 10;
            if (n == 1)
            {
                frame = ++frame >= totalFrame ? 0 : frame;
                count = refreshSpeed;
            }
            g.drawImage(texture, (int)position.x,(int)position.y,widthPerFrame * frame, 0, widthPerFrame, height);
            
        }
        else
        {
            g.drawImage(texture, (int)position.x,(int)position.y,0, 0, widthPerFrame, height);
        }
    }
    
}
