package jj;

import se.lth.control.realtime.*;

public class Regul extends Thread {

	private AnalogIn mainIn;
	private AnalogIn tailIn;
	private AnalogOut mainOut;
	private AnalogOut tailOut;
	private double umin = -10.0;
	private double umax = 10.0;

	public Regul(int pri) {

//		try {
//			mainIn = new AnalogIn(0);
//			tailIn = new AnalogIn(1);
//			mainOut = new AnalogOut(0);
//			tailOut = new AnalogOut(1);
//			mainOut.set(0);
//			tailOut.set(0);
//		} catch (IOChannelException e) {
//			e.printStackTrace();
//		}

		setPriority(pri);
	}

	public void run() {
		try {
			while (true) {
//				mainOut.set(0);
//				Thread.sleep(5000);
//				mainOut.set(5);
//				Thread.sleep(5000);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Thread t = new Regul(8);
		t.start();
	}
}
