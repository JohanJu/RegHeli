package jj;

public class PlotData implements Cloneable {
	public double ang, vel, acc;
	public double x; // holds the current time
	
	public PlotData(double xIn, double ang, double vel, double acc) {
		this.ang = ang;
		this.vel = vel;
		this.acc = acc;
		x = xIn;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}
}