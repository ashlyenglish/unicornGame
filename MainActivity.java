package edu.upenn.cis573.hwk2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	/**
	 * Application consists of one or more activities, one activity is roughly one screen
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen); // content view is what to display
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

	/*
	 * This method is called automatically when the user clicks on the button.
	 */
	public void onButtonClick(View v) {
		// this code is used to launch the GameActivity
		Intent i = new Intent(this, GameActivity.class);
		startActivity(i);
	}
}
