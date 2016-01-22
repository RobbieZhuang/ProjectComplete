package mainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
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

import character.Character;
import character.CharacterClass;
import dailies.DailyClass;
import login.FileClass;
import login.LoginClass;
import optimization.Status;
import todos.TasksSPClass;

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
	//static JPanel characterPanel;
	static int tasks = 0;

	public static void main(String[] args) throws Exception {
		LoginClass.welcomeWindow();
	}

	public static void start() throws Exception {

		window = new JFrame("Project: Complete");
		window.setSize(1080, 800);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		// http://stackoverflow.com/questions/9093448/do-something-when-the-close-button-is-clicked-on-a-jframe
		window.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					Character.user.setDate();
					FileClass.exportData(Character.user.getName().toUpperCase());
				} catch (FileNotFoundException e) {
				}
			}
		});

		try {

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				// OS Based LookAndFeel

				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				/*
				 * //Nimbus LookAndFeel if ("Nimbus".equals(info.getName())) {
				 * UIManager.setLookAndFeel(info.getClassName()); break; }
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
		//characterPanel = CharacterClass.initiatePanel();

		// Adds the first pane called Tasks which contains dailies panel and
		// checklist panel
		createTasksPane();

		// Adds the second pane called Schedule which contains the list of
		// dailies and list of to-dos
		createSchedulePane();

		// Adds the third pane called Stats which contains graphs and charts on
		// the user's dailies and to-dos
		// createStatsPane();

		
		// Check if all dailies are done from previous day and resets dailies
		DailyClass.checkDailies();
		// Sets the main JFrame to visible
		window.setVisible(true);
	}

	// Adds the first pane called Tasks which contains dailies panel and
	// checklist panel
	public static void createTasksPane() throws IOException {
		JPanel backP = new JPanel(new BorderLayout());

		backP.add(DailyClass.initiateDailyPanel(), BorderLayout.WEST);

		backP.add(TasksSPClass.initiateScatterPlot(), BorderLayout.EAST);

		backP.add(CharacterClass.initiatePanel(), BorderLayout.NORTH);

		tabbedPane.addTab("Tasks", null, backP, null);
	}

	// Adds the second pane called Schedule which contains the list of dailies
	// and list of to-dos
	public static void createSchedulePane() throws IOException {
		JPanel schedule = new JPanel(new BorderLayout());

		schedule.add(Status.initiateStatsPanel(), BorderLayout.WEST);

		tabbedPane.addTab("Optimization", null, schedule, null);
	}

	// Adds the third pane called Stats which contains graphs and charts on the
	// user's dailies and to-dos
	

}
