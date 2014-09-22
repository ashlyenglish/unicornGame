package edu.upenn.cis573.hwk2;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameView extends View {

	private Stroke stroke = new Stroke(); // composition
	private ArrayList<Position> positions = stroke
			.setStroke(new ArrayList<Position>());

	private int lineColor = stroke.setLineColor(Color.RED);
	private int lineWidth = stroke.setLineWidth(10);

	private Image unicorn = new Image();
	private Bitmap image = unicorn.getImage();
	private Position imagePosition = unicorn.setImagePosition(new Position(
			-150, 100));
	private int yChange = 0;

	private boolean killed = false;
	private boolean newUnicorn = true;

	private int score = 0;
	public long startTime;
	public long endTime;

	public GameView(Context context) {
		super(context);
		setBackgroundResource(R.drawable.space);
		image = BitmapFactory
				.decodeResource(getResources(), R.drawable.unicorn);
		image = Bitmap.createScaledBitmap(image, 150, 150, false);
	}

	public GameView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setBackgroundResource(R.drawable.space);
		image = BitmapFactory
				.decodeResource(getResources(), R.drawable.unicorn);
		image = Bitmap.createScaledBitmap(image, 150, 150, false);
	}

	/*
	 * This method is automatically invoked when the View is displayed. It is
	 * also called after you call "invalidate" on this object.
	 */
	protected void onDraw(Canvas canvas) {

		// Resets the position of the unicorn if one is killed or reaches the
		// right edge
		if (newUnicorn || imagePosition.getX() >= this.getWidth()) {
			imagePosition = new Position(-150,
					(int) (Math.random() * 200 + 200));
			yChange = (int) (10 - Math.random() * 20);
			newUnicorn = false;
			killed = false;
		}

		// show the exploding image when the unicorn is killed
		if (killed) {
			// rather than creating a new Bitmap object in the
			// "show the exploding image" part of the code, it uses the existing
			// Image field and simply changes which Bitmap gets displayed.
			Bitmap explode = BitmapFactory.decodeResource(getResources(),
					R.drawable.explosion);
			explode = unicorn.setImage(explode);
			// explode = Bitmap.createScaledBitmap(explode, 150, 150, false);
			canvas.drawBitmap(explode, imagePosition.getX(),
					imagePosition.getY(), null);
			newUnicorn = true;
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
			invalidate();
			return;
		}

		// draws the unicorn at the specified point
		canvas.drawBitmap(image, imagePosition.getX(), imagePosition.getY(),
				null);
		// draws the stroke, in Stroke class
		stroke.drawLine(canvas, lineColor, lineWidth);

	}

	/*
	 * This method is automatically called when the user touches the screen.
	 */
	public boolean onTouchEvent(MotionEvent event) {

		stroke.onTouchEvent(event); // in Stroke class
		// see if the point is within the boundary of the image
		if (unicorn.isInBound(image, imagePosition, event) && killed == false) {
			killed = true;
			score++;
			((TextView) (GameActivity.instance.getScoreboard())).setText(""
					+ score);
		}

		// forces a redraw of the View
		invalidate();

		return true;
	}

	/*
	 * This inner class is responsible for making the unicorn appear to move.
	 * When "exec" is called on an object of this class, "doInBackground" gets
	 * called in a background thread. It just waits 10ms and then updates the
	 * image's position. Then "onPostExecute" is called.
	 */
	class BackgroundDrawingTask extends AsyncTask<Integer, Void, Integer> {

		// this method gets run in the background
		protected Integer doInBackground(Integer... args) {
			try {
				// note: you can change these values to make the unicorn go
				// faster/slower
				Thread.sleep(10);
				imagePosition = new Position((imagePosition.getX() + 10),
						(imagePosition.getY() + yChange));
			} catch (Exception e) {
			}
			// the return value is passed to "onPostExecute" but isn't actually
			// used here
			return 1;
		}

		// this method gets run in the UI thread
		protected void onPostExecute(Integer result) {
			// redraw the View
			invalidate();
			if (score < 10) {
				// need to start a new thread to make the unicorn keep moving
				BackgroundDrawingTask task = new BackgroundDrawingTask();
				task.execute();
			} else {
				// game over, man!
				endTime = System.currentTimeMillis();
				// these methods are deprecated but it's okay to use them...
				// probably.
				GameActivity.instance.removeDialog(1);
				GameActivity.instance.showDialog(1);
			}
		}
	}

}
