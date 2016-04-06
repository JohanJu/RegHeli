
public class Main {
	
	public static void main(String[] argv) {
		MPC mpc = new MPC();
		GUI gui = new GUI(mpc);
		
		mpc.start();
	}

}
