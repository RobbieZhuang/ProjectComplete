package optimization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import character.Character;
import dailies.Daily;
import dailies.DailyClass;
import mainPackage.Program;
import todos.ToDo;
import javax.swing.SwingConstants;

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
		updateToDoStatsPanel();

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
			JLabel jl = new JLabel((i + 1) + ") " + optDailies[i]);
			jl.setPreferredSize(new Dimension(450, 40));
			jl.setFont(new Font("Century", Font.PLAIN, 18));
			jl.setOpaque(true);
			jl.setBackground(DailyClass.generateRandomColor());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.gridx = 1;
			gbc.weighty = 1;
			dailyStatsP.add(jl, gbc);
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

	public static void updateToDoStatsPanel() {
		JPanel toDoStatsP = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		JLabel titleLL = new JLabel("Recommended Order Of Todos");
		titleLL.setHorizontalAlignment(SwingConstants.CENTER);
		titleLL.setFont(new Font("Century", Font.PLAIN, 24));
		p2.add(titleLL, BorderLayout.NORTH);

		int index = 1;
		for (int i = 0; i < ToDo.toDoList.length; i++) {
			if (ToDo.toDoList[i] != null && !ToDo.toDoList[i].getDone()) {
				GridBagConstraints gbc1 = new GridBagConstraints();
				gbc1.anchor = GridBagConstraints.NORTHWEST;
				gbc1.gridx = 1;
				gbc1.weighty = 1;
				JLabel jl = new JLabel(index + ") " + ToDo.toDoList[i].getTitle());
				jl.setPreferredSize(new Dimension(450, 30));
				jl.setFont(new Font("Century", Font.PLAIN, 18));
				jl.setOpaque(true);
				jl.setBorder(new LineBorder(new Color((float) Math.random(), (float) Math.random(), 0), 3));

				toDoStatsP.add(jl, gbc1);
				index++;
			}
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
				System.out.println(Daily.dayList[i].getComplete());
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
}
