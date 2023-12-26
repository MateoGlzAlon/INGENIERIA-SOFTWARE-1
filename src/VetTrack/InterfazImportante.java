package VetTrack;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class InterfazImportante {

	public JFrame frame;

	/**
	 * Create the application.
	 */
	public InterfazImportante() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 1200, 900);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				frame.dispose();
			}
		});
	}

}
