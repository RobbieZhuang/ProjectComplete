package dailies;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedCloseUDListener implements ActionListener {
	public int buttonID;

	public ExtendedCloseUDListener(int buttonID) {
		this.buttonID = buttonID;
	}

	// Closes the daily edit window
	public void actionPerformed(ActionEvent e) {
		DailyClass.newDailyB.setEnabled(false);
		DailyClass.updateDaily(buttonID);
	}
}
