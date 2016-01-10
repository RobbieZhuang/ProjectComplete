import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;

public class DailyClass {

	static JScrollPane jSP; // JScrollPane
	static JPanel dayPanel; // Main Panel
	static JPanel dayTaskP; // Tasks (updated after every user interaction)
	// For the creation of a new daily task
	static JButton newDailyB; // Daily Button
	static JPanel newDailyP; // New Daily Panel (creates a new one when user clicks the newDailyB button)
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
	static JButton completeB;
	// ArrayLists storing the User's dailies and completed dailies --> To be updated into a file
	static ArrayList<Daily> dayList = new ArrayList<Daily>(); // Stores Dailies that need to be complete 
	static ArrayList<Daily> completedDayList = new ArrayList<Daily>();
	private static JTextField textField;
	
	static class Daily{
		String title;
		String memo;
		String [] checklist;
		int difficulty;
		boolean [] repeat = new boolean[7];
		boolean complete;
		
		Daily(String t, String m, String [] checklist, int d, boolean [] r, boolean comp){
			title = t;
			memo = m;
			this.checklist = checklist;
			difficulty = d;
			repeat = r;
			complete = comp;
		}
		
	}
	
	
	public static JScrollPane initiateDailyPanel(){
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
		
		updateDaily();
		
		jSP = new JScrollPane(dayPanel);
		jSP.setPreferredSize(new Dimension(350, 450));
		
		return jSP;
	}
	
	static class newDailyBLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			newDailyB.setEnabled(false);
			createNewDaily();
		}
	}
	
	static class newCompBLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addNewDaily();
			updateDaily();
			newDailyB.setEnabled(true);
		}
	}
	
	static class checkBLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() instanceof JButton){
				if (((JButton) e.getSource()).getText().equals("Done")){
					((JButton) e.getSource()).setText("Finish");
				}
				else if (((JButton) e.getSource()).getText().equals("Finish")){
					((JButton) e.getSource()).setText("Done");
				}
			}
		}
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	// Ask user information from console add to ArrayList of dailies
	public static void createNewDaily(){
		dayPanel.remove(dayTaskP);
		// TextField implementation for title
		JPanel newTitleP = new JPanel(new BorderLayout());
		newTitleT = new JTextField("Title", 40);
		newTitleP.add(newTitleT);
		
		// New description textarea
		newDescripA = new JTextArea("Description", 3, 40);
		newDescripA.setLineWrap(true);
		
		// JPanel for checklist
		JPanel cklstTitleP = new JPanel(new BorderLayout());
		JPanel cklstP = new JPanel(new BorderLayout());
		// checklist label
		JLabel cklstL = new JLabel("Checklist");
		cklstTitleP.add(cklstL);
		//cklstL.setForeground(Color.BLACK);
		cklstTitleP.add(cklstL, BorderLayout.CENTER);
		cklstA = new JTextArea("Enter Checklist here, one item per line", 3, 35);
		cklstA.setLineWrap(true);
		cklstP.add(cklstA, BorderLayout.CENTER);
		JScrollPane cklstSP = new JScrollPane(cklstP);
		cklstSP.setPreferredSize(new Dimension(325, 75));
		
		// Difficulty Slider Implementation
		JPanel diffP = new JPanel(new BorderLayout());
		JLabel diffL = new JLabel("Difficulty");
		//diffL.setForeground(Color.BLACK);
		diffS = new JSlider(1, 10);
		diffS.setSize(new Dimension(3, 40));
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
		newDailyP = new JPanel(new MigLayout("flowy"));
		//newDailyP.setBackground(Color.DARK_GRAY);
		//newDailyP.setForeground(Color.WHITE);
		newDailyP.add(newTitleP);
		newDailyP.add(newDescripA);
		newDailyP.add(cklstTitleP);
		newDailyP.add(cklstSP);
		newDailyP.add(diffP);
		newDailyP.add(repeatP);
		newDailyP.add(completeB);
		dayPanel.add(newDailyP, BorderLayout.CENTER);
		Program.window.setVisible(true);
	}
	
	public static void addNewDaily(){
		dayPanel.remove(newDailyP);
		jSP.repaint();
		title = newTitleT.getText();
		descrip = newDescripA.getText();
		String [] cklst = new String[cklstA.getLineCount()];
		StringTokenizer sk = new StringTokenizer(cklstA.getText(), "\n");
		
		diff = diffS.getValue();
		System.out.println(diff);
		Daily d = new Daily(title, descrip, cklst, diff, repeat, false);
		dayList.add(d);
	}
	public static void updateDaily(){
		dayTaskP = new JPanel();
		GridBagLayout gbl_dayTaskP = new GridBagLayout();
		gbl_dayTaskP.columnWidths = new int[]{0, 0};
		gbl_dayTaskP.rowHeights = new int[]{0, 0};
		gbl_dayTaskP.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_dayTaskP.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		dayTaskP.setLayout(gbl_dayTaskP);
		
		for (int i = 0; i < dayList.size(); i++){
			JPanel taskP = new JPanel(new BorderLayout());
			JButton dayTaskB = new JButton("Finish");
			dayTaskB.addActionListener(new checkBLis());
			taskP.add(dayTaskB, BorderLayout.WEST);
			taskP.add(new JLabel(dayList.get(i).title), BorderLayout.CENTER);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.gridx = 0;
			gbc.gridy = i;
			dayTaskP.add(taskP, gbc);
		}
		dayPanel.add(dayTaskP);
		dayPanel.repaint();
	}
	
}
