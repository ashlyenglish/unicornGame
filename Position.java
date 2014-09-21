package edu.upenn.cis573.hwk2;

import android.graphics.Point;

public class Position extends Point {

	private int xPosition;
	private int yPosition;

	public Position(int x, int y) {
		xPosition = x;
		yPosition = y;
	}
	
	public int getX() {
		return xPosition;
	}
	
	public int getY() {
		return yPosition;
	}

}
