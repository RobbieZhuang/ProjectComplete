package dailyPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Login.LoginClass;
import mainPackage.Program;
import net.miginfocom.swing.MigLayout;
import statusPackage.Status;

public class DailyClass {

	static JScrollPane jSP; // JScrollPane
	static JPanel dayPanel; // Main Panel
	static JPanel dayTaskP; // Tasks (updated after every user interaction)
	// For the creation of a new daily task
	public static JButton newDailyB; // Daily Button
	static JPanel newDailyP; // New Daily Panel (creates a new one when user
								// clicks the newDailyB button)
	static JPanel upDailyP;
	// Swing tools to get user information
	static JTextField newTitleT;
	static JTextArea newDescripA;
	static JTextArea cklstA;
	static JSlider diffS;
	// Shared variables for adding new daily
	static String title, descrip;
	static String[] checklist, daysOfTheWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	static int diff;
	static boolean complete;
	static JButton completeB;

	static JButton[] repeatButtons = new JButton[7];
	static JButton[] upRepeatButtons = new JButton[7];
	static boolean[] repeat = { true, true, true, true, true, true, true };
	
	static JButton[] cklistButtons;

	public static JScrollPane initiateDailyPanel() throws FileNotFoundException {

		dayPanel = new JPanel();
		dayPanel.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		// dayPanel.setBackground(Color.DARK_GRAY);
		dayPanel.setLayout(new BorderLayout());

		JPanel titleP = new JPanel();
		titleP.setLayout(new BorderLayout());

		JLabel titleL = new JLabel("Dailies");
		// titleL.setForeground(Color.BLACK);
		titleL.setFont(new Font("Century", Font.PLAIN, 20));
		// titleL.setBackground(Color.DARK_GRAY);
		titleL.setHorizontalAlignment(SwingConstants.CENTER);

		newDailyB = new JButton("New Daily");
		newDailyB.addActionListener(new newDailyBLis());

		titleP.add(titleL, BorderLayout.CENTER);
		titleP.add(newDailyB, BorderLayout.SOUTH);
		dayPanel.add(titleP, BorderLayout.NORTH);

		updateDailyPanel();

		jSP = new JScrollPane(dayPanel);
		jSP.setPreferredSize(new Dimension(350, 450));

		return jSP;
	}

	// http://stackoverflow.com/questions/43044/algorithm-to-randomly-generate-an-aesthetically-pleasing-color-palette
	public static Color generateRandomColor() {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		Color mix = new Color(255, 255, 255);
		// mix the color
		if (mix != null) {
			red = (red + mix.getRed()) / 2;
			green = (green + mix.getGreen()) / 2;
			blue = (blue + mix.getBlue()) / 2;
		}

		Color color = new Color(red, green, blue);
		return color;
	}

