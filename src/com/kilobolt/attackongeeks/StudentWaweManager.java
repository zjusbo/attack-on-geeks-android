package com.kilobolt.attackongeeks;

import java.util.ArrayList;

import android.util.Log;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * This class is to control the student wave.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class StudentWaweManager
{
    private int numberOfWaves; // How many waves the game will have
    private float timeSinceLastWave; // How long since the last wave ended

    private ArrayList<StudentWave> waves = new ArrayList<StudentWave>(); // A queue of all our waves

    private Image[] studentTextureArray; // The texture used to draw the Students

    private boolean waveFinished = false; // Is the current wave over?
    private boolean soundFlag = false;//help to make sound

    private Map map; // A reference to our map class.

    public StudentWave CurrentWave() // Get the wave at the front of the queue
    {
        return waves.get(0);
    }
    public int TotalRounds()
    {
        return numberOfWaves;
    }
    public ArrayList<Student> Students() // Get a list of the current enemeies
    {
        return CurrentWave().Students();
    }
    public int Round() // Returns the wave number
    {
        return CurrentWave().RoundNumber() + 1;
    }
    public boolean GetSoundFlag()
    {
        return soundFlag; 
    }
    public void SetSoundFlag(boolean value)
    {
        soundFlag = value;
    }
    public StudentWaweManager(Player player, Map map, int numberOfWaves, Image[] studentTextureArray)
    {
        this.numberOfWaves = numberOfWaves;
        this.studentTextureArray = studentTextureArray;
        this.map = map;
        soundFlag = true;

        for (int i = 0; i < numberOfWaves; i++)
        {
            int initialNumerOfStudents = 10;
            int numberModifier = 2*(i / 10) + 1;

            // Pass the reference to the player, to the wave class.
            StudentWave wave = new StudentWave(i, initialNumerOfStudents * 
                numberModifier, player, map, studentTextureArray);
            waves.add(wave);
        }

        StartNextWave();
    }

    private void StartNextWave()
    {
        if (waves.size() > 0) // If there are still waves left
        {
            soundFlag = true;
            waves.get(0).Start(); // Start the next one

            timeSinceLastWave = 0; // Reset timer
            waveFinished = false;
        }
    }

    public void Update(float gameTime)
    {
        CurrentWave().Update(gameTime); // Update the wave

        if (CurrentWave().RoundOver()) // Check if it has finished
        {
            waveFinished = true;
        }

        if (waveFinished) // If it has finished
        {
            timeSinceLastWave += (float)gameTime / 100.0; // Start the timer
        }

        if (timeSinceLastWave > 5.0f) // If 30 seconds has passed
        {
            waves.remove(0); // Remove the finished wave
            StartNextWave(); // Start the next wave
        }
    }

    public void Draw(Graphics g)
    {
        CurrentWave().Draw(g);
    }
}
