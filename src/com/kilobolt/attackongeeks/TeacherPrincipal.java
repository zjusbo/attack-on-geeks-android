package com.kilobolt.attackongeeks;

import java.util.ArrayList;



import com.kilobolt.attackongeeks.GameScreen.PaperState;
import com.kilobolt.framework.Image;

/**
 * This class is to hold the principal
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class TeacherPrincipal extends Teacher//spike teacher
{
    // A list of directions that the teacher can shoot in.
 
    private Vector2[] directions = new Vector2[8];
    // All the enemies that are in range of the teacher.
    private ArrayList<Student> targets = new ArrayList<Student>();

   
    public boolean HasTarget()
    {
        // The teacher will never have just one target.
        return false;
    }

    /// <summary>
    /// Constructs a new Spike Teacher object.
    /// </summary>
    public TeacherPrincipal(Image texture, Image paperTexture, Point position, Image paperBreakTexture)
        
    {
    	super(texture, paperTexture, position, paperBreakTexture, 6,30,40,180,6);

        // Store a list of all the directions the teacher can shoot.
        directions = new Vector2[]
        {
            new Vector2(-1, -1), // North West
            new Vector2( 0, -1), // North
            new Vector2( 1, -1), // North East
            new Vector2(-1,  0), // West
            new Vector2( 1,  0), // East
            new Vector2(-1,  1), // South West
            new Vector2( 0,  1), // South
            new Vector2( 1,  1), // South East
        };
    }
    @Override
    public void GetClosestStudent(ArrayList<Student> students)
    {
        // Do a fresh search for targets.
        targets.clear();

        // Loop over all the students.
        for(Student student : students)
        {
            // Check whether this student is in shooting distance.
            if (IsInRange(student.GetCenter()))
            {
                // Make it a target.
                targets.add(student);
            }
        }
    }
    @Override
    public void Update(float gameTime)
    {
        super.Update(gameTime);
        // Decide if it is time to shoot.
        if (paperTimer >= 1.5f && targets.size() != 0)
        {

            // For every direction the teacher can shoot,
            for (int i = 0; i < directions.length; i++)
            {
                // create a new paper that moves in that direction.
                Paper paper = new Paper(paperTexture, paperBreakTexture, Vector2.Subtract(center,
                    new Vector2(paperTexture.getWidth() / 2)), directions[i], 5, damage);

                paperList.add(paper);
            }

            paperTimer = 0;
        }

        // Loop through all the papers.
        for (int i = 0; i < paperList.size(); i++)
        {
            Paper paper = paperList.get(i);
            paper.Update(gameTime);

            // Kill the paper when it is out of range.
            if (!IsInRange(paper.GetCenter()))
            {
                paper.Kill(PaperState.MISS);
            }

            // Loop through all the possible targets
            for (int t = 0; t < targets.size(); t++)
            {
                // If this paper hits a target and is in range,
                if (targets.get(t) != null && Vector2.Distance(paper.GetCenter(), targets.get(t).GetCenter()) < 12)
                {
                    // hurt the student.
                    if (paper.Paperstate() == PaperState.FLYING)
                    {
                        targets.get(t).SetCurrentHealth(targets.get(t).GetCurrentHealth() - paper.Damage());
                    }
                    
                    paper.Kill(PaperState.HIT);

                    // This paper can't kill anyone else.
                    break;
                }
            }
            
            // Remove the paper if it is dead.
            if (paper.IsDead())
            {
                paperList.remove(paper);
                i--;
            }

        }
        if (paperList.size() > 0)
        {
            SetRefreshSpeed(4);
        }
        else { SetRefreshSpeed(0); }
    }
}
