package com.kilobolt.attackongeeks;

/**
 * This class is to build a XNA Vector2-like class.
 * @author Wang Yue / Song Bo / Xie Fan
 *
 */
public class Vector2{
	public float x,y;
	public float length;

	public static Vector2 Multiply(Vector2 x, float scale){
		return new Vector2(x.x * scale, x.y * scale);
	}
	
	public static Vector2 Zero = new Vector2(0,0);
    public static float Distance(Point x, Point y){
    	return (float)Math.sqrt((x.x - y.x) * (x.x-y.x) + (x.y - y.y) * (x.y - y.y));
    }
	public Vector2(double x, double y){
		this.x = (float)x;
		this.y = (float)y;
		this.length = (float) Math.sqrt(x*x+y+y*y);
	}
	public Vector2(Vector2 v){
		this.x = v.x;
		this.y = v.y;
		this.length = (float) Math.sqrt(x*x+y+y*y);
	}
	public Vector2(double x) {
		// TODO Auto-generated constructor stub
		this.x = this.y = (float)x;
		this.length = (float) (Math.sqrt(2.0) * x);
	}
	public boolean equal(Vector2 v){
		return v.x==this.x && v.y==this.y?true:false;
	}
	public void Normalize(){
		if(length == 0){
			return;
		}
		this.x /= length;
		this.y /= length;
		this.length = 1;
	}
	public void set(float x, float y){
		this.x = x;
		this.y = y;
		this.length = (float) Math.sqrt(x*x+y+y*y);
	}
	public static Vector2 Rotate(Vector2 vector2, float rotation) {//roatation in radians.
		// TODO Auto-generated method stub
		float x1 = (float) (vector2.x * Math.cos(rotation) - vector2.y * Math.sin(rotation));
		float y1 = (float) (vector2.x * Math.sin(rotation) + vector2.y * Math.cos(rotation));
		return new Vector2(x1,y1);
	}
	public static Point Subtract(Point p1, Vector2 p2) {
		// TODO Auto-generated method stub
		return new Point((int)(p1.x - p2.x),(int)(p1.y - p2.y));
	}
	public static boolean equal(Vector2 v, Point p) {
		// TODO Auto-generated method stub
		
		return v.x==p.x && v.y==p.y?true:false;
	}
}
