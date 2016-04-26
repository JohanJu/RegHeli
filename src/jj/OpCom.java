package jj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import se.lth.control.*;
import se.lth.control.plot.*;

/** Class that creates and maintains a GUI for the Ball and Beam process. 
Uses two PlotterPanels for the plotters */
public class OpCom {    

	public static final int OFF=0, BEAM=1, BALL=2;
	private static final double eps = 0.000001;

	private Regul regul;
	private int priority;
	private int mode;

	// Declarartion of main frame.
	private JFrame frame;

	// Declarartion of panels.
	private BoxPanel guiPanel, plotterPanel, innerParPanel, outerParPanel, parPanel;
	private JPanel innerParLabelPanel, innerParFieldPanel, outerParLabelPanel, outerParFieldPanel, buttonPanel, somePanel, leftPanel;
	private PlotterPanel measPanel, ctrlPanel;

	// Declaration of components.
	private DoubleField innerParKField = new DoubleField(5,3);
	private DoubleField innerParTiField = new DoubleField(5,3);
	private DoubleField innerParTrField = new DoubleField(5,3);
	private DoubleField innerParBetaField = new DoubleField(5,3);
	private DoubleField innerParHField = new DoubleField(5,3);
	private JButton innerApplyButton;


	private JRadioButton offModeButton;
	private JRadioButton beamModeButton;
	private JRadioButton ballModeButton;
	private JButton stopButton;

	private boolean hChanged = false;
	private boolean isInitialized = false;

	/** Constructor. */
	public OpCom(int plotterPriority) {
		priority = plotterPriority;
	}

	/** Starts the threads. */
	public void start() {
		measPanel.start();
		ctrlPanel.start();
	}

	/** Sets up a reference to Regul. Called by Main. */
	public void setRegul(Regul r) {
		regul = r;
	}

