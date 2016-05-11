package jj;

//2.8 3.4
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.lth.control.DoublePoint;
import se.lth.control.realtime.*;

public class Regul extends Thread implements ChangeListener {

	private OpCom opcom;
	private AnalogIn mainIn;
	private AnalogIn tailIn;
	private AnalogOut mainOut;
	private AnalogOut tailOut;
	private double umin = -10.0;
	private double umax = 10.0;
	private double starttime;

	private long time = 50;

	private double power = 0;

	public Regul(OpCom opcom, int pri) {

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
			double mAng, mVel, mAcc;
			double k = 1, td = 1;
			while (true) {
				mAng = mainIn.get();
				mVel = AtV(mAng);
				mAcc = VtA(mVel);
				double e = 0 - mAng;
				power = e*k+td*mVel;
				mainOut.set(power);
				sendDataToOpCom(mAng, mVel, mAcc);
				Thread.sleep(time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private double lastA;

	private double AtV(double ang) {
		double re = 100 * (ang - lastA) / time;
		lastA = ang;
		return re;
	}

	private double lastV;

	private double VtA(double vel) {
		double re = 100 * (vel - lastV) / time;
		lastV = vel;
		return re;
	}

	private void sendDataToOpCom(double mAng, double mVel, double mAcc) {
		double x = (double) (System.currentTimeMillis() - starttime) / 1000.0;
		PlotData main = new PlotData(x, mAng, mVel, mAcc);
		PlotData tail = null;
		opcom.putControlDataPoint(main);
		opcom.putMeasurementDataPoint(main);
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		JSlider theJSlider = (JSlider) source;
		power = theJSlider.getValue() / 100.0;
		System.out.println(power);

	}
	
	public static void main(String[] args) {
		OpCom opcom = new OpCom(7);
		Regul regul = new Regul(opcom, 8);
		opcom.setRegul(regul);
		opcom.initializeGUI();
		opcom.start();
		regul.start();
	}
}
