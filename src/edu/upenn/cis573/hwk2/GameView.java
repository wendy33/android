package edu.upenn.cis573.hwk2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameView extends View {
	private Image image;
    private Stroke stroke = new Stroke();
    private boolean killed = false;
    private boolean newUnicorn = true;
    private int score = 0;
    private int yChange = 0;
    
    public GameView(Context context) {
	    super(context);
	    createView();
    }
    
    public GameView(Context context, AttributeSet attributeSet) {
    	super(context, attributeSet);
    	createView();
    }
    
    public void createView(){
    	setBackgroundResource(R.drawable.space);
	    image = new Image(getResources(),R.drawable.unicorn);
    }
    /*
     * This method is automatically invoked when the View is displayed.
     * It is also called after you call "invalidate" on this object.
     */
    protected void onDraw(Canvas canvas) {    	

    	// resets the position of the unicorn if one is killed or reaches the right edge
    	if (newUnicorn || image.getImagePoint().x >= this.getWidth()) {
    		image.display(getResources(), R.drawable.unicorn);
    		image.getImagePoint().x = -150;
    		image.getImagePoint().y = (int)((Math.random()+1) * 200);
    		yChange = (int)(10 - Math.random() * 20);
    		newUnicorn = false;
    		killed = false;
    	}

		// show the exploding image when the unicorn is killed
    	if (killed) {
    		image.display(getResources(), R.drawable.explosion);
    		canvas.drawBitmap(image.getImage(), image.getImagePoint().x, image.getImagePoint().y, null);
    		newUnicorn = true;
    		try { Thread.sleep(10); } catch (Exception e) { }
    		invalidate();
    		return;
    	}

    	// draws the unicorn at the specified point
		canvas.drawBitmap(image.getImage(), image.getImagePoint().x, image.getImagePoint().y, null);
    	
		// draws the stroke
    	if (stroke.getPoints().size() > 1) {
    		for (int i = 0; i < stroke.getPoints().size()-1; i++) {
    			int startX = stroke.getPoints().get(i).x;
    			int stopX = stroke.getPoints().get(i+1).x;
    			int startY = stroke.getPoints().get(i).y;
    			int stopY = stroke.getPoints().get(i+1).y;
    			Paint paint = new Paint();
    			paint.setColor(stroke.getLinecolor());
    			paint.setStrokeWidth(stroke.getLinewidth());
    			canvas.drawLine(startX, startY, stopX, stopY, paint);
    		}
    	}
    	
    }
    
    public boolean addPoints(int action){
    	return  action == MotionEvent.ACTION_DOWN||action == MotionEvent.ACTION_MOVE;
    }

    /* 
     * This method is automatically called when the user touches the screen.
     */
    public boolean onTouchEvent(MotionEvent event) {
    	if (addPoints(event.getAction())) {
    		stroke.addPoints(new Point((int)event.getX(),(int)event.getY()));
    	}
    	else if (event.getAction() == MotionEvent.ACTION_UP) {
    		stroke.getPoints().clear();
    	}
    	else {
    		return false;
    	}
    	
    	// see if the point is within the boundary of the image
    	float x = event.getX();
    	float y = event.getY();
    	// the !killed thing here is to prevent a "double-kill" that could occur
    	// while the "explosion" image is being shown
    	if (!killed && image.inBoundary(x,y)) {
    		killed = true;
    		score++;
    		((TextView)((GameActivity) getContext()).getScoreboard()).setText(""+score);
    	}
    	
    	// forces a redraw of the View
    	invalidate();
    	
    	return true;
    }    

    public Point getImagePoint(){
    	return image.getImagePoint();
    }
    
    public int getYChange(){
    	return yChange;
    }
    
    public int getScore(){
    	return score;
    }

    public void setEndTime(long t){
    	((GameActivity) getContext()).endTime = t;
    }

}

