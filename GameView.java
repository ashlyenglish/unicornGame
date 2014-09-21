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
	private Bitmap image;

	private ArrayList<Position> positions = new ArrayList<Position>();
	private Position imagePosition = new Position(-150, 100);
	private int yChange = 0;
	private boolean killed = false;
	private boolean newUnicorn = true;

	private int score = 0;
	private static final int lineColor = Color.RED;
	private static final int lineWidth = 10;
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
			Bitmap explode = BitmapFactory.decodeResource(getResources(),
					R.drawable.explosion);
			explode = Bitmap.createScaledBitmap(explode, 150, 150, false);
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
		// draws the stroke
		if (positions.size() > 1) {
			for (int i = 0; i < positions.size() - 1; i++) {
				int startX = positions.get(i).getX();
				int stopX = positions.get(i + 1).getX();
				int startY = positions.get(i).getY();
				int stopY = positions.get(i + 1).getY();

				Paint paint = new Paint();
				paint.setColor(lineColor);
				paint.setStrokeWidth(lineWidth);
				canvas.drawLine(startX, startY, stopX, stopY, paint);
			}
		}

	}

	/*
	 * This method is automatically called when the user touches the screen.
	 */
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			positions.add(new Position((int) event.getX(), (int) event.getY()));
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			positions.add(new Position((int) event.getX(), (int) event.getY()));
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			positions.clear();
		} else {
			return false;
		}

		// see if the point is within the boundary of the image
		int width = image.getWidth();
		int height = image.getHeight();
		float x = event.getX();
		float y = event.getY();
		// the !killed thing here is to prevent a "double-kill" that could occur
		// while the "explosion" image is being shown
		if (!killed && x > imagePosition.getX()
				&& x < imagePosition.getX() + width && y > imagePosition.getY()
				&& y < imagePosition.getY() + height) {
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
