package dailies;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedRepeatBNewListener implements ActionListener {
	int buttonID;

	public ExtendedRepeatBNewListener(int buttonID) {
		this.buttonID = buttonID;
	}
	
	// When the ActionCommand is repeat, this will change the repeat variable to false 
	// (button will turn red, indicating that a daily will not be repeated on that day)
	// If user presses button again, then it will turn green (indicating that the daily
	// will be repeated on that day
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean[] repeat = DailyClass.repeat;
		if ("repeat".equals(e.getActionCommand())) {
			repeat[buttonID] = false;
			DailyClass.repeat = repeat;
			DailyClass.repeatButtons[buttonID].setActionCommand("no repeat");
			DailyClass.repeatButtons[buttonID].setForeground(Color.RED);
		} else if ("no repeat".equals(e.getActionCommand())) {
			repeat[buttonID] = true;
			DailyClass.repeat = repeat;
			DailyClass.repeatButtons[buttonID].setActionCommand("repeat");
			DailyClass.repeatButtons[buttonID].setForeground(Color.GREEN);
		}
	}
}
