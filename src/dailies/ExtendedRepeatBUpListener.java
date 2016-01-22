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
