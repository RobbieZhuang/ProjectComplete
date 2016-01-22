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
	public static JScrollPane jSP;
	public static JPanel p1;
	/**
	 * @wbp.parser.entryPoint
	 */
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
		System.out.println("Length " + optDailies.length);
		for (int i = 0; i < optDailies.length; i++) {
			JLabel dailyLabel = new JLabel((i + 1) + ") " + optDailies[i]);
			dailyLabel.setPreferredSize(new Dimension(450, 40));
			dailyLabel.setFont(new Font("Century", Font.PLAIN, 18));
			dailyLabel.setOpaque(true);
			dailyLabel.setBackground(DailyClass.generateRandomColor());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.gridx = 1;
			gbc.weighty = 1;
			dailyStatsP.add(dailyLabel, gbc);
		}
		jSP = new JScrollPane(dailyStatsP);
		jSP.setPreferredSize(new Dimension(475, 450));
		p1.add(jSP, BorderLayout.CENTER);
		statsPanel.add(p1, BorderLayout.WEST);

		jSP.repaint();
		p1.repaint();
		statsPanel.repaint();
		Program.window.revalidate();
		Program.window.repaint();
	}

	// Update the Todo schedule
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
			todoLabel.setPreferredSize(new Dimension(450, 30));
			todoLabel.setFont(new Font("Century", Font.PLAIN, 18));
			todoLabel.setOpaque(true);
			todoLabel.setBackground(DailyClass.generateRandomColor());
			toDoStatsP.add(todoLabel, gbc1);
		}
		p2.add(toDoStatsP, BorderLayout.CENTER);
		statsPanel.add(p2, BorderLayout.EAST);
		statsPanel.repaint();
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
			System.out.println(oDailies[i].getTitle() + " " + oDailies[i].getDifficulty());
		}
		return items;
	}
	
	// Optimizing the todos through with an array of coordinates indicating grids on the scatterplot
	// Task that has the highest priority will be in the first box
	public static String[] optimizedTodos(){
		ArrayList <ToDo> items = new ArrayList<ToDo>();
		for (int i = 0; i < ToDo.toDoList.length; i++) {
			if (ToDo.toDoList[i] != null && !ToDo.toDoList[i].getDone()) {
				items.add(ToDo.toDoList[i]);
			}
		}
		ArrayList <String> finalList = new ArrayList<String>();
		int [][] findArray = 	{{0, 1, 10, 7}, {0, 1, 7, 3}, {0, 1, 3, 1}, 
								 {1, 4, 10, 7}, {1, 3, 7, 3}, {1, 2, 3, 1},
								 {4, 8, 10, 7}, {3, 6, 7, 3}, {2, 4, 3, 1},
								 				{6, 8, 7, 3}, {4, 8, 1, 3},
				 				 {8,15, 10, 7}, {8,12, 7, 3},
				 				 {15,59,10, 7}, {12,59,7, 3},
				 				 {8, 59, 3, 1}
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
