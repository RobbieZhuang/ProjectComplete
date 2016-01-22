package toDoPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import characterPackage.Character;
import statusPackage.*;
public class ExtendedDoneListener implements ActionListener {
	public int index;

	public ExtendedDoneListener(int n) {
		this.index = n;
	}

	public void actionPerformed(ActionEvent e) {
		ToDo.toDoList[index].setDone(true);
		try {
			TasksSPClass.addScatterPlot();
		} catch (IOException error) {
		}
		TaskPopup.popF.dispose();
		Character.taskComplete(ToDo.toDoList[index].getImportance());
		Status.updateDailyStatsPanel();
	}
}
