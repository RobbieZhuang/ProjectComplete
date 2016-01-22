package toDoPackage;

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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;

import mainPackage.Program;
import net.miginfocom.swing.MigLayout;

public class TasksSPClass {

	static JPanel cklstP;

	static float[][] tasks;

	static JButton newToDoB;
	static JButton doneToDoB;
	static ChartPanel cp;
	static JPanel newToDoP;
	static JTextField newTitleT;
	static JTextArea newDescripA;
	static JSlider impS;
	static JSpinner dueS;
	static java.awt.Point p;
	static int[] sizes = new int[1000];
	static Paint[] colors = new Paint[1000];
	static int[] shapes = new int[1000];

	static class newToDoLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addNewToDoPanel();
			newToDoB.setEnabled(false);
		}
	}

	static class addToDoLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addToDoInfo();
			newToDoB.setEnabled(true);
		}
	}
	
	public static JPanel initiateScatterPlot() throws IOException {
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

	public static int trackColour() {
		PointerInfo pointer;
		pointer = MouseInfo.getPointerInfo();
		p = pointer.getLocation();
		Robot r;
		try {
			System.out.println("trying...");
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
		System.out.println(p);
		return -1;
	}

	public static class Handler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			int index = trackColour();
			if (index != -1) {
				String title = ToDo.toDoList[index].getTitle();
				String descrip = ToDo.toDoList[index].getDescription();
				System.out.println("Task: " + title);
				System.out.println("Description: " + descrip);
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

		// http://stackoverflow.com/questions/4369077/how-do-i-call-an-actionlistener-while-the-slider-is-moving-not-just-when-i-let
		impS.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				System.out.println((((JSlider) ce.getSource()).getValue()));
				// impP.add(new JLabel(String.valueOf(((JSlider)
				// ce.getSource()).getValue())));
				// impP.repaint();
			}
		});

		/// Due Date TextField
		JPanel dueP = new JPanel(new BorderLayout());
		JLabel dueL = new JLabel("Select the amount of days until due.");
		dueS = new JSpinner();
		dueS.setValue(3);
		dueP.add(dueL, BorderLayout.NORTH);
		dueP.add(dueS, BorderLayout.CENTER);

		doneToDoB = new JButton("Done");
		doneToDoB.addActionListener(new addToDoLis());

		// Initiation of newToDoP
		newToDoP = new JPanel(new MigLayout("flowy", "[grow,fill]", "[][][][pref!][][][]"));
		// newToDoP.setPreferredSize(new Dimension(250, 400));
		newToDoP.add(newTitleP);
		newToDoP.add(new JLabel("Description"));
		newToDoP.add(newDescripSP);
		newToDoP.add(impP);
		newToDoP.add(dueP);
		newToDoP.add(doneToDoB);
		cklstP.add(newToDoP, BorderLayout.CENTER);
		Program.window.repaint();
	}

	public static void addToDoInfo() {
		cklstP.remove(newToDoP);
		String title = newTitleT.getText();
		String descrip = newDescripA.getText();
		int i = impS.getValue();
		System.out.println("Importance " + i);
		int dD = (int) dueS.getValue();
		System.out.println("DueDate " + dD);
		int index = ToDo.lowestIndex();
		ToDo.toDoList[index] = new ToDo(title, descrip, i, dD);
		ToDo.addColor(index);

		try {
			addScatterPlot();
		} catch (IOException e) {

		}

		Program.window.repaint();
	}

	public static void populate() {
		tasks = new float[ToDo.toDoList.length][2];
		for (int i = 0; i < tasks.length; i++) {

			if (ToDo.toDoList[i] != null) {
				System.out.println("From SP" + i + " " + ToDo.toDoList[i].getDone());
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
}
