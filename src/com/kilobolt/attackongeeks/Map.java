package com.kilobolt.attackongeeks;

import java.util.ArrayList;


import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * This class is to build a game Map
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Map {
        private Image tileTexture;
        private ArrayList<Point> waypoints = new ArrayList<Point>();
        int [][]map =
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,1,1,0,0},
            {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,1,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        };

        public ArrayList<Point> GetWaypoints()
        {
            return waypoints;
        }

        public int GetWidth()
        {
            return map[0].length;
        }
        public int GetHeight()
        {
            return map.length;
        }
        /**
         * Set the way points.
         */
        public Map()
        {
        	tileTexture = Assets.map;
            waypoints.add(new Point(0, 8 * 60));
            waypoints.add(new Point(9 * 60, 8 * 60));
            waypoints.add(new Point(9 * 60, 5 * 60));
            waypoints.add(new Point(6 * 60, 5 * 60));
            waypoints.add(new Point(6 * 60, 1 * 60));
            waypoints.add(new Point(12 * 60, 1 * 60));
        }

        public int GetIndex(int posX, int posY)
        {
            // It needed to be Width - 1 and Height - 1.
            if (posX < 0 || posX > GetWidth() - 1 || posY < 0 || posY > GetHeight() - 1)
                return 0;
            return map[posY][posX];
        }
        /**
         * Add a new texture.
         * @param texture
         */
        public void AddTexture(Image texture)
        {
            tileTexture = texture;
        }
        /**
         * Draw the map.
         * @param g The Graphics
         */
        public void Draw(Graphics g)
        {	
            g.drawImage(tileTexture, 0,0);
        }
    }

