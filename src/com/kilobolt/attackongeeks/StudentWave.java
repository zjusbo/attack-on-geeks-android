package com.kilobolt.attackongeeks;

import java.util.ArrayList;


import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * This class is a student wave class
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class StudentWave
{
    /*Number of Students to spawn*/
    private int numOfStudents; 
    private int waveNumber;

    /*The time to spawn an Student*/
    private float spawnTimer = 0;
    /*How many Students have spawned in one round*/
    private int studentsSpawned = 0; 
    /*The last student*/
    private boolean studentAtEnd; 
    /*To indicate if the students are spawn*/
    private boolean spawningStudents;

    /*The reference of the player and map*/
    private Player player;
    private Map map;

    /*The reference of the student texture*/
    private Image[] studentTextureArray; 
    /*The list to store students*/
    private ArrayList<Student> students = new ArrayList<Student>();

    public boolean RoundOver()
    {
            return students.size() == 0 && studentsSpawned == numOfStudents?true:false; 
    }
    public int RoundNumber()
    {
        return waveNumber;
    }

    public boolean GetStudentAtEnd()
    {
        return studentAtEnd;
    }
    public void SetStudentAtEnd(boolean value)
    {
    	studentAtEnd = value;
    }
    public ArrayList<Student> Students()
    {
        return students;
    }

    public StudentWave(int waveNumber, int numOfStudents,
        Player player, Map map, Image[] studentTextureArray)
    {
        /*A new wave of students*/
        this.waveNumber = waveNumber;
        this.numOfStudents = 10;

        this.player = player;
        this.map = map;

        this.studentTextureArray = studentTextureArray;
    }

    private void  AddStudent()
    {
        /*Randomly select a student to add*/
        int random = (int)(Math.random() * 10000);
       
        Student student = new Student(studentTextureArray[random%4],
            map.GetWaypoints().get(0), 30+10*waveNumber, 2+waveNumber, 0.75f+0.2f*waveNumber, random%4,7,4);
        
      
        student.SetWaypoints(map.GetWaypoints());
        
       
        students.add(student);
        
        spawnTimer = 0;
        studentsSpawned++;
    }

    public void Start()
    {
        spawningStudents = true;
    }

    public void Update(float gameTime)//gameTime * 10 ms
    {
        /*The number of students in one round is fixed*/
        if (studentsSpawned == numOfStudents)
            spawningStudents = false; 
        
        /*The interval between two students*/
        if (spawningStudents)
        {
            spawnTimer += (float)gameTime / 100.0;

            if (spawnTimer > 2-waveNumber*0.1)
                AddStudent(); 
        }
        /*Update different students*/
        for (int i = 0; i < students.size(); i++)
        {
            Student student = students.get(i);
            student.Update(gameTime);

            if (student.IsDead())
            {
                if (student.GetCurrentHealth() > 0)
                {
                    SetStudentAtEnd(true);
                    player.SetLives(player.GetLives() - 1);
                }
                
                else
                {
                    player.SetMoney(player.GetMoney() + student.BountyGiven());
                }

                Students().remove(student);
                i--;
            }
        }
    }

    public void Draw(Graphics g)
    {
        /*Draw the students*/
        for(Student student : students)
            student.Draw(g);
    }
}
