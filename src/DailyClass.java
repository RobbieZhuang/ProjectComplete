import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class DailyClass {
	
	static ArrayList<Daily> dayList = new ArrayList<Daily>();
	static ArrayList<Daily> completedDayList = new ArrayList<Daily>();
	static JPanel taskP;
	static JButton newDayB;
	static JPanel dayPanel;
	
	static class Daily{
		String title;
		String memo;
		String [] checklist;
		int difficulty;
		boolean [] repeat = new boolean[7];
	
		Daily(String t, String m, String [] checklist, int d, boolean [] r){
			title = t;
			memo = m;
			this.checklist = checklist;
			difficulty = d;
			repeat = r;
		}
		
	}
	
	public static JPanel initiateDailyPanel(){
		
		dayPanel = new JPanel();
		dayPanel.setLayout(new MigLayout("", "[368px]", "[30px][23px][23px][23px][23px]"));
		
		JPanel dayTitleP = new JPanel();
		dayPanel.add(dayTitleP, "flowx,cell 0 0");
		dayTitleP.setLayout(new GridLayout(0, 3));
		dayTitleP.add(new JLabel());
		JLabel dayTitleL = new JLabel("Dailies");
		newDayB = new JButton("New Daily");
		newDayB.addActionListener(new newNewDayBListener());
		dayPanel.add(newDayB, "cell 0 0");
		
		dayTitleL.setForeground(Color.BLACK);
		dayTitleL.setFont(new Font("Century", Font.PLAIN, 24));
		dayTitleL.setBackground(Color.DARK_GRAY);
		dayTitleP.add(dayTitleL);
		
		return dayPanel;
		
	}
	
	static class newNewDayBListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			createNewDaily();
			//addNewDaily();
		}
	}
	
	// Ask user information from console add to ArrayList of dailies
	public static void createNewDaily(){
		String title, descrip;
		String [] checklist, daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		boolean [] repeat = new boolean[7];
		int diff;
		
		JTextField newTitleT = new JTextField("Title");
		TextArea newDescripA = new TextArea("Description");
		
		JLabel checklistL = new JLabel("Checklist");
		ArrayList <String> clElements = new ArrayList<String>();
		/*
		String input = null;
		do{
			JTextField elementF = new JTextField(20);
			input = elementF.getText();
			if (!input.isEmpty()){
				clElements.add(input);
			}
		}while(!input.isEmpty());
		
		checklist = (String[]) clElements.toArray();
		*/
		checklist = new String[0];
		JLabel diffL = new JLabel("Difficulty");
		JSlider diffS = new JSlider(0, 10);
		
		JLabel repeatL = new JLabel("Select days to repeat on.");
		
		JPanel daysOfWeekP = new JPanel(new GridLayout(1, 7));
		
		for (int i = 0; i < daysOfTheWeek.length; i++) {
			daysOfWeekP.add(new JButton(daysOfTheWeek[i]));
		}
		
		title = newTitleT.getText();
		descrip = newDescripA.getText();
		diff = diffS.getValue();
		
		Daily d = new Daily(title, descrip, checklist, diff, repeat);
		System.out.println(d.title);
		System.out.println(d.checklist.length);
		
		dayList.add(d);
		
		
		JPanel newDailyP = new JPanel(new GridLayout(7, 1));
		newDailyP.add(newTitleT);
		//newDailyP.add(newDescripA);
		newDailyP.add(checklistL);
		//newDailyP.add(comp)
		newDailyP.add(diffL);
		newDailyP.add(diffS);
		newDailyP.add(repeatL);
		newDailyP.add(daysOfWeekP);
		
		dayPanel.add(newDailyP, "cell 0 1, shrink");
		Program.window.setVisible(true);
		
		
		/*
		// Using Console to get information
		Scanner in = new Scanner(System.in);
		tasks ++;
		
		System.out.println("Title: ");
		String title = in.nextLine();
		System.out.println("Description: ");
		String memo = in.nextLine();
		System.out.println("Checklist separated by a comma\",\": ");
		String clString = in.nextLine();
		String [] checklist = clString.split(",");
		for (int i = 0; i < checklist.length; i++) {
			System.out.println(checklist[i]);
		}
		System.out.println("Difficulty 1-10: ");
		int diff = in.nextInt();
		boolean [] repeat = new boolean[7];
		System.out.println("String of T/F corresponding to day of the week beginning on Sunday (max length = 7): ");
		String s = in.next();

		for (int i = 0; i < s.length(); i++){
			if (s.toUpperCase().equals("T")){
				repeat[i] = true;
			}
		}
		*/
	}
	
	public static void addNewDaily(){
		taskP = new JPanel();
		dayPanel.removeAll();
		for (int i = 0; i < dayList.size(); i++){
			taskP.setLayout(new GridLayout(4 + dayList.get(i).checklist.length, 1));
			taskP.add(new JLabel(dayList.get(i).title));
			taskP.add(new JLabel((dayList.get(i).memo)));
			for (int k = 0; k < dayList.get(i).checklist.length; k++) {
				taskP.add(new JCheckBox(dayList.get(i).checklist[k]));
			}
			taskP.add(new JLabel("Difficulty: " + dayList.get(i).difficulty));
			JPanel repeatP = new JPanel(new GridLayout(1, 7));
			
			String [] daysOfTheWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
			for (int j = 0; j < daysOfTheWeek.length; j++){
				if (j > dayList.get(i).repeat.length ) repeatP.add(new JLabel(daysOfTheWeek[j] + " No "));
				else if (dayList.get(i).repeat[j] == true) repeatP.add(new JLabel(daysOfTheWeek[j] + " Yes "));
				else repeatP.add(new JLabel(daysOfTheWeek[j] + " No "));
			}
			taskP.add(repeatP);
			dayPanel.add(taskP, "cell 0 " + i+1 +", shrink");
			Program.window.setVisible(true);
		}
			
		
	}
	
}
