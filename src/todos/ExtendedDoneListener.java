package todos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import character.Character;
import optimization.*;

// When user is done with the todo, it will call this action listener
public class ExtendedDoneListener implements ActionListener {
	public int index;

	public ExtendedDoneListener(int n) {
		this.index = n;
	}
	// Set done to true in the toDoList and then refresh the scatter plot
	public void actionPerformed(ActionEvent e) {
		ToDo.toDoList[index].setDone(true);
		try {
			TasksSPClass.addScatterPlot();
		} catch (IOException error) {
		}
		TaskPopup.popF.dispose();
		Character.taskComplete(ToDo.toDoList[index].getImportance());
		Schedule.updateTodoStatsPanel();
	}
}
