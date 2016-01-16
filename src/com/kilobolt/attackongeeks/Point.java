package com.kilobolt.attackongeeks;

/**
 * The class is to serve as a XNA Vector2-like Point.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Point {
	public float x,y;
	public int X,Y;
	public static Point Zero = new Point(0,0);
	public static float Distance(Point p1, Point p2){
		return (float)Math.sqrt((p1.x - p2.x) * (p1.x-p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
	}
	
	public Point(float x, float y){
		this.x = x;
		this.y = y;
		updateINT();
	}
	private void updateINT(){
		this.X = (int)this.x;
		this.Y = (int)this.y;
	}
	public Point(int x, int y){
		this.x = x;
		this.y = y;
		updateINT();
	}

	public Point(Point point) {
		// TODO Auto-generated constructor stub
		this.x = point.x;
		this.y = point.y;
		updateINT();
	}

	public void set(float x, float y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		updateINT();
	}
	
	public void offset(float dx, float dy){
		this.x += dx;
		this.y += dy;
		updateINT();
	}
	@Override
	public String toString(){
		return new String("Point = ("+this.x+","+this.y+")");
	}

	public void offset(double dx, double dy) {
		// TODO Auto-generated method stub
		this.x += dx;
		this.y += dy;
		updateINT();
	}

	
	
	
}
