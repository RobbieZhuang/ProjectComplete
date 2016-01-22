package todos;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;

import character.Character;
import mainPackage.Program;
import net.miginfocom.swing.MigLayout;
import optimization.Schedule;

public class TasksSPClass {

	// Various swing components declared
	static JPanel cklstP;
	static JButton newToDoB;
	static JButton doneToDoB;
	static ChartPanel cp;
	static JPanel newToDoP;
	static JTextField newTitleT;
	static JTextArea newDescripA;
	static JSlider impS;
	static JSpinner dueS;
	static java.awt.Point p;

	// Tasks array to store tasks
	static float[][] tasks;
	// Size array to store size of dots
	static int[] sizes = new int[1000];
	// Color array to store colour of dots
	static Paint[] colors = new Paint[1000];
	// Shape array to store shapes of dots (all circles)
	static int[] shapes = new int[1000];
	
	public static JPanel initiateScatterPlot() throws IOException {
		// Updates due dates on todos
		TasksSPClass.updateDueDates();
		// Initiate cklstP panel and add scatter plot
		cklstP = new JPanel(new BorderLayout());
		cklstP.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));

		JPanel topP = new JPanel(new BorderLayout());
		JLabel titleL = new JLabel("Advanced Checklist");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Century", Font.PLAIN, 20));

		newToDoB = new JButton("New ToDo");
		newToDoB.addActionListener(new newToDoLis());

		topP.add(titleL, BorderLayout.CENTER);
		topP.add(newToDoB, BorderLayout.EAST);
		cklstP.add(topP, BorderLayout.NORTH);

		addScatterPlot();
		cklstP.setPreferredSize(new Dimension(700, 450));
		trackColour();

		return cklstP;
	}

	// Check the date difference between this open and last open and subtract
	// from the due date. If difference makes the due date < 0, the due date becomes 0
	@SuppressWarnings("deprecation")
	public static void updateDueDates(){
		Date d = new Date();
		Date prevD = Character.user.getDate();
		int gap = d.getDate()-prevD.getDate();
		if (prevD.getDate() != d.getDate()){
			for (int i = 0; i < ToDo.toDoList.length; i++) {
				if (ToDo.toDoList[i] != null){
					int due = ToDo.toDoList[i].getDueDate();
					if (due-gap <= 0){
						ToDo.toDoList[i].setDueDate(0);
					}
					else{
						ToDo.toDoList[i].setDueDate(due-gap);
					}
				}
			}
		}
	}
	
	// Add the scatterplot to the checklist panel
	public static void addScatterPlot() throws IOException {
		if (cp != null && cp.getParent() == cklstP) {
			cklstP.remove(cp);
		}
		populate();

		NumberAxis domainAxis = new NumberAxis("Due Date");
		NumberAxis rangeAxis = new NumberAxis("Importance");

		domainAxis.setRange(0, 60);
		rangeAxis.setRange(0, 10);

		ExtendedFastScatterPlot plot = new ExtendedFastScatterPlot(tasks, domainAxis, rangeAxis, sizes, colors);
		JFreeChart taskPlot = new JFreeChart(plot);

		cp = new ChartPanel(taskPlot, true);
		cp.setDomainZoomable(false);
		cp.setRangeZoomable(false);
		cp.setPopupMenu(null);
		Handler MouseHandler = new Handler();
		cp.addMouseListener(MouseHandler);
		cp.repaint();
		cklstP.add(cp, BorderLayout.CENTER);
		cklstP.repaint();
	}

	// Add new to do panel initiated here and added to the cklstP
	public static void addNewToDoPanel() {
		cklstP.remove(cp);

		newToDoP = new JPanel();
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

		// Importance Slider Implementation
		final JPanel impP = new JPanel(new BorderLayout());
		JLabel impL = new JLabel("Importance");
		impS = new JSlider(1, 10);
		impP.add(impL, BorderLayout.NORTH);
		impP.add(impS, BorderLayout.CENTER);

		/// Due Date TextField
		JPanel dueP = new JPanel(new BorderLayout());
		JLabel dueL = new JLabel("Select the amount of days until due.");
		dueS = new JSpinner();
		SpinnerNumberModel model = new SpinnerNumberModel(5.0, 0.0, 59.0, 1.0);  
		dueS.setModel(model);
		dueP.add(dueL, BorderLayout.NORTH);
		dueP.add(dueS, BorderLayout.CENTER);

		doneToDoB = new JButton("Done");
		doneToDoB.addActionListener(new addToDoLis());

		// Initiation of newToDoP
		newToDoP = new JPanel(new MigLayout("flowy", "[grow,fill]", "[][][][pref!][][][]"));
		newToDoP.add(newTitleP);
		newToDoP.add(new JLabel("Description"));
		newToDoP.add(newDescripSP);
		newToDoP.add(impP);
		newToDoP.add(dueP);
		newToDoP.add(doneToDoB);
		cklstP.add(newToDoP, BorderLayout.CENTER);
		Program.window.repaint();
	}

	// Pulling inputed data and adding to toDoList array
	public static void addToDoInfo() {
		cklstP.remove(newToDoP);
		String title = newTitleT.getText();
		String descrip = newDescripA.getText();
		int i = impS.getValue();
		int dD = Integer.parseInt(dueS.getValue().toString().substring(0, dueS.getValue().toString().length()-2));
		int index = ToDo.lowestIndex();
		ToDo.toDoList[index] = new ToDo(title, descrip, i, dD);
		ToDo.addColor(index);

		try {
			addScatterPlot();
		} catch (IOException e) {

		}
		Schedule.updateTodoStatsPanel();
		Program.window.repaint();
	}

	// Add tasks from ToDo object array to tasks array
	public static void populate() {
		tasks = new float[ToDo.toDoList.length][2];
		for (int i = 0; i < tasks.length; i++) {

			if (ToDo.toDoList[i] != null) {
				if (ToDo.toDoList[i].getDone() != true) {
					tasks[i][0] = ToDo.toDoList[i].getDueDate();
					tasks[i][1] = ToDo.toDoList[i].getImportance();
				} else {
					tasks[i][0] = -1;
					tasks[i][1] = -1;
				}
			}
		}
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = 20;
		}
		for (int i = 0; i < colors.length; i++) {
			if (ToDo.toDoList[i] != null && ToDo.toDoList[i].getColour() != null) {
				colors[i] = ToDo.toDoList[i].getColour();
			}
		}
		for (int i = 0; i < shapes.length; i++) {
			shapes[i] = 10;
		}
	}

	// Colour tracking robot finds colour under the mouse click and returns a colour to the mouse handler
	public static int trackColour() {
		PointerInfo pointer;
		pointer = MouseInfo.getPointerInfo();
		p = pointer.getLocation();
		Robot r;
		try {
			r = new Robot();
			r.delay(10);
			p = MouseInfo.getPointerInfo().getLocation();
			Paint color = r.getPixelColor((int) p.getX(), (int) p.getY());

			for (int i = 0; i < ToDo.toDoList.length; i++) {
				if (ToDo.toDoList[i] != null && ToDo.toDoList[i].getColour() != null) {
					if (color.equals(ToDo.toDoList[i].getColour())) {
						return i;
					}
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Mouse listener handler, when the mouse is clicked at a certain colour, the new popup window will be activated
	public static class Handler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			int index = trackColour();
			if (index != -1) {
				String title = ToDo.toDoList[index].getTitle();
				String descrip = ToDo.toDoList[index].getDescription();
				TaskPopup.createPopup(index, title, descrip, p);
			}
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}
	}

	// New Todo actionlistener
	static class newToDoLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addNewToDoPanel();
			newToDoB.setEnabled(false);
		}
	}

	// When done adding new todo, this listener will be called
	static class addToDoLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addToDoInfo();
			newToDoB.setEnabled(true);
		}
	}
}
