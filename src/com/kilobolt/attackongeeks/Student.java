package com.kilobolt.attackongeeks;

import java.util.ArrayList;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * The student class
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Student extends Sprite
{
    private ArrayList<Point> waypoints = new ArrayList<Point>();


    private float speedModifier;

    private float modifierDuration;
    private float modiferCurrentTime;

    protected float startHealth;
    protected float currentHealth;

    protected boolean alive = true;

    protected float speed = 0.5f;
    protected int bountyGiven;
    private int studentType;

    public int GetStudentType()
    {
        return studentType; 
    }
    public void SetStudentType(int value)
    {
        studentType = value; 
    }
    
    
    /// <summary>
    /// Alters the speed of the Student.
    /// </summary>
    public float GetSpeedModifier()
    {
        return speedModifier; 
    }
    public void SetSpeedModifier(float value)
    {
        speedModifier = value;
    }
    /// <summary>
    /// Defines how long the speed modification will last.
    /// </summary>
    public float GetModifierDuration()
    {
        return modifierDuration; 
    }
    public void SetModifierDuration(float value) 
    { 
            modifierDuration = value;
            modiferCurrentTime = 0;
    }

    public float GetCurrentHealth()
    {
       return currentHealth; 
    }
    public void SetCurrentHealth(float value)
    {
    	currentHealth = value;
    }

    public boolean IsDead()
    {
        return !alive;
    }
    public int BountyGiven()
    {
        return bountyGiven;
    }

    public float DistanceToDestination()
    {
        return Point.Distance(position, this.waypoints.get(0));
        
    }


    public Student(Image texture, Point position, int health, int bountyGiven, float speed, int studentType, int totalframe, int refreshSpeed)
    {
    	super(texture,position,totalframe,refreshSpeed);
        this.startHealth = health;
        this.currentHealth = startHealth;
        this.bountyGiven = bountyGiven;
        this.speed = speed;
        this.studentType = studentType;
    }

    public  void SetWaypoints(ArrayList<Point> waypoints)
    {
        for(Point waypoint : waypoints)
            this.waypoints.add(waypoint);
        
        this.position = new Point(this.waypoints.get(0));
        this.waypoints.remove(0);
    }
    /*To be Debugged*/
    @Override
    public  void Update(float gameTime)
    {
        super.Update(gameTime);
        if (waypoints.size() > 0)
        {
            if (DistanceToDestination() <= 1f)
            {
                this.position = new Point(this.waypoints.get(0));//right               
                this.waypoints.remove(0);
            }
            else
            {
            	Point waypoint = this.waypoints.get(0);
                Vector2 direction = new Vector2(waypoint.x - position.x,waypoint.y - position.y);
                direction.Normalize();
                
                // Store the original speed.
                float temporarySpeed = speed;

                // If the modifier has finished,
                if (modiferCurrentTime > modifierDuration)
                {
                    // reset the modifier.
                    speedModifier = 0;
                    modiferCurrentTime = 0;
                }

                if (speedModifier != 0 && modiferCurrentTime <= modifierDuration)
                {
                    // Modify the speed of the Student.
                    temporarySpeed *= speedModifier;
                    // Update the modifier timer.
                    modiferCurrentTime += (float)(gameTime / 100.0);//To be debugged
                }
                velocity = Vector2.Multiply(direction, temporarySpeed);
                position.offset(velocity.x,velocity.y);
            }
        }
        else
            alive = false;

        if (currentHealth <= 0)
            alive = false;
    }
    @Override
    public void Draw(Graphics g)
    {
        if (alive)
        {
            super.Draw(g);
        }
    }
}
