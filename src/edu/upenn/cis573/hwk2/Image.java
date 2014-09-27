package edu.upenn.cis573.hwk2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Image {
	 private Bitmap image;
	 private Point imagePoint = new Point(-150,100);
	 
	 public Image(Resources res, int id){
	    image = BitmapFactory.decodeResource(res, id);
	    image = Bitmap.createScaledBitmap(image, 150, 150, false);
	 }

	public Point getImagePoint() {
		return imagePoint;
	}

	public void display(Resources res, int id){
		image = BitmapFactory.decodeResource(res, id);
	    image = Bitmap.createScaledBitmap(image, 150, 150, false);
	}
	
	public Bitmap getImage(){
		return image;
	}
	
	public int getImageWidth(){
		return image.getWidth();
	}
	
	public int getImageHeight(){
		return image.getHeight();
	}
	
	public boolean inBoundary(float x,float y){
		return (x > imagePoint.x && x < imagePoint.x + image.getWidth() && y > imagePoint.y && y < imagePoint.y + image.getHeight());
	}
}
