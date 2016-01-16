package com.kilobolt.attackongeeks;

import java.util.ArrayList;


import com.kilobolt.attackongeeks.GameScreen.PaperState;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * This class is to hold a common teacher
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Teacher extends Sprite
{
    /*The cost, damage, radius of the teacher*/
    protected int cost; 
    protected int damage;
    protected float radius; 
    
    protected Student target;

    /*Different paper image to print*/
    protected boolean isPaperDead;
    protected float paperTimer; 
    protected Image paperTexture, paperBreakTexture;
    protected int teachertype;
    /*A list to store the papaer*/
    protected ArrayList<Paper> paperList = new ArrayList<Paper>();

    public int Cost()
    {
        return cost;
    }
    public int Damage()
    {
        return damage;
    }
    public float Radius()
    {
        return radius;
    }
    public Student Target()
    {
        return target;
    }
    public boolean HasTarget()
    {
        return target != null;
    }
    public Teacher(Image texture, Image paperTexture, Point position, 
    		Image paperBreakTexture, int totalFrame, int damage, int cost, int radius, int teachertype)  
    {
    	super(texture, position, totalFrame, 0);
        isPaperDead = false;
        this.paperTexture = paperTexture;
        this.paperBreakTexture = paperBreakTexture;
        this.damage = damage;//10
        this.cost = cost;//15
        this.radius = radius;//120
        this.teachertype = teachertype;
    }

    protected void FaceTarget()
    {
        /*To get the direction*/
        Vector2 direction = new Vector2(center.x - target.GetCenter().x , center.y - target.GetCenter().y);
        //direction.Normalize();
        rotation = (float)Math.atan2(-direction.x, direction.y);
    }

    public boolean IsInRange(Point position)
    {
        return Vector2.Distance(center, position) <= radius;
    }

    public void GetClosestStudent(ArrayList<Student> students)
    {
        /*Get the closest student and set it to be the target*/
        target = null;
        float smallestRange = radius;

        for (Student student : students)
        {
            if (Vector2.Distance(center, student.GetCenter()) < smallestRange)
            {
                smallestRange = Vector2.Distance(center, student.GetCenter());
                target = student;
            }
        }
    }

    public void Update(float gameTime)
    {
        super.Update(gameTime);

        paperTimer += (float)gameTime / 100.0;

        /*Set the refresh speed of the paper images*/ 
        if (target != null )
        {
            FaceTarget();

            if (!IsInRange(target.GetCenter()) || target.IsDead())
            {
                target = null;
                paperTimer = 0;
            }
            SetRefreshSpeed(4);
        }
        else
        {
            SetRefreshSpeed(0);
        }
        if(teachertype > 4)//ignore teacherslow and teacherPrincipal
        	return;
        
        if (paperTimer >= 0.75f && target != null)
        {
            Paper paper = new Paper(paperTexture, paperBreakTexture, Vector2.Subtract(center,
                new Vector2(paperTexture.getWidth() / 2)), rotation, 5, damage);

            paperList.add(paper);
            paperTimer = 0;
        }

        for (int i = 0; i < paperList.size(); i++)
        {
            Paper paper = paperList.get(i);

            paper.SetRotation(rotation);
            paper.Update(gameTime);

            if (!IsInRange(paper.GetCenter()))
                paper.Kill(PaperState.MISS);

            if (target != null && Vector2.Distance(paper.GetCenter(), target.GetCenter()) < 12)
            {
                if (target.GetStudentType() != this.teachertype && paper.Paperstate() == PaperState.FLYING)
                {
                     target.SetCurrentHealth(target.GetCurrentHealth() - paper.Damage());
                }
                paper.Kill(PaperState.HIT);
            }
            if (target == null)
            {
                paper.Kill(PaperState.MISS);
            }
            if (paper.IsDead())
            {
                paperList.remove(paper);
                i--;
            }
        }
    }


    public void Draw(Graphics g)
    {
        /*Draw the paper images*/
        super.Draw(g);
        for(Paper paper : paperList)
            paper.Draw(g);
    }

}
