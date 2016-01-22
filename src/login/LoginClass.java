package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainPackage.Program;

public class LoginClass {
	static JFrame window;
	static JFrame newU;
	/**
	 * @wbp.parser.entryPoint
	 */
	// Welcome window
	public static void welcomeWindow() {
		// Borders removed, transparency set, background colour set
		window = new JFrame("Project:Complete");
		window.setUndecorated(true);
		window.setOpacity(0.92f);
		window.setBackground(Color.DARK_GRAY);
		window.setSize(600, 510);
		window.setAlwaysOnTop(true);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel overlordP = new JPanel();
		overlordP.setBackground(Color.DARK_GRAY);
		JLabel titleL = new JLabel("Project: Complete");
		titleL.setForeground(Color.WHITE);
		titleL.setFont(new Font("Century", Font.PLAIN, 24));
		overlordP.add(titleL, BorderLayout.NORTH);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setLayout(new FlowLayout());

		JLabel userL = new JLabel("Username:");
		userL.setForeground(Color.WHITE);
		userL.setFont(new Font("Century", Font.PLAIN, 16));
		topPanel.add(userL);
		final JTextField userTF = new JTextField(15);
		userTF.setFont(new Font("Century", Font.PLAIN, 16));
		topPanel.add(userTF);
		JButton newUserB = new JButton("New User");
		newUserB.setForeground(Color.BLACK);
		newUserB.setBackground(Color.LIGHT_GRAY);
		newUserB.setFont(new Font("Century", Font.PLAIN, 16));
		newUserB.setPreferredSize(new Dimension(125, 25));
		// Add action listener for creating a new user
		newUserB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userTF.repaint();
				String user = userTF.getText();
				if(FileClass.findUser(user) || user.equals("")){
					System.out.println("Username already taken or nothing was entered.");
				}
				else{
					try {
						FileClass.newUser(user);
					} 
					catch (FileNotFoundException e2) {}
					FileClass.importAll(user);
					window.dispose();
					try {
						Program.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		topPanel.add(newUserB);
		
		JPanel loginButtonP = new JPanel(new BorderLayout());
		loginButtonP.setBackground(Color.DARK_GRAY);
		JButton loginB = new JButton("Log In");
		loginB.setForeground(Color.BLACK);
		loginB.setBackground(Color.LIGHT_GRAY);
		loginB.setFont(new Font("Century", Font.PLAIN, 16));
		loginB.setPreferredSize(new Dimension(5, 25));
		// Add action listener to log in
		loginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = userTF.getText();
				if (FileClass.findUser(user)){
					FileClass.importAll(user);
					window.dispose();
					try {
						Program.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else{
					System.out.println("User not found: " + user);
				}
			}
		});

		loginButtonP.add(new JLabel("                                                       "), BorderLayout.WEST);
		loginButtonP.add(new JLabel("                                                       "), BorderLayout.EAST);
		loginButtonP.add(loginB, BorderLayout.CENTER);

		Icon icon = new ImageIcon("res/Default.gif");
		JLabel label = new JLabel(icon);
		label.setBackground(Color.DARK_GRAY);
		panel.add(label, BorderLayout.NORTH);

		panel.add(topPanel, BorderLayout.CENTER);
		panel.add(loginButtonP, BorderLayout.SOUTH);
		overlordP.add(panel, BorderLayout.CENTER);
		window.getContentPane().add(overlordP, BorderLayout.CENTER);
		window.setVisible(true);
	}
	
	public static int dayOfWeek() {
		// Get the day of the week in numerical format
		Date now = new Date();
		Calendar c = Calendar.getInstance();
        c.setTime(now);
		int day = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(day);
		if (day == 1){ day = 6; }
		else if (day == 2){ day = 7; }
		else { day -= 2; }
		return day;
	}
}
