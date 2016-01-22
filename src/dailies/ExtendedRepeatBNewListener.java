package dailies;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedRepeatBNewListener implements ActionListener {
	int buttonID;

	public ExtendedRepeatBNewListener(int buttonID) {
		this.buttonID = buttonID;
	}

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
		for (int i = 0; i < repeat.length; i++) {
			System.out.print(repeat[i]);
		}
	}
}
