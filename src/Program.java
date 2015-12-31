import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;

public class Program {
	static JFrame window;
	static JPanel titlePanel;
	static JTextField titleText;
	static List points = new ArrayList();
	static JPanel tasksPanel;
	static JTabbedPane tabbedPane;
	static JPanel character;
	static JButton newDayB;
	static JPanel dayPanel;
	static int tasks = 0;
	
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
	
	static ArrayList<Daily> dayList = new ArrayList<Daily>();
	
	public static void main(String [] args) throws Exception{
		window = new JFrame("Project: Complete");
		window.setSize(1080,720);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		try {
			
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	// OS Based LookAndFeel
		    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    	
		    	/* 
		    	//Nimbus LookAndFeel
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		        */
		    }
		} catch (Exception e) {
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		
		window.getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		window.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblProjectComplete = new JLabel("Project: Complete");
		lblProjectComplete.setForeground(Color.WHITE);
		lblProjectComplete.setBackground(Color.GRAY);
		lblProjectComplete.setFont(new Font("Century", Font.PLAIN, 24));
		panel.add(lblProjectComplete);
				
		// Creates a tabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		window.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		// Adds the first pane called Tasks which contains dailies panel and checklist panel
		createTasksPane();
		
		// Adds the second pane called Schedule which contains the list of dailies and list of to-dos
		createSchedulePane();
		
		// Adds the third pane called Stats which contains graphs and charts on the user's dailies and to-dos
		createStatsPane();
		
		

		window.setVisible(true);
	}
	
	// Adds the first pane called Tasks which contains dailies panel and checklist panel
	public static void createTasksPane(){
		JPanel tasks = new JPanel();
		tasks.setLayout(new BorderLayout());
		
		dayPanel = new JPanel();
		tasks.add(dayPanel, BorderLayout.WEST);
		dayPanel.setLayout(new MigLayout("", "[368px]", "[30px][23px][23px][23px][23px]"));
		
		
		JPanel dayTitleP = new JPanel();
		dayPanel.add(dayTitleP, "flowx,cell 0 0");
		dayTitleP.setLayout(new GridLayout(0, 3));
		dayTitleP.add(new JLabel());
		
		JLabel label_1 = new JLabel("Dailies");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Century", Font.PLAIN, 24));
		label_1.setBackground(Color.DARK_GRAY);
		dayTitleP.add(label_1);
		
		newDayB = new JButton("New Daily");
		dayPanel.add(newDayB, "cell 0 0");
		
		newDayB.addActionListener(new newnewDayBListener());
		
		JPanel cklstPanel = new JPanel();
		tasks.add(cklstPanel, BorderLayout.EAST);

		character = new JPanel();
		tasks.add(character, BorderLayout.NORTH);
		
		tabbedPane.addTab("Tasks", null, tasks, null);
	}
	
	static class newnewDayBListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			createTask();
			newTask();
		}
	}
	
	// Ask user information from console add to ArrayList of dailies
	public static void createTask(){
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
		
		Daily d = new Daily(title, memo, checklist, diff, repeat);

		System.out.println(d.title);
		System.out.println(d.checklist.length);

		dayList.add(d);
	}
	
	public static void newTask(){
		for (int i = 0; i < dayList.size(); i++){
			JPanel task = new JPanel();
			task.setLayout(new GridLayout(4 + dayList.get(i).checklist.length, 1));
			task.add(new JLabel(dayList.get(i).title));
			task.add(new JLabel((dayList.get(i).memo)));
			for (int k = 0; k < dayList.get(i).checklist.length; k++) {
				task.add(new JCheckBox(dayList.get(i).checklist[k]));
			}
			task.add(new JLabel("Difficulty: " + dayList.get(i).difficulty));
			JPanel repeatP = new JPanel(new GridLayout(1, 7));
			
			String [] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
			for (int j = 0; j < daysOfTheWeek.length; j++){
				if (j > dayList.get(i).repeat.length ) repeatP.add(new JLabel(daysOfTheWeek[j] + " No "));
				else if (dayList.get(i).repeat[j] == true) repeatP.add(new JLabel(daysOfTheWeek[j] + " Yes "));
				else repeatP.add(new JLabel(daysOfTheWeek[j] + " No "));
			}
			task.add(repeatP);
			dayPanel.add(task,"flowx,cell 0 " + tasks);
			window.setVisible(true);
		}
		
	}
	public static void deleteTask(){
		
	}
	
	// Adds the second pane called Schedule which contains the list of dailies and list of to-dos
	public static void createSchedulePane(){
		JPanel schedule = new JPanel();
		schedule.setLayout(new BorderLayout());
		
		JPanel dayListPanel = new JPanel();
		schedule.add(dayListPanel, BorderLayout.WEST);
		
		JPanel cklstListPanel = new JPanel();
		schedule.add(cklstListPanel, BorderLayout.WEST);
		
		character = new JPanel();
		schedule.add(character, BorderLayout.NORTH);

		tabbedPane.addTab("Schedule & Optimization", null, schedule, null);
	}
	
	// Adds the third pane called Stats which contains graphs and charts on the user's dailies and to-dos
	public static void createStatsPane(){
		JPanel backPanel = new JPanel(new BorderLayout());
		JPanel stats = new JPanel();
		stats.setLayout(new GridLayout(2,2));
		
		JPanel dayStats = new JPanel();
		stats.add(dayStats);
		
		JPanel cklstStats = new JPanel();
		stats.add(cklstStats);
		
		JPanel genStats = new JPanel();
		stats.add(genStats);
		
		character = new JPanel();
		backPanel.add(character, BorderLayout.NORTH);
		backPanel.add(stats, BorderLayout.SOUTH);
		
		tabbedPane.addTab("Stats", null, backPanel, null);
	}
	
	/*
	public static void buildScatterPlot(){
		points.add(new Point2D.Float(100, 400));
        points.add(new Point2D.Float(200, 100));
        points.add(new Point2D.Float(300, 120));
        //points.add(new Point2D.Float(3, 10));
        // points.add(new Point2D.Float(4, 12));
        JPanel panel = new JPanel();
    	panel.setSize(500, 500);
    	panel{
            @Override
            public void paintComponent(Graphics g) {
                //g.translate(0, 0);
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g.setColor(Color.RED);
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                for (Iterator i = points.iterator(); i.hasNext();) {
                    Point2D.Float pt = (Point2D.Float) i.next();
                    Ellipse2D dot = new Ellipse2D.Float(pt.x - 1, pt.y - 1, 2, 2);
                    g2d.fill(dot);
                }
                g2d.dispose();
            }
        };
        tasksPanel.add(panel);
	}
	*/
}
