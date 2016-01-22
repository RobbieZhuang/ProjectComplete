package dailies;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedUDListener implements ActionListener {
	public int index;

	public ExtendedUDListener(int n) {
		this.index = n;
	}

	public void actionPerformed(ActionEvent e) {
		DailyClass.addDailyInfo(index);
		DailyClass.updateDailyPanel();
		DailyClass.newDailyB.setEnabled(true);
	}
}
