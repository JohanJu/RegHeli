package jj;

import se.lth.control.DoublePoint;
import se.lth.control.realtime.*;

public class Regul extends Thread {

	private OpCom opcom;
	private AnalogIn mainIn;
	private AnalogIn tailIn;
	private AnalogOut mainOut;
	private AnalogOut tailOut;
	private double umin = -10.0;
	private double umax = 10.0;
	private double starttime;

	public Regul(OpCom opcom,int pri) {
		
		this.opcom = opcom;

		try {
			mainIn = new AnalogIn(0);
			tailIn = new AnalogIn(1);
			mainOut = new AnalogOut(0);
			tailOut = new AnalogOut(1);
			mainOut.set(0);
			tailOut.set(0);
		} catch (IOChannelException e) {
			e.printStackTrace();
		}

		setPriority(pri);
	}

	public void run() {
		starttime = System.currentTimeMillis();
		try {
			double mAng;
			while (true) {
				mainOut.set(0);
				for (int i = 0; i < 20; i++) {
					mAng = mainIn.get();
					sendDataToOpCom(mAng,AtV(mAng),0);
					Thread.sleep(100);
				}
				mainOut.set(5);
				for (int i = 0; i < 17; i++) {
					mAng = mainIn.get();
					sendDataToOpCom(mAng,AtV(mAng),0);
					Thread.sleep(100);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private double lastA;
	private double AtV(double ang){
		double re = (ang-lastA);
		lastA = ang;
		return re;
	}
	
	private void sendDataToOpCom(double mAng, double mVel, double u) {
		double x = (double)(System.currentTimeMillis() - starttime) / 1000.0;
		DoublePoint dp = new DoublePoint(x,u);
		PlotData pd = new PlotData(x,mAng,mVel);
		opcom.putControlDataPoint(dp);
		opcom.putMeasurementDataPoint(pd);
	}

	public static void main(String[] args) {
		OpCom opcom = new OpCom(7);
		Regul regul = new Regul(opcom,8);
		opcom.initializeGUI();
		opcom.start();
		regul.start();
	}
}
