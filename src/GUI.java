import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import SimEnvironment.Plotter;

public class GUI extends JFrame implements ActionListener{
	
	private MPC mpc;
	JTextField input;

	public GUI(MPC mpc) {
		this.mpc = mpc;
		input = new JTextField(40);
		input.addActionListener(this);
		add(input);
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Plotter plotter = new Plotter(3,100,10,-10);
		add(plotter.getPanel());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		mpc.setTime(Integer.parseInt(input.getText()));
	}

}
