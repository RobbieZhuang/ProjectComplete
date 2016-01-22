package dailies;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedRepeatBUpListener implements ActionListener {
	int buttonID;
	int index;

	public ExtendedRepeatBUpListener(int index, int buttonID) {
		this.buttonID = buttonID;
		this.index = index;
	}
	// When the ActionCommand is repeat, this will change the repeat variable to false 
	// (button will turn red, indicating that a daily will not be repeated on that day)
	// If user presses button again, then it will turn green (indicating that the daily
	// will be repeated on that day
	public void actionPerformed(ActionEvent e) {
		boolean[] repeat = DailyClass.repeat;
		if ("repeat".equals(e.getActionCommand())) {
			repeat[buttonID] = false;
			Daily.dayList[index].setRepeat(repeat);
			DailyClass.upRepeatButtons[buttonID].setActionCommand("no repeat");
			DailyClass.upRepeatButtons[buttonID].setForeground(Color.RED);
		} else if ("no repeat".equals(e.getActionCommand())) {
			repeat[buttonID] = true;
			Daily.dayList[index].setRepeat(repeat);
			DailyClass.upRepeatButtons[buttonID].setActionCommand("repeat");
			DailyClass.upRepeatButtons[buttonID].setForeground(Color.GREEN);
		}
	}
}
