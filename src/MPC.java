import se.lth.control.realtime.AnalogIn;
import se.lth.control.realtime.AnalogOut;

public class MPC extends Thread {

	private AnalogOut mainOut, tailOut;
	private AnalogIn mainIn, tailIn;
	private int i = 0;
	
	synchronized public void setTime(int i){
		this. i = i;
	}
	synchronized public int getTime(){
		return i;
	}
	synchronized public void incTime(){
		++i;
	}
	

	public MPC() {
//		try {
//			mainOut = new AnalogOut(0);
//			tailOut = new AnalogOut(1);
//			mainIn = new AnalogIn(0);
//			tailIn = new AnalogIn(1);
//		} catch (IOChannelException e) {
//			System.out.print("Error: IOChannelException: ");
//			System.out.println(e.getMessage());
//		}
	}

	public void run() {
		
		long h = 1;
		long nextWake = System.currentTimeMillis();
		while (true) {
			try {
				
				System.out.println(getTime());
				incTime();
				
				nextWake += h;
				sleep(nextWake - System.currentTimeMillis());
			} catch (Exception e) {
			}
		}

	}
}