	// Load information into JPanels on dailyPanel
	// Updates the dailyPanel
	public static void updateDailyPanel() {
		if (newDailyP != null && newDailyP.getParent() == dayPanel) {
			dayPanel.remove(newDailyP);
		} else if (upDailyP != null && upDailyP.getParent() == dayPanel) {
			dayPanel.remove(upDailyP);
		}
		
		// Contains all the daily tasks in jcheckboxes
		dayTaskP = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		dayTaskP.setLayout(gbl);
		int numOfDailies = 0;
		Daily.destroyEmpty();
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (Daily.dayList[i] != null) {
				numOfDailies++;
			}
		}
		cklistButtons = new JButton[numOfDailies];
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (Daily.dayList[i] != null) {
				JPanel taskP = new JPanel(new BorderLayout());

				taskP.setPreferredSize(new Dimension(325, 30));
				taskP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				Font f = new Font("Century", Font.PLAIN, 18);

				JCheckBox itemCB = new JCheckBox(Daily.dayList[i].getTitle(), Daily.dayList[i].getComplete());
				itemCB.addActionListener(new ExtendedJCheckBoxListener(i, Daily.dayList[i].getDifficulty()));
				itemCB.setFont(f);
				itemCB.setOpaque(false);

				//System.out.println(i + " " + Daily.dayList[i].getRepeat()[LoginClass.dayOfWeek()]);

				if (Daily.dayList[i].getRepeat()[LoginClass.dayOfWeek()] == false) {
					taskP.setBackground(Color.DARK_GRAY);
					itemCB.setForeground(Color.LIGHT_GRAY);
				} else {
					taskP.setBackground(generateRandomColor());
				}

				taskP.add(itemCB, BorderLayout.WEST);
				//System.out.println(taskP);
				cklistButtons[i] = new JButton("+");
				cklistButtons[i].setActionCommand("small");
				cklistButtons[i].setPreferredSize(new Dimension(40,20));

				cklistButtons[i].addActionListener(new ExtendedCloseUDListener(i));
				//cklistButtons[i].addActionListener(new ExtendedAddRemCklistPanel(i));
				taskP.add(cklistButtons[i], BorderLayout.EAST);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTHWEST;
				gbc.gridx = 1;
				// gbc.gridy = GridBagConstraints.RELATIVE;
				gbc.weighty = 1;
				dayTaskP.add(taskP, gbc);
			}
		}
		dayPanel.add(dayTaskP, BorderLayout.CENTER);

		if (Status.statsPanel != null){
			Status.updateDailyStatsPanel();
			System.out.println("Updated");
		}
		
		Program.window.repaint();
	}

	// User clicks newDailyB and this method is called
	// createNewDaily creates the new daily pane that users can edit to create a
	// daily
	
	public static void createNewDailyPanel() {
		dayPanel.remove(dayTaskP);

		// TextField implementation for title
		JPanel newTitleP = new JPanel(new BorderLayout());
		JLabel newTitleL = new JLabel("Title");
		newTitleT = new JTextField();
		newTitleP.add(newTitleL, BorderLayout.NORTH);
		newTitleP.add(newTitleT, BorderLayout.CENTER);

		// New description textarea
		JPanel newDescripP = new JPanel(new BorderLayout());
		newDescripA = new JTextArea();
		newDescripA.setLineWrap(true);
		newDescripP.add(newDescripA, BorderLayout.CENTER);
		JScrollPane newDescripSP = new JScrollPane(newDescripP);
		newDescripSP.setPreferredSize(new Dimension(300, 75));

		// JPanel for checklist
		JPanel cklstTitleP = new JPanel(new BorderLayout());
		JPanel cklstP = new JPanel(new BorderLayout());
		// checklist label
		JLabel cklstL = new JLabel("Checklist");
		cklstTitleP.add(cklstL);
		// cklstL.setForeground(Color.BLACK);
		cklstTitleP.add(cklstL, BorderLayout.CENTER);
		cklstA = new JTextArea("Enter one item/line");
		cklstA.setLineWrap(true);
		cklstP.add(cklstA, BorderLayout.CENTER);
		JScrollPane cklstSP = new JScrollPane(cklstP);
		cklstSP.setPreferredSize(new Dimension(300, 75));

		// Difficulty Slider Implementation
		JPanel diffP = new JPanel(new BorderLayout());
		JLabel diffL = new JLabel("Difficulty");
		// diffL.setForeground(Color.BLACK);
		diffS = new JSlider(1, 10);
		diffP.add(diffL, BorderLayout.NORTH);
		diffP.add(diffS, BorderLayout.CENTER);

		// Repeat buttons
		JPanel repeatP = new JPanel(new BorderLayout());
		JLabel repeatL = new JLabel("Select days to repeat on.");
		// repeatL.setForeground(Color.WHITE);
		JPanel daysOfWeekP1 = new JPanel(new GridLayout(1, 3));
		JPanel daysOfWeekP2 = new JPanel(new GridLayout(1, 4));
		// Reset repeat
		for (int i = 0; i < repeat.length; i++) {
			repeat[i] = true;
		}
		for (int i = 0; i < 3; i++) {
			repeatButtons[i] = new JButton(daysOfTheWeek[i]);
			if (repeat[i] == true) {
				repeatButtons[i].setActionCommand("repeat");
				repeatButtons[i].setForeground(Color.CYAN);
			} else {
				repeatButtons[i].setActionCommand("no repeat");
				repeatButtons[i].setForeground(Color.BLACK);
			}
			repeatButtons[i].addActionListener(new ExtendedRepeatBNewListener(i));
			daysOfWeekP1.add(repeatButtons[i]);
		}

		for (int i = 3; i < daysOfTheWeek.length; i++) {
			repeatButtons[i] = new JButton(daysOfTheWeek[i]);
			if (repeat[i] == true) {
				repeatButtons[i].setActionCommand("repeat");
				repeatButtons[i].setForeground(Color.CYAN);
			} else {
				repeatButtons[i].setActionCommand("no repeat");
				repeatButtons[i].setForeground(Color.BLACK);
			}
			repeatButtons[i].addActionListener(new ExtendedRepeatBNewListener(i));
			daysOfWeekP2.add(repeatButtons[i]);
		}

		repeatP.add(repeatL, BorderLayout.NORTH);
		JPanel repeatDaysP = new JPanel(new BorderLayout());
		repeatDaysP.add(daysOfWeekP1, BorderLayout.NORTH);
		repeatDaysP.add(daysOfWeekP2, BorderLayout.SOUTH);
		repeatP.add(repeatDaysP, BorderLayout.CENTER);

		completeB = new JButton("Done");
		completeB.addActionListener(new newCompBLis());

		// Initiation of newDailyP + foreground and background colours
		newDailyP = new JPanel(new MigLayout("flowy", "[fill]", "[][][][pref!][][][]"));
		newDailyP.setPreferredSize(new Dimension(250, 400));
		// newDailyP.setBackground(Color.DARK_GRAY);
		// newDailyP.setForeground(Color.WHITE);
		newDailyP.add(newTitleP);
		newDailyP.add(new JLabel("Description"));
		newDailyP.add(newDescripSP);
		newDailyP.add(cklstTitleP);
		newDailyP.add(cklstSP);
		newDailyP.add(diffP);
		newDailyP.add(repeatP);
		newDailyP.add(completeB);
		dayPanel.add(newDailyP, BorderLayout.CENTER);
		Program.window.repaint();
	}

	// Adds information from user input to a new Daily object and then to the
	// dayList array
	public static void addDailyInfo(int index) {
		//System.out.println(index);
		if (Daily.dayList[index] == null) {
			Daily.dayList[index] = new Daily();
			Daily.dayList[index].setComplete(false);
		}
		Daily.dayList[index].setTitle(newTitleT.getText());
		Daily.dayList[index].setDescription(newDescripA.getText());
		int cLength;

		if (cklstA.getText().trim().isEmpty()) {
			cLength = 0;
		} else {
			cLength = cklstA.getText().replaceAll("(?m)^[ \t]*\r?\n", "").split("\n").length;
		}
		String[] cklst = new String[cLength];
		// http://stackoverflow.com/questions/4123385/remove-all-empty-lines -->
		// Where I got the REGEX from
		StringTokenizer sk = new StringTokenizer(cklstA.getText().replaceAll("(?m)^[ \t]*\r?\n", ""));
		for (int i = 0; i < cklst.length; i++) {
			cklst[i] = (sk.nextToken());
		}
		Daily.dayList[index].setChecklist(cklst);
		// Just add all false :)
		boolean[] cklstDone = new boolean[cLength];
		Daily.dayList[index].setChecklistDone(cklstDone);
		Daily.dayList[index].setDifficulty(diffS.getValue());
		for (int i = 0; i < repeat.length; i++) {
			//System.out.println(repeat[i] + " ");
		}
		Daily.dayList[index].setRepeat(repeat);
	}

	// Removes a daily from the dayList arraylist
	public static void removeDaily(int i) {
		Daily.dayList[i] = null;
		Daily.destroyEmpty();
	}

	// Opens a new Jpanel for user to update dayList
	public static void updateDaily(final int index) {
		dayPanel.remove(dayTaskP);
		// TextField implementation for title
		JPanel newTitleP = new JPanel(new BorderLayout());
		JLabel newTitleL = new JLabel("Title");
		newTitleT = new JTextField(Daily.dayList[index].getTitle());
		newTitleP.add(newTitleL, BorderLayout.NORTH);
		newTitleP.add(newTitleT, BorderLayout.CENTER);

		// New description textarea
		JPanel newDescripP = new JPanel(new BorderLayout());
		newDescripA = new JTextArea(Daily.dayList[index].getDescription());
		newDescripA.setLineWrap(true);
		newDescripP.add(newDescripA, BorderLayout.CENTER);
		JScrollPane newDescripSP = new JScrollPane(newDescripP);
		newDescripSP.setPreferredSize(new Dimension(300, 75));

		// JPanel for checklist
		JPanel cklstTitleP = new JPanel(new BorderLayout());
		JPanel cklstP = new JPanel(new BorderLayout());
		// checklist label
		JLabel cklstL = new JLabel("Checklist");
		cklstTitleP.add(cklstL);
		// cklstL.setForeground(Color.BLACK);
		cklstTitleP.add(cklstL, BorderLayout.CENTER);
		cklstA = new JTextArea();
		String cklstS = "";
		for (int i = 0; i < Daily.dayList[index].getChecklist().length; i++) {
			if (i == Daily.dayList[index].getChecklist().length - 1) {
				cklstS += Daily.dayList[index].getChecklist()[i];
			} else {
				cklstS += Daily.dayList[index].getChecklist()[i] + "\r\n";
			}
		}
		cklstA.setText(cklstS);
		cklstA.setLineWrap(true);
		cklstP.add(cklstA, BorderLayout.CENTER);
		JScrollPane cklstSP = new JScrollPane(cklstP);
		cklstSP.setPreferredSize(new Dimension(300, 75));

		// Difficulty Slider Implementation
		JPanel diffP = new JPanel(new BorderLayout());
		JLabel diffL = new JLabel("Difficulty");
		// diffL.setForeground(Color.BLACK);
		diffS = new JSlider(1, 10);

		diffS.setValue(Daily.dayList[index].getDifficulty());
		diffP.add(diffL, BorderLayout.NORTH);
		diffP.add(diffS, BorderLayout.CENTER);

		// Repeat buttons
		JPanel repeatP = new JPanel(new BorderLayout());
		JLabel repeatL = new JLabel("Select days to repeat on.");
		// repeatL.setForeground(Color.WHITE);
		JPanel daysOfWeekP1 = new JPanel(new GridLayout(1, 3));
		JPanel daysOfWeekP2 = new JPanel(new GridLayout(1, 4));

		repeat = Daily.dayList[index].getRepeat();
		for (int i = 0; i < 3; i++) {
			upRepeatButtons[i] = new JButton(daysOfTheWeek[i]);
			if (repeat[i] == true) {
				upRepeatButtons[i].setActionCommand("repeat");
				upRepeatButtons[i].setForeground(Color.CYAN);
			} else {
				upRepeatButtons[i].setActionCommand("no repeat");
				upRepeatButtons[i].setForeground(Color.BLACK);
			}
			//System.out.println(i);
			upRepeatButtons[i].addActionListener(new ExtendedRepeatBUpListener(index, i));
			daysOfWeekP1.add(upRepeatButtons[i]);
		}

		for (int i = 3; i < daysOfTheWeek.length; i++) {
			upRepeatButtons[i] = new JButton(daysOfTheWeek[i]);
			if (repeat[i] == true) {
				upRepeatButtons[i].setActionCommand("repeat");
				upRepeatButtons[i].setForeground(Color.CYAN);
			} else {
				upRepeatButtons[i].setActionCommand("no repeat");
				upRepeatButtons[i].setForeground(Color.BLACK);
			}
			upRepeatButtons[i].addActionListener(new ExtendedRepeatBUpListener(index, i));
			daysOfWeekP2.add(upRepeatButtons[i]);
		}

		repeatP.add(repeatL, BorderLayout.NORTH);
		JPanel repeatDaysP = new JPanel(new BorderLayout());
		repeatDaysP.add(daysOfWeekP1, BorderLayout.NORTH);
		repeatDaysP.add(daysOfWeekP2, BorderLayout.SOUTH);
		repeatP.add(repeatDaysP, BorderLayout.CENTER);

		JPanel buttonP = new JPanel(new FlowLayout());
		JButton deleteB = new JButton("Delete");
		deleteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DailyClass.removeDaily(index);
				newDailyB.setEnabled(true);
				updateDailyPanel();
			}
		});
		completeB = new JButton("Done");
		completeB.addActionListener(new ExtendedUDListener(index));
		buttonP.add(deleteB);
		buttonP.add(completeB);
		// Initiation of upDailyP + foreground and background colours
		upDailyP = new JPanel(new MigLayout("flowy", "[fill]", "[][][][pref!][][][]"));
		upDailyP.setPreferredSize(new Dimension(250, 400));
		// upDailyP.setBackground(Color.DARK_GRAY);
		// upDailyP.setForeground(Color.WHITE);
		upDailyP.add(newTitleP);
		upDailyP.add(new JLabel("Description"));
		upDailyP.add(newDescripSP);
		upDailyP.add(cklstTitleP);
		upDailyP.add(cklstSP);
		upDailyP.add(diffP);
		upDailyP.add(repeatP);
		upDailyP.add(buttonP);
		dayPanel.add(upDailyP, BorderLayout.CENTER);
		Program.window.repaint();
	}
	
	
	static class newDailyBLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newDailyB.setEnabled(false);
			createNewDailyPanel();
		}
	}

	static class newCompBLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addDailyInfo(99);
			Daily.destroyEmpty();
			updateDailyPanel();
			newDailyB.setEnabled(true);
			Program.window.repaint();
		}
	}
}