	/** Creates the GUI. Called from Main. */
	public void initializeGUI() {
		// Create main frame.
		frame = new JFrame("Heli");

		// Create a panel for the two plotters.
		plotterPanel = new BoxPanel(BoxPanel.VERTICAL);
		// Create PlotterPanels.
		measPanel = new PlotterPanel(2, priority);
		measPanel.setYAxis(20, -10, 2, 2);
		measPanel.setXAxis(10, 5, 5);
		measPanel.setUpdateFreq(1);
		ctrlPanel = new PlotterPanel(1, priority);
		ctrlPanel.setYAxis(20, -10, 2, 2);
		ctrlPanel.setXAxis(10, 5, 5);
		ctrlPanel.setUpdateFreq(1);

		plotterPanel.add(measPanel);
		plotterPanel.addFixed(10);
		plotterPanel.add(ctrlPanel);

		// Get initial parameters from Regul

		// Create panels for the parameter fields and labels, add labels and fields 
		innerParPanel = new BoxPanel(BoxPanel.HORIZONTAL);
		innerParLabelPanel = new JPanel();
		innerParLabelPanel.setLayout(new GridLayout(0,1));
		innerParLabelPanel.add(new JLabel("K: "));
		innerParLabelPanel.add(new JLabel("Ti: "));
		innerParLabelPanel.add(new JLabel("Tr: "));
		innerParLabelPanel.add(new JLabel("Beta: "));
		innerParLabelPanel.add(new JLabel("h: "));
		innerParFieldPanel = new JPanel();
		innerParFieldPanel.setLayout(new GridLayout(0,1));
		innerParFieldPanel.add(innerParKField); 
		innerParFieldPanel.add(innerParTiField);
		innerParFieldPanel.add(innerParTrField);
		innerParFieldPanel.add(innerParBetaField);
		innerParFieldPanel.add(innerParHField);

		// Set initial parameter values of the fields
		innerParKField.setValue(0);
		innerParTiField.setValue(0);
		innerParTiField.setMinimum(-eps);
		innerParTrField.setValue(0);
		innerParTrField.setMinimum(-eps);
		innerParBetaField.setValue(0);
		innerParBetaField.setMinimum(-eps);
		innerParHField.setValue(0);
		innerParHField.setMinimum(-eps);

		// Add action listeners to the fields
		innerParKField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerApplyButton.setEnabled(true);
			}
		});
		innerParTiField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerApplyButton.setEnabled(true);
			}
		});
		innerParTrField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerApplyButton.setEnabled(true);
			}
		});
		innerParBetaField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerApplyButton.setEnabled(true);
			}
		});
		innerParHField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerApplyButton.setEnabled(true);
				hChanged = true;
			}
		});

		// Add label and field panels to parameter panel
		innerParPanel.add(innerParLabelPanel);
		innerParPanel.addGlue();
		innerParPanel.add(innerParFieldPanel);
		innerParPanel.addFixed(10);

		// Create apply button and action listener.
		innerApplyButton = new JButton("Apply");
		innerApplyButton.setEnabled(false);
		innerApplyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hChanged = false;
				innerApplyButton.setEnabled(false);
			}
		});

		// Create panel with border to hold apply button and parameter panel
		BoxPanel innerParButtonPanel = new BoxPanel(BoxPanel.VERTICAL);
		innerParButtonPanel.setBorder(BorderFactory.createTitledBorder("Inner Parameters"));
		innerParButtonPanel.addFixed(10);
		innerParButtonPanel.add(innerParPanel);
		innerParButtonPanel.addFixed(10);
		innerParButtonPanel.add(innerApplyButton);

		

		// Create panel for parameter fields, labels and apply buttons
		parPanel = new BoxPanel(BoxPanel.HORIZONTAL);
		parPanel.add(innerParButtonPanel);

		// Create panel for the radio buttons.
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		// Create the buttons.
		offModeButton = new JRadioButton("OFF");
		beamModeButton = new JRadioButton("MAIN");
		ballModeButton = new JRadioButton("BOTH");
		stopButton = new JButton("STOP");
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(offModeButton);
		group.add(beamModeButton);
		group.add(ballModeButton);
		// Button action listeners.
		offModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		beamModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ballModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				measPanel.stopThread();
				ctrlPanel.stopThread();
				System.exit(0);
			}
		});

		// Add buttons to button panel.
		buttonPanel.add(offModeButton, BorderLayout.NORTH);
		buttonPanel.add(beamModeButton, BorderLayout.CENTER);
		buttonPanel.add(ballModeButton, BorderLayout.SOUTH);

		// Panel for parameter panel and radio buttons
		somePanel = new JPanel();
		somePanel.setLayout(new BorderLayout());
		somePanel.add(parPanel, BorderLayout.CENTER);
		somePanel.add(buttonPanel, BorderLayout.SOUTH);

		// Select initial mode.
		switch (mode) {
		case OFF:
			offModeButton.setSelected(true);
			break;
		case BEAM:
			beamModeButton.setSelected(true);
			break;
		case BALL:
			ballModeButton.setSelected(true);
		}


		// Create panel holding everything but the plotters.
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(somePanel, BorderLayout.CENTER);
		leftPanel.add(stopButton, BorderLayout.SOUTH);

		// Create panel for the entire GUI.
		guiPanel = new BoxPanel(BoxPanel.HORIZONTAL);
		guiPanel.add(leftPanel);
		guiPanel.addGlue();
		guiPanel.add(plotterPanel);

		// WindowListener that exits the system if the main window is closed.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				measPanel.stopThread();
				ctrlPanel.stopThread();
				System.exit(0);
			}
		});

		// Set guiPanel to be content pane of the frame.
		frame.getContentPane().add(guiPanel, BorderLayout.CENTER);

		// Pack the components of the window.
		frame.pack();

		// Position the main window at the screen center.
		Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fd = frame.getSize();
		frame.setLocation((sd.width-fd.width)/2, (sd.height-fd.height)/2);

		// Make the window visible.
		frame.setVisible(true);
		
		isInitialized = true;
	}

	/** Called by Regul to plot a control signal data point. */
	public synchronized void putControlDataPoint(DoublePoint dp) {
		if (isInitialized) {
			ctrlPanel.putData(dp.x, dp.y);
		} else {
			DebugPrint("Note: GUI not yet initialized. Ignoring call to putControlDataPoint().");
		}
	}

	/** Called by Regul to plot a measurement data point. */
	public synchronized void putMeasurementDataPoint(PlotData pd) {
		if (isInitialized) {
			measPanel.putData(pd.x, pd.yref, pd.y);
		} else {
			DebugPrint("Note: GUI not yet initialized. Ignoring call to putMeasurementDataPoint().");
		}
	}
	
	private void DebugPrint(String message) {
		System.out.println(message);
	}
}