package todos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Creating a new popup window with the title and description of the 
// checklist todo as well as buttons to complete and to close the popup
public class TaskPopup {
	static JFrame popF;
	static JButton doneB;
	static JButton closeB;

	// Creating the frame of the popup window and adding data
	public static void createPopup(int index, String title, String description, Point p) {
		popF = new JFrame();
		popF.setUndecorated(true);
		popF.setOpacity(0.75f);
		popF.setBackground(new Color(255, 100, 100, 0));
		JPanel panP = new JPanel(new BorderLayout());
		JLabel titleL = new JLabel(title);
		JLabel descripL = new JLabel(description);
		JPanel buttonP = new JPanel(new FlowLayout());
		doneB = new JButton("Done");
		doneB.addActionListener(new ExtendedDoneListener(index));
		closeB = new JButton("Close");
		closeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popF.dispose();
			}
		});
		buttonP.add(doneB);
		buttonP.add(closeB);
		panP.add(titleL, BorderLayout.NORTH);
		panP.add(descripL, BorderLayout.CENTER);
		panP.add(buttonP, BorderLayout.SOUTH);
		popF.add(panP);
		popF.pack();

		popF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popF.setVisible(true);
		popF.setAlwaysOnTop(true);
		popF.setLocation(p);
		popF.setVisible(true);
	}
}
