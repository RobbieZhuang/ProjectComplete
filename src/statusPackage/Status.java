package statusPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import dailyPackage.Daily;
import mainPackage.Program;
import toDoPackage.ToDo;
import javax.swing.SwingConstants;

public class Status {
	static JPanel statsPanel;
	static JPanel dailyStatsP;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static JPanel initiateStatsPanel() {
		statsPanel = new JPanel(new BorderLayout());
		statsPanel.setPreferredSize(new Dimension(1080, 600));
		updateDailyStatsPanel();
		updateToDoStatsPanel();
		return statsPanel;
	}

	public static void updateDailyStatsPanel() { 	// Error with updating the panel
		if (dailyStatsP != null && dailyStatsP.getParent() == statsPanel) {
			statsPanel.remove(dailyStatsP);
		}
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		JLabel titleL = new JLabel("List of things to do today.");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Century", Font.PLAIN, 24));
		p1.add(titleL, BorderLayout.NORTH);

		dailyStatsP = new JPanel(new GridBagLayout());
		String[] optDailies = optimizedDailies();
		System.out.println(optDailies.length);
		for (int i = 0; i < optDailies.length; i++) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.gridx = 1;
			gbc.weighty = 1;
			JLabel jl = new JLabel((i + 1) + ") " + optDailies[i]);
			jl.setFont(new Font("Century", Font.PLAIN, 18));
			jl.setOpaque(true);
			jl.setBorder(new LineBorder(new Color((float) Math.random(), (float) Math.random(),
					0 /* (float)Math.random())); */), 3));

			dailyStatsP.add(jl, gbc);
		}
		p1.add(dailyStatsP, BorderLayout.CENTER);

		statsPanel.add(p1, BorderLayout.WEST);
		statsPanel.repaint();
		Program.window.repaint();
	}

	public static void updateToDoStatsPanel() {
		JPanel toDoStatsP = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new BorderLayout());
		JLabel titleLL = new JLabel("Recommended order of tasks:");
		titleLL.setFont(new Font("Century", Font.PLAIN, 24));
		p2.add(titleLL, BorderLayout.NORTH);

		for (int i = 0; i < ToDo.toDos.length; i++) {
			if (ToDo.toDos[i] != null) {
				GridBagConstraints gbc1 = new GridBagConstraints();
				gbc1.anchor = GridBagConstraints.NORTHWEST;
				gbc1.gridx = 1;
				gbc1.weighty = 1;
				JLabel jl = new JLabel((i + 1) + ") " + ToDo.toDos[i].getTitle());
				jl.setFont(new Font("Century", Font.PLAIN, 18));
				jl.setOpaque(true);
				jl.setBorder(new LineBorder(new Color((float) Math.random(), (float) Math.random(),
						0 /* (float)Math.random())); */), 3));

				toDoStatsP.add(jl, gbc1);
			}
		}
		p2.add(toDoStatsP, BorderLayout.CENTER);
		statsPanel.add(p2, BorderLayout.EAST);
		statsPanel.repaint();
		Program.window.repaint();
	}

	public static String[] optimizedDailies() {
		int size = 0;
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (Daily.dayList[i] != null) {
				size++;
			}
		}
		Daily[] oDailies = new Daily[size];
		Daily.destroyEmpty();
		for (int i = 0; i < oDailies.length; i++) {
			oDailies[i] = Daily.dayList[i];
		}
		String[] items = new String[size];
		boolean swapped = true;
		while (swapped) {
			swapped = false;
			int n = oDailies.length;
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