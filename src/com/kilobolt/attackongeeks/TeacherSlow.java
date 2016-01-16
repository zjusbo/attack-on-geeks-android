package com.kilobolt.attackongeeks;


import com.kilobolt.attackongeeks.GameScreen.PaperState;
import com.kilobolt.framework.Image;

/**
 * This class is to hold the woman teacher 
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class TeacherSlow extends Teacher//slow teacher
{
    // Defines how fast an student will move when hit.
    private float speedModifier;
    // Defines how long this effect will last.
    private float modifierDuration;

    public TeacherSlow(Image texture, Image paperTexture, Point position, Image paperBreakTexture)
    {
    	super(texture, paperTexture, position, paperBreakTexture, 5,5,25,240,5);
        this.speedModifier = 0.6f;
        this.modifierDuration = 2.0f;
    }
    @Override
    public void Update(float gameTime)
    {
        super.Update(gameTime);

        if (paperTimer >= 2.0f && target != null)
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

            // If the paper hits a target,
            if (target != null && Vector2.Distance(paper.GetCenter(), target.GetCenter()) < 12)
            {
                // destroy the paper and hurt the target.
                if (paper.Paperstate() == PaperState.FLYING)
                {
                    target.SetCurrentHealth(target.GetCurrentHealth() - paper.Damage());
                }
                
                paper.Kill(PaperState.HIT);

                // Apply our speed modifier if it is better than
                // the one currently affecting the target :
                if (target.GetSpeedModifier() <= speedModifier)
                {
                    target.SetSpeedModifier(speedModifier);
                    target.SetModifierDuration(modifierDuration);
                }
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
}
