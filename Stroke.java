package edu.upenn.cis573.hwk2;

import java.util.ArrayList;


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

}
