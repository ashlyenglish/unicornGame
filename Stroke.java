package edu.upenn.cis573.hwk2;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Stroke {

	private ArrayList<Position> stroke;
	private int lineColor;
	private int lineWidth;

	public ArrayList<Position> getStroke() {
		return this.stroke;
	}

	public ArrayList<Position> setStroke(ArrayList<Position> stroke) {
		this.stroke = stroke;
		return this.stroke;
	}

	public int getLineColor() {
		return this.lineColor;
	}

	public int setLineColor(int lineColor) {
		this.lineColor = lineColor;
		return this.lineColor;
	}

	public int getLineWidth() {
		return this.lineWidth;
	}

	public int setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
		return this.lineWidth;
	}

	public void drawLine(Canvas canvas) {
		if (stroke.size() > 1) {
			for (int i = 0; i < stroke.size() - 1; i++) {
				int startX = stroke.get(i).getX();
				int stopX = stroke.get(i + 1).getX();
				int startY = stroke.get(i).getY();
				int stopY = stroke.get(i + 1).getY();

				Paint paint = new Paint();
				paint.setColor(lineColor);
				paint.setStrokeWidth(lineWidth);
				canvas.drawLine(startX, startY, stopX, stopY, paint);
			}
		}
	}

}
