package edu.upenn.cis573.hwk2;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Point;

public class Stroke {
	private ArrayList<Point> points;
    private static final int lineColor = Color.RED;
    private static final int lineWidth = 10;
    
    public Stroke(){
    	points = new ArrayList<Point>();
    }
    
    public void addPoints(Point p){
    	points.add(p);
    	
    }
    
    public ArrayList<Point> getPoints(){
    	return points;
    }

	public static int getLinecolor() {
		return lineColor;
	}

	public static int getLinewidth() {
		return lineWidth;
	}
}
