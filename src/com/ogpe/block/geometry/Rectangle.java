package com.ogpe.block.geometry;

public class Rectangle {

	public final Point min;
	public final Point max;

	public final double x;
	public final double y;
	public final double w;
	public final double h;

	public Rectangle(Point p1, Point p2) {
		min = new Point(p1.x < p2.x ? p1.x : p2.x, p1.y < p2.y ? p1.y : p2.y);
		max = new Point(p1.x >= p2.x ? p1.x : p2.x, p1.y >= p2.y ? p1.y : p2.y);
		x = min.x;
		y = min.y;
		w = max.x - min.x;
		h = max.y - min.y;
	}
	
	public Rectangle(Point position) {
		min = position;
		max = position;
		x = min.x;
		y = min.y;
		w = 0;
		h = 0;
	}
	
	public Rectangle(double x, double y, double w, double h) {
		min = new Point(x, y);
		max = new Point(x + w, y + h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Rectangle() {
		min = new Point();
		max = new Point();
		x = 0;
		y = 0;
		w = 0;
		h = 0;
	}
	

	public boolean inside(Point p) {
		return min.x <= p.x && p.x <= max.x && min.y <= p.y && p.y <= max.y;
	}

	public boolean intersects(Rectangle r) {
		return min.x <= r.max.x && r.min.x <= max.x && min.y <= r.max.y && r.min.y <= max.y;
	}
	
	public Rectangle setLocation(Point p) {
		return new Rectangle(p, p.add(getSize()));
	}
	
	public Point getSize() {
		return new Point(w, h);
	}
	
	public Rectangle setSize(Point size) {
		return new Rectangle(min, min.add(size));
	}

}
