package dailyPackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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

import actionListenersPackage.ExtendedCloseUDListener;
import actionListenersPackage.ExtendedJCheckBoxListener;
import actionListenersPackage.ExtendedUDListener;
import mainPackage.Program;
import net.miginfocom.swing.MigLayout;

public class DailyClass {

	static JScrollPane jSP; // JScrollPane
	static JPanel dayPanel; // Main Panel
	static JPanel dayTaskP; // Tasks (updated after every user interaction)
	// For the creation of a new daily task
	public static JButton newDailyB; // Daily Button
	static JPanel newDailyP; // New Daily Panel (creates a new one when user clicks the newDailyB button)
	static JPanel upDailyP;
	// Swing tools to get user information
	static JTextField newTitleT;
	static JTextArea newDescripA; 
	static JTextArea cklstA;
	static JSlider diffS; 
	static JButton [] repeatB = new JButton [7];
	//Shared variables for adding new daily
	static String title, descrip;
	static String [] checklist, daysOfTheWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	static boolean [] repeat = new boolean[7];
	static int diff;
	static boolean complete;
	static JButton completeB; 	
	
	public static JScrollPane initiateDailyPanel() throws FileNotFoundException{
		
		dayPanel = new JPanel();
		dayPanel.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		//dayPanel.setBackground(Color.DARK_GRAY);
		dayPanel.setLayout(new BorderLayout());
		
		JPanel titleP = new JPanel();
		titleP.setLayout(new BorderLayout());
		
		JLabel titleL = new JLabel("Dailies");
		//titleL.setForeground(Color.BLACK);
		titleL.setFont(new Font("Century", Font.PLAIN, 20));
		//titleL.setBackground(Color.DARK_GRAY);
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		
		newDailyB = new JButton("New Daily");
		newDailyB.addActionListener(new newDailyBLis());
		
		titleP.add(titleL, BorderLayout.CENTER);
		titleP.add(newDailyB,  BorderLayout.SOUTH);
		dayPanel.add(titleP, BorderLayout.NORTH);
		
		updateDailyPanel();
		
		jSP = new JScrollPane(dayPanel);
		jSP.setPreferredSize(new Dimension(350, 450));
		
		return jSP;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	// Load information into JPanels on dailyPanel
	// Updates the dailyPanel
	public static void updateDailyPanel(){
		if (newDailyP != null && newDailyP.getParent() == dayPanel){
			dayPanel.remove(newDailyP);
		}
		else if (upDailyP != null && upDailyP.getParent() == dayPanel){
	 		dayPanel.remove(upDailyP);
		}
 		
		// Contains all the daily tasks in jcheckboxes
		dayTaskP = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		dayTaskP.setLayout(gbl);
		
		/*
		for (int i = 0; i < Daily.dayList.length; i++) {
			System.out.println(Daily.dayList[i].getTitle());
		}
		*/
		
		for (int i = 0; i < Daily.dayList.length; i++){
			if (Daily.dayList[i] != null){
				JPanel taskP = new JPanel(new BorderLayout());
				taskP.setBackground(new Color((float)Math.random(), (float)Math.random(), 0));//(float)Math.random()));
		        taskP.setPreferredSize(new Dimension(325, 30));
		        taskP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				Font f = new Font("Century", Font.PLAIN, 18);
				
				System.out.println("Checkbox checked: " + Daily.dayList[i].getComplete());
				System.out.println("Title: " + Daily.dayList[i].getTitle());
				
				JCheckBox itemCB = new JCheckBox(Daily.dayList[i].getTitle(), Daily.dayList[i].getComplete());
				itemCB.addActionListener(new ExtendedJCheckBoxListener(i, Daily.dayList[i].getDifficulty()));
				itemCB.setFont(f);
				
				taskP.add(itemCB, BorderLayout.WEST);
				
				JButton dayTaskB = new JButton("+");
				//dayTaskB.setPreferredSize(new Dimension(40,20));
				dayTaskB.addActionListener(new ExtendedCloseUDListener(i));
				taskP.add(dayTaskB, BorderLayout.EAST);
				
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTHWEST;
				gbc.gridx = 1;
				//gbc.gridy = GridBagConstraints.RELATIVE;
				gbc.weighty = 1;
				dayTaskP.add(taskP, gbc);
			}
		}
		dayPanel.add(dayTaskP);
		dayPanel.repaint();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	// User clicks newDailyB and this method is called
	// createNewDaily creates the new daily pane that users can edit to create a daily
	public static void createNewDailyPanel(){
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
		//cklstL.setForeground(Color.BLACK);
		cklstTitleP.add(cklstL, BorderLayout.CENTER);
		cklstA = new JTextArea("Enter Checklist here, one item per line");
		cklstA.setLineWrap(true);
		cklstP.add(cklstA, BorderLayout.CENTER);
		JScrollPane cklstSP = new JScrollPane(cklstP);
		cklstSP.setPreferredSize(new Dimension(300, 75));
		
		// Difficulty Slider Implementation
		JPanel diffP = new JPanel(new BorderLayout());
		JLabel diffL = new JLabel("Difficulty");
		//diffL.setForeground(Color.BLACK);
		diffS = new JSlider(1, 10);
		diffP.add(diffL, BorderLayout.NORTH);
		diffP.add(diffS, BorderLayout.CENTER);
		
		// Repeat buttons
		JPanel repeatP = new JPanel(new BorderLayout());
		JLabel repeatL = new JLabel("Select days to repeat on.");
		//repeatL.setForeground(Color.WHITE);
		JPanel daysOfWeekP1 = new JPanel(new GridLayout(1, 3));
		JPanel daysOfWeekP2 = new JPanel(new GridLayout(1, 4));
		
		for (int i = 0; i < 3; i++) {
			daysOfWeekP1.add(new JButton(daysOfTheWeek[i]));
		}
		for (int i = 3; i < daysOfTheWeek.length; i++){
			daysOfWeekP2.add(new JButton(daysOfTheWeek[i]));
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
		//newDailyP.setBackground(Color.DARK_GRAY);
		//newDailyP.setForeground(Color.WHITE);
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
	
	//Adds information from user input to a new Daily object and then to the dayList array
	public static void addDailyInfo(int index){
		System.out.println(index);
		if (Daily.dayList[index] == null) {
			Daily.dayList[index] = new Daily();
			Daily.dayList[index].setComplete(false);
		}
		Daily.dayList[index].setTitle(newTitleT.getText());
		Daily.dayList[index].setDescription(newDescripA.getText());
		int cLength;
		
		if (cklstA.getText().trim().isEmpty()){
			cLength = 0;
		}
		else{
			cLength = cklstA.getText().replaceAll("(?m)^[ \t]*\r?\n", "").split("\n").length;
		}
		String [] cklst = new String[cLength];
		StringTokenizer sk = new StringTokenizer(cklstA.getText().replaceAll("(?m)^[ \t]*\r?\n", "")); //http://stackoverflow.com/questions/4123385/remove-all-empty-lines --> Where I got the REGEX from
		for (int i = 0; i < cklst.length; i++){
			cklst[i] = (sk.nextToken());
		}
		Daily.dayList[index].setChecklist(cklst);
		Daily.dayList[index].setDifficulty(diffS.getValue());
		// Figure out how to set buttons for repeat
		//Daily.dayList[index].setRepeat(repeatArray);
	}
	
	// Removes a daily from the dayList arraylist
	public static void removeDaily(int i){
		Daily.dayList[i] = null;
	}
	
	// Opens a new Jpanel for user to update dayList
	public static void updateDaily(int index){
		dayPanel.remove(dayTaskP);
		// TextField implementation for title
		JPanel newTitleP = new JPanel(new BorderLayout());
		JLabel newTitleL = new JLabel("Title");
		newTitleT = new JTextField(	Daily.dayList[index].getTitle());
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
		//cklstL.setForeground(Color.BLACK);
		cklstTitleP.add(cklstL, BorderLayout.CENTER);
		cklstA = new JTextArea();
		String cklstS = "";
		for (int i = 0; i < Daily.dayList[index].getChecklist().length; i++) {
			if (i == Daily.dayList[index].getChecklist().length-1){
				cklstS += Daily.dayList[index].getChecklist()[i];
			}
			else{
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
		//diffL.setForeground(Color.BLACK);
		diffS = new JSlider(1, 10);
		diffP.add(diffL, BorderLayout.NORTH);
		diffP.add(diffS, BorderLayout.CENTER);
		
		// Repeat buttons
		JPanel repeatP = new JPanel(new BorderLayout());
		JLabel repeatL = new JLabel("Select days to repeat on.");
		//repeatL.setForeground(Color.WHITE);
		JPanel daysOfWeekP1 = new JPanel(new GridLayout(1, 3));
		JPanel daysOfWeekP2 = new JPanel(new GridLayout(1, 4));
		
		for (int i = 0; i < 3; i++) {
			daysOfWeekP1.add(new JButton(daysOfTheWeek[i]));
		}
		for (int i = 3; i < daysOfTheWeek.length; i++){
			daysOfWeekP2.add(new JButton(daysOfTheWeek[i]));
		}
		
		repeatP.add(repeatL, BorderLayout.NORTH);
		JPanel repeatDaysP = new JPanel(new BorderLayout());
		repeatDaysP.add(daysOfWeekP1, BorderLayout.NORTH);
		repeatDaysP.add(daysOfWeekP2, BorderLayout.SOUTH);
		repeatP.add(repeatDaysP, BorderLayout.CENTER);
		
		completeB = new JButton("Done");
		completeB.addActionListener(new ExtendedUDListener(index));
		
		// Initiation of upDailyP + foreground and background colours
		upDailyP = new JPanel(new MigLayout("flowy", "[fill]", "[][][][pref!][][][]"));
		upDailyP.setPreferredSize(new Dimension(250, 400));
		//upDailyP.setBackground(Color.DARK_GRAY);
		//upDailyP.setForeground(Color.WHITE);
		upDailyP.add(newTitleP);
		upDailyP.add(new JLabel("Description"));
		upDailyP.add(newDescripSP);
		upDailyP.add(cklstTitleP);
		upDailyP.add(cklstSP);
		upDailyP.add(diffP);
		upDailyP.add(repeatP);
		upDailyP.add(completeB);
		dayPanel.add(upDailyP, BorderLayout.CENTER);
		Program.window.repaint();
	}

	static class newDailyBLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			newDailyB.setEnabled(false);
			createNewDailyPanel();
		}
	}
	
	static class newCompBLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addDailyInfo(Daily.lowestIndex());
			updateDailyPanel();
			newDailyB.setEnabled(true);
		}
	}
}
