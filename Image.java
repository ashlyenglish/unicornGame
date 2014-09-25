package edu.upenn.cis573.hwk2;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Image {
	private Bitmap image;
	private Position imagePosition;

	public Position getImagePosition() {
		return this.imagePosition;
	}

	public Position setImagePosition(Position imagePosition) {
		this.imagePosition = imagePosition;
		return this.imagePosition;
	}

	public Bitmap getImage() {
		return this.image;
	}

	public Bitmap setImage(Bitmap image) {
		this.image = image;
		return this.image;
	}

	/**
	 * Checks if the stroke draw is in the bound of the unicorn image 
	 * @param bImage
	 * @param p
	 * @param event
	 * @return
	 */
	public boolean isInBound(Bitmap bImage, Position p, MotionEvent event) {
		// see if the point is within the boundary of the image
		int width = bImage.getWidth();
		int height = bImage.getHeight();
		float x = event.getX();
		float y = event.getY();
		boolean kill = false;
		// the !killed thing here is to prevent a "double-kill" that could occur
		// while the "explosion" image is being shown
		if (x > p.getX() && x < (p.getX() + width) && y > p.getY()
				&& (y < p.getY() + height)) {
			kill = true;
		}
		return kill;
	}

}
