package optimization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import dailies.Daily;
import dailies.DailyClass;
import mainPackage.Program;
import todos.ToDo;

public class Schedule {
	public static JPanel statsPanel;
	public static JPanel dailyStatsP;
	public static JScrollPane jSPdaily;
	public static JScrollPane jSPtodo;
	public static JPanel p1;
	
	// Initiate the schedule panel
	public static JPanel initiateSchedulePanel() {
		statsPanel = new JPanel(new BorderLayout());
		
		updateDailyStatsPanel();
		updateTodoStatsPanel();

		statsPanel.setPreferredSize(new Dimension(1065, 600));
		return statsPanel;
	}

	// Updates the Daily schedule list panel
	public static void updateDailyStatsPanel() { 	
		if (dailyStatsP != null && dailyStatsP.getParent() == statsPanel) {
			statsPanel.remove(dailyStatsP);
		}
		p1 = new JPanel(new BorderLayout());
		p1.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		JLabel titleL = new JLabel("Recommended Order Of Dailies");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Century", Font.PLAIN, 24));
		p1.add(titleL, BorderLayout.NORTH);

		dailyStatsP = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		dailyStatsP.setLayout(gbl);
		String[] optDailies = optimizedDailies();
		for (int i = 0; i < optDailies.length; i++) {
			JLabel dailyLabel = new JLabel((i + 1) + ") " + optDailies[i]);
			dailyLabel.setPreferredSize(new Dimension(510, 40));
			dailyLabel.setFont(new Font("Century", Font.PLAIN, 18));
			dailyLabel.setOpaque(true);
			dailyLabel.setBackground(DailyClass.generateRandomColor());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.gridx = 1;
			gbc.weighty = 1;
			dailyStatsP.add(dailyLabel, gbc);
		}
		jSPdaily = new JScrollPane(dailyStatsP);
		jSPdaily.setPreferredSize(new Dimension(525, 450));
		p1.add(jSPdaily, BorderLayout.CENTER);
		statsPanel.add(p1, BorderLayout.WEST);

		jSPdaily.repaint();
		p1.repaint();
		statsPanel.repaint();
		Program.window.revalidate();
		Program.window.repaint();
	}

	// Update the todo schedule
	public static void updateTodoStatsPanel() {
		JPanel toDoStatsP = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		JLabel titleLL = new JLabel("Recommended Order Of Todos");
		titleLL.setHorizontalAlignment(SwingConstants.CENTER);
		titleLL.setFont(new Font("Century", Font.PLAIN, 24));
		p2.add(titleLL, BorderLayout.NORTH);

		String [] optArray = optimizedTodos();
		
		for (int i = 0; i < optArray.length; i++) {
			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.anchor = GridBagConstraints.NORTHWEST;
			gbc1.gridx = 1;
			gbc1.weighty = 1;
			JLabel todoLabel = new JLabel((i+1) + ") " + optArray[i]);
			todoLabel.setPreferredSize(new Dimension(510, 40));
			todoLabel.setFont(new Font("Century", Font.PLAIN, 18));
			todoLabel.setOpaque(true);
			todoLabel.setBackground(DailyClass.generateRandomColor());
			toDoStatsP.add(todoLabel, gbc1);
		}
		jSPtodo = new JScrollPane(toDoStatsP);
		jSPtodo.setPreferredSize(new Dimension(525, 450));
		p2.add(jSPtodo, BorderLayout.CENTER);
		
		statsPanel.add(p2, BorderLayout.EAST);
		
		jSPtodo.repaint();
		p2.repaint();
		statsPanel.repaint();
		Program.window.revalidate();
		Program.window.repaint();
	}

	// Figures out which dailies have been done, which haven't and then sort by importance
	public static String[] optimizedDailies() {
		int size = 0;
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (Daily.dayList[i] != null && !Daily.dayList[i].getComplete()) {
				size++;
			}
		}
		Daily[] oDailies = new Daily[size];
		Daily.destroyEmpty();
		int index = 0;
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (index < oDailies.length && !Daily.dayList[i].getComplete()){
				oDailies[index] = Daily.dayList[i];
				index++;
			}
		}
		String[] items = new String[size];
		boolean swapped = true;
		while (swapped) {
			swapped = false;
			int n = oDailies.length-1;
			for (int i = 1; i < n; i++) {
				if (oDailies[i].getDifficulty() > oDailies[i - 1].getDifficulty()) {
					Daily temp = oDailies[i];
					oDailies[i] = oDailies[i - 1];
					oDailies[i - 1] = temp;
					swapped = true;
				}
			}
			n = n - 1;
		}
		for (int i = 0; i < oDailies.length; i++) {
			items[i] = oDailies[i].getTitle();
		}
		return items;
	}
	
	// Optimizing the todos with an array of coordinates indicating grids on the scatter plot
	// Tasks that are the highest priority would be found in the first grid, second in the second grid, ect.
	public static String[] optimizedTodos(){
		ArrayList <ToDo> items = new ArrayList<ToDo>();
		for (int i = 0; i < ToDo.toDoList.length; i++) {
			if (ToDo.toDoList[i] != null && !ToDo.toDoList[i].getDone()) {
				items.add(ToDo.toDoList[i]);
			}
		}
		ArrayList <String> finalList = new ArrayList<String>();
		// Calculated grid
		// Pretty much anything that has a due date of NOW is added first
		// Then todos are added by importance and due date
		int [][] findArray = 	{{0, 1, 10, 7}, {0, 1, 7, 3}, {0, 1, 3, 0}, 
								 {1, 4, 10, 7}, {1, 3, 7, 3}, {1, 2, 3, 0},
								 {4, 8, 10, 7}, {3, 6, 7, 3}, {2, 4, 3, 0},
								 				{6, 8, 7, 3}, {4, 8, 3, 0},
				 				 {8,15, 10, 7}, {8,12, 7, 3},
				 				 {15,59,10, 7}, {12,59,7, 3},
				 				 {8, 59, 3, 0}
								};
		
		for (int i = 0; i < findArray.length; i++){
			for (int j = 0; j < items.size(); j++) {
				if (items.get(j).getDueDate() >= findArray[i][0] &&
					items.get(j).getDueDate() < findArray[i][1] &&
					items.get(j).getImportance() <= findArray[i][2] &&
					items.get(j).getImportance() > findArray[i][3]){
					finalList.add(items.get(j).getTitle()); 
				}
			}
		}
		String [] list = finalList.toArray(new String[finalList.size()]);
		return list;
	}
}
