package mainPackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Login.FileClass;
import Login.LoginClass;
import characterPackage.CharacterClass;
import dailyPackage.DailyClass;
import taskScatterPlotPackage.TasksSPClass;

import java.awt.Component;
import javax.swing.Box;

/**
 * @author Robbie
 *
 */
public class Program {
	public static JFrame window;
	static JPanel titlePanel;
	static JTextField titleText;
	static List points = new ArrayList();
	static JPanel tasksPanel;
	static JTabbedPane tabbedPane;
	static JPanel characterPanel;
	static int tasks = 0;

	public static String username = "GOLDENHIPPO";
	
	public static void main(String [] args) throws Exception{
		
		FileClass.importDailies();
		
		window = new JFrame("Project: Complete");
		window.setSize(1080,800);
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
		
		JLabel ProjectCompleteL = new JLabel("Project: Complete");
		ProjectCompleteL.setForeground(Color.WHITE);
		ProjectCompleteL.setBackground(Color.GRAY);
		ProjectCompleteL.setFont(new Font("Century", Font.PLAIN, 24));
		panel.add(ProjectCompleteL);
				
		// Creates a tabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		window.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// initiates characterPanel from CharacterClass
		characterPanel = CharacterClass.initiatePanel();
		
		// Adds the first pane called Tasks which contains dailies panel and checklist panel
		createTasksPane();
		
		// Adds the second pane called Schedule which contains the list of dailies and list of to-dos
		createSchedulePane();
		
		// Adds the third pane called Stats which contains graphs and charts on the user's dailies and to-dos
		createStatsPane();
		
		// Sets the main JFrame to visible
		window.setVisible(true);
	}
	
	// Adds the first pane called Tasks which contains dailies panel and checklist panel
	public static void createTasksPane() throws IOException{
		JPanel backP = new JPanel(new BorderLayout());
		
		backP.add(DailyClass.initiateDailyPanel(), BorderLayout.WEST);
		
		backP.add(TasksSPClass.initiateScatterPlot(), BorderLayout.EAST);
		
		backP.add(CharacterClass.initiatePanel(), BorderLayout.NORTH);
		
		tabbedPane.addTab("Tasks", null, backP, null);
	}
	
	public static void deleteTask(){
		
	}
	
	// Adds the second pane called Schedule which contains the list of dailies and list of to-dos
	public static void createSchedulePane() throws IOException{
		
		JPanel schedule = new JPanel();
		schedule.setLayout(new BorderLayout());
		
		JPanel dayListPanel = new JPanel();
		schedule.add(dayListPanel, BorderLayout.WEST);
		
		//schedule.add(cklstListPanel, BorderLayout.WEST);
		schedule.add(characterPanel, BorderLayout.NORTH);

		tabbedPane.addTab("Schedule & Optimization", null, schedule, null);
	}
	
	// Adds the third pane called Stats which contains graphs and charts on the user's dailies and to-dos
	public static void createStatsPane() throws IOException{
		JPanel backP = new JPanel(new BorderLayout());
		JPanel stats = new JPanel();
		stats.setLayout(new GridLayout(2,2));
		
		JPanel dayStats = new JPanel();
		stats.add(dayStats);
		
		JPanel cklstStats = new JPanel();
		stats.add(cklstStats);
		
		JPanel genStats = new JPanel();
		stats.add(genStats);
		
		//backP.add(CharacterClass.characterPanel, BorderLayout.NORTH);
		backP.add(stats, BorderLayout.SOUTH);
		
		tabbedPane.addTab("Stats", null, backP, null);
	}

}
