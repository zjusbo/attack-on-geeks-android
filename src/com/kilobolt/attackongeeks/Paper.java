package com.kilobolt.attackongeeks;


import android.graphics.Rect;

import com.kilobolt.attackongeeks.GameScreen.PaperState;
import com.kilobolt.framework.Image;

/**
 * The paper class extens Sprite.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Paper extends Sprite
{
    /*The damage, age, speed of the paper*/
    private int damage;
    private int age;
    private int speed;
    private Image paperBreak;
    private PaperState state;
    public int Damage()
    {
        return damage;
    }

    public boolean IsDead()
    {
        return age > 100;
    }
    public PaperState Paperstate()
    {
        return state;
    }
    public Paper(Image texture,Image breakTexture, Point position, float rotation, int speed, int damage)
    {
    	super(texture,position,1,0);//total frame, refresh speed.
        this.state = PaperState.FLYING;
        this.rotation = rotation;
        this.damage = damage;
        this.speed = speed;
        this.paperBreak = breakTexture;
        velocity = Vector2.Rotate(new Vector2(0, -speed),rotation);
    }
    
    public Paper(Image texture, Image breakTexture, Point position, Vector2 velocity, int speed, int damage)
        
    {
    	super(texture, position, 1, 0);
        //this.rotation = rotation;
        this.damage = damage;
        this.paperBreak = breakTexture;
        this.speed = speed;
        this.state = PaperState.FLYING;
        this.velocity = Vector2.Multiply(velocity, speed);
    }

    /**
     * This class is to set the paper to dim.
     * @param state The paper's state.
     */
    public void Kill(PaperState state)
    {
        /*TO indicate different stage of the paper*/
        if (this.state != PaperState.FLYING)
        {
            return;
        }
        this.state = state;
        if (state == PaperState.MISS)
        {
            this.age = 200;
        }
        else if(state == PaperState.HIT)
        {
            //Replace texture to paper break
            texture = paperBreak;
            totalFrame = 5;
            frame = 0;
            height = texture.getHeight();
            widthPerFrame = texture.getWidth() / totalFrame;
            count = 0;
            SetRefreshSpeed(5);
            velocity = Vector2.Zero;
        }
    }

    /**
     * This method is to set paper to rotate.
     * @param value The angle.
     */
    public void SetRotation(float value)
    {
        /*Set the rotation and velocity of the paper*/
        rotation = value;
        velocity = Vector2.Rotate(new Vector2(0, -speed), rotation);
    }
    
    @Override
    public void Update(float gameTime)
    {
        /*Set the different position of the paper according to the velocity*/
        if (state == PaperState.HIT)
        {
            if (frame == 4)
            {
                this.age = 200;//Kill Paper
            }
        }
        else
        {
            age++;
            position.set(velocity.x + position.x,velocity.y + position.y);
        }
        super.Update(gameTime);
    }
}
