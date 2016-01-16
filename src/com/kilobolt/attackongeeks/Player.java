package com.kilobolt.attackongeeks;

import java.util.ArrayList;
import java.util.List;


import android.util.Log;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;

/**
 * This class is the player class
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Player
{
    /* Player Information*/
    private int money = 60;
    private int lives = 10;
    private Boolean beingSet = false;
    
    private Image[] teacherTextures;
    private Image paperTexture, paperBreakTexture;

    /*The list to store the teachers in the map*/
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private Game game;
    /*The mosue state*/
    private TouchEvent touchState;
    private TouchEvent oldState=null;
    
    /*Teacher position*/
    private int posX;
    private int posY;

    private int posXX;
    private int posYY;

    /*he type of teacher to add*/
    private int newTeacherType=0;
    /*The index of the new teachers texture*/

    /*A reference to the map*/
    private Map map;

    public int GetMoney()
    {
        return money;
    }
    public void SetMoney(int value)
    {
        money = value;
    }
    public int GetLives()
    {
        return lives; 
    }
    public void SetLives(int value){
        lives = value;
    }

    public void NewTeacherType(int value)
    {
        newTeacherType = value;
    }

    public boolean GetTeacherBeingSet()
    {
        return beingSet;
    }
    public void SetTeacherBeginSet(boolean value)
    {
        beingSet = value;
    }

    public Player(Map map, Image[] teacherTextures, Image paperTexture, Image paperBreakTexture,Game game)
    {
        /*Set a new player*/
    	this.game = game;
        this.map = map;
        this.teacherTextures = teacherTextures;
        this.paperTexture = paperTexture;
        this.paperBreakTexture = paperBreakTexture;
    }

   /**
    * To indicate if a cell is clear.
    * @return True or false.
    */
    private boolean IsCellClear()
    {
        /*To indicate a cell is valiable*/
        /*Make sure teacher is within limits*/
        boolean inBounds = posX >= 0 && posY >= 0 && 
            posX < map.GetWidth() && posY < map.GetHeight(); 

        boolean spaceClear = true;

        /*Check that there is no teacher in this spot*/
        for (Teacher teacher : teachers) 
        {
            spaceClear = !Vector2.equal(new Vector2(posXX, posYY),teacher.GetPosition());
            Log.d("teaposition", teacher.GetPosition().toString());
            Log.d("position", posXX + ","+posYY);
            if (!spaceClear)
            {
                break;
            }
        }

        boolean onPath = (map.GetIndex(posX, posY) != 1);

        /*If both checks are true return true*/
        return inBounds && spaceClear && onPath; 
    }

   /**
    * Add a new teacher to a cell.
    */
    public void AddTeacher()
    {
        /*Add a teacher to the map*/
        Teacher teacherToAdd = null;

        switch (newTeacherType)
        {
            case 1:
            {
                teacherToAdd = new Teacher(teacherTextures[1],
                    paperTexture, new Point(posXX, posYY), paperBreakTexture,6,10,15,120,0);
                break;
            }
            case 2:
            {
                teacherToAdd = new Teacher(teacherTextures[2],
                    paperTexture, new Point(posXX, posYY), paperBreakTexture,6,10,15,120,1);
                break;
            }
            case 3:
            {
                teacherToAdd = new Teacher(teacherTextures[3],
                    paperTexture, new Point(posXX, posYY), paperBreakTexture,6,10,15,120,2);
                break;
            }
            case 4:
            {
                teacherToAdd = new Teacher(teacherTextures[4],
                    paperTexture, new Point(posXX, posYY), paperBreakTexture,6,10,15,120,3);
                break;
            }
            case 5:
            {
                teacherToAdd = new TeacherSlow(teacherTextures[5],
                    paperTexture, new Point(posXX, posYY), paperBreakTexture);
                break;
            }
            case 6:
            {
                teacherToAdd = new TeacherPrincipal(teacherTextures[6],
                    paperTexture, new Point(posXX, posYY), paperBreakTexture);
                break;
            }
        }

        /*Check player's money and if the cell is avaible*/
        if (IsCellClear() == true && teacherToAdd.Cost() <= money)
        {
            teachers.add(teacherToAdd);
            money -= teacherToAdd.Cost();
            beingSet = true;
            newTeacherType = 0;
        }

        else
        {
            newTeacherType = 0;
        }
    }

    /**
     * Update the status.
     * @param gameTime The game time.
     * @param students A collection to hold all students.
     * @param touchEvents All touch events.
     */
    public void Update(float gameTime, ArrayList<Student> students, List<TouchEvent> touchEvents)
    {
    	
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            touchState = event;
        }
        if(len > 0){        	
	        /*Convert the position of the mouse*/
	        posX = (int)(touchState.x / 60); 
	        posY = (int)(touchState.y / 60);
	
	        /*Convert from array space to map space*/
	        posXX = posX * 60; 
	        posYY = posY * 60; 
	
	        /*Listen to the mouse and if the mouse is pressed, set a new teacher*/
	        if (touchState.type == TouchEvent.TOUCH_UP) 
	        {
	            if (newTeacherType > 0)
	            {
	                AddTeacher();
	            }
	        }
	    }
	        /*Update all the teachers*/
	    for(Teacher teacher : teachers)
	    {
	        if (teacher.HasTarget() == false)
	        {
	            teacher.GetClosestStudent(students);
	        }	
	        teacher.Update(gameTime);
        }
        /*Set the oldState so it becomes the state of the previous frame*/
        if(len > 0)
        	oldState = touchState; 
    }

    /**
     * Draw all the teacher if the teacher is set.
     * @param g Graphics
     */
    public void Draw(Graphics g)
    {
        /*Draw all the teachers*/
        for(Teacher teacher : teachers)
        {
            teacher.Draw(g);
        }
    }

    /**
     * Draw the not set teacher.
     * @param g Graphics
     */
    public void DrawPreview(Graphics g)
    {
        /*Draw the teacher preview*/
        if (newTeacherType > 0)
        {
            /*Convert the position of the mouse from array space to map space*/
            int posX = (int)(touchState.x / 60); 
            int posY = (int)(touchState.y / 60);

            /*Convert from array space to map space*/
            int posXX = posX * 60; 
            int posYY = posY * 60; 

            Image previewTexture = teacherTextures[newTeacherType];
            int width = previewTexture.getWidth() / 6;
            int height = previewTexture.getHeight();
            g.drawImage(previewTexture,posXX, posYY,
                0, 0, width, height);
        }
    }

}
