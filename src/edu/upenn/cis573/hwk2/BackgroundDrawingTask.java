package edu.upenn.cis573.hwk2;

import android.os.AsyncTask;

public class BackgroundDrawingTask extends AsyncTask<GameView, Void, GameView> {
	
	// this method gets run in the background
	protected GameView doInBackground(GameView... param) {
		try { 
			// note: you can change these values to make the unicorn go faster/slower
			Thread.sleep(10);
			param[0].getImagePoint().x += 10; 
			param[0].getImagePoint().y += param[0].getYChange(); 
		} 
		catch (Exception e) {}
		// the return value is passed to "onPostExecute" but isn't actually used here
		return param[0]; 
	}
	
	// this method gets run in the UI thread
	protected void onPostExecute(GameView result) {
		// redraw the View
		result.invalidate();
		if (result.getScore() < 10) {
			// need to start a new thread to make the unicorn keep moving
			BackgroundDrawingTask task = new BackgroundDrawingTask();
			task.execute(result);
		}
		else {
			// game over, man!
			result.setEndTime(System.currentTimeMillis());
			// these methods are deprecated but it's okay to use them... probably.
			
			GameActivity.instance.removeDialog(1);
			GameActivity.instance.showDialog(1);
		}
	}

//	@Override
//	protected GameView doInBackground(GameView... params) {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
