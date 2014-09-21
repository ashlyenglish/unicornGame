package edu.upenn.cis573.hwk2;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends ActionBarActivity {
	
	// a global, static instance so that the GameView object can refer to this object
	public static GameActivity instance;
	// keeps track of the best time so far
	private static float bestTime = 10000000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_game);
		
        instance = this;
        
        // this method is deprecated but, trust me, it's easier this way
        showDialog(0);
	}
	
	static View getScoreboard() {
		return instance.findViewById(R.id.scoreboard);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_about) {
			Toast.makeText(getApplicationContext(), "Unicorn Game! \n(c)2014 Univ of Pennsylvania", Toast.LENGTH_SHORT).show(); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void onButtonClick(View v) {
    	// this terminates the Activity and goes back to the previous one
    	finish();
    }
    
    protected Dialog onCreateDialog(int id) {
    	if (id == 0) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // this is the message to display
	    	builder.setMessage("Ready?"); 
            // this is the button to display
	    	builder.setPositiveButton(R.string.yes,
	    		new DialogInterface.OnClickListener() {
                       // this is the method to call when the button is clicked 
	    	           public void onClick(DialogInterface dialog, int id) {
                           // this will hide the dialog
	    	        	   dialog.cancel();
	    	        	   // then start the unicorn moving across the screen
	    	               GameView gv = (GameView)findViewById(R.id.gameView);
	    	               GameView.BackgroundDrawingTask t = gv.new BackgroundDrawingTask();
	    	               t.execute();
	    	               gv.startTime = System.currentTimeMillis();
	    	           }
	    	         });
    		return builder.create();
    	}
    	else if (id == 1) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // figure out which message to display
            GameView gv = (GameView)findViewById(R.id.gameView);
	    	long time = gv.endTime - gv.startTime;
	    	// a little magic to convert to tenths of a second
	    	float displayTime = (time / 100) / (float)10.0;
	    	if (bestTime == 10000000) {
	    		bestTime = displayTime;
		    	builder.setMessage("Great job! You finished in " + displayTime + " seconds"); 
	    	}
	    	else if (displayTime <= bestTime) {
	    		bestTime = displayTime;
		    	builder.setMessage("You finished in " + displayTime + " seconds! That's the fastest so far!"); 
	    	}
	    	else builder.setMessage("Great job! You finished in " + displayTime + " seconds; the best so far is " + bestTime);
	    	
            // this is the button to display
	    	builder.setPositiveButton(R.string.yes,
	    		new DialogInterface.OnClickListener() {
                       // this is the method to call when the button is clicked 
	    	           public void onClick(DialogInterface dialog, int id) {
                           // this will hide the dialog
	    	        	   dialog.cancel();
	    	        	   // this will terminate this Activity
	    	        	   finish();
	    	           }
	    	         });
    		return builder.create();
    		
    	}
    	else return null;
    }
    
    
}


