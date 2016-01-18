package taskScatterPlotPackage;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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

public class TasksSPClass{
	
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
	
	static int [] sizes = new int[100];
	static Paint [] colors = new Paint[100];
	static int [] shapes = new int[100];
	static class newToDoLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addNewToDoPanel();
			newToDoB.setEnabled(false);
		}
	}
	static class addToDoLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addToDoInfo();
			newToDoB.setEnabled(true);
		}
	}
	
	public static JPanel initiateScatterPlot() throws IOException{
		
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

	public static int trackColour(){
		PointerInfo pointer;
		pointer = MouseInfo.getPointerInfo();
		java.awt.Point p = pointer.getLocation();
		Robot r;
		try {
			r = new Robot();
			r.delay(100);
			p = MouseInfo.getPointerInfo().getLocation();
			Paint color = r.getPixelColor((int)p.getX(), (int)p.getY());
			for (int i = 0; i < ToDo.toDos.length; i++){
				if (ToDo.toDos[i] != null && ToDo.toDos[i].getColour() != null){
					if (color.equals(ToDo.toDos[i].getColour())){
						return i;
					}
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static class Handler implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent event){
			int index = trackColour();
			if (index != -1){
				// Place to add new jframe! YAY >:(
				System.out.println("Task: " + ToDo.toDos[index].getTitle());
			}
		}
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void addScatterPlot() throws IOException{
		populate();
		
		NumberAxis domainAxis = new NumberAxis("Due Date");
        //domainAxis.setAutoRangeIncludesZero(false);
        NumberAxis rangeAxis = new NumberAxis("Importance");
        //rangeAxis.setAutoRangeIncludesZero(false);
        
		ExtendedFastScatterPlot plot = new ExtendedFastScatterPlot(tasks, domainAxis, rangeAxis, sizes, colors, shapes);
		plot.setBackgroundImage(ImageIO.read(new File("res/MR.jpg")));
		JFreeChart taskPlot = new JFreeChart(plot);
        
		cp = new ChartPanel(taskPlot, true);
		
		Handler MouseHandler = new Handler();
		cp.addMouseListener(MouseHandler);
		
		cklstP.add(cp, BorderLayout.CENTER);
	}
	
	public static void addNewToDoPanel(){
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
		//newDescripSP.setPreferredSize(new Dimension(300, 75));
		
		// Importance Slider Implementation
		final JPanel impP = new JPanel(new BorderLayout());
		JLabel impL = new JLabel("Importance");
		impS = new JSlider(1, 10);
		impP.add(impL, BorderLayout.NORTH);
		impP.add(impS, BorderLayout.CENTER);
		
		//http://stackoverflow.com/questions/4369077/how-do-i-call-an-actionlistener-while-the-slider-is-moving-not-just-when-i-let
		impS.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        	System.out.println((((JSlider) ce.getSource()).getValue()));
	            //impP.add(new JLabel(String.valueOf(((JSlider) ce.getSource()).getValue())));
	            //impP.repaint();
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
		//newToDoP.setPreferredSize(new Dimension(250, 400));
		newToDoP.add(newTitleP);
		newToDoP.add(new JLabel("Description"));
		newToDoP.add(newDescripSP);
		newToDoP.add(impP);
		newToDoP.add(dueP);
		newToDoP.add(doneToDoB);
		cklstP.add(newToDoP, BorderLayout.CENTER);
		Program.window.repaint();
	}
	
	public static void addToDoInfo(){
		cklstP.remove(newToDoP);
		String title = newTitleT.getText();
		String descrip = newDescripA.getText();
		int i = impS.getValue();
		int dD = (int) dueS.getValue();
		int index = ToDo.lowestIndex();
		ToDo.toDos[index] = new ToDo(title, descrip, i, dD);
		ToDo.addColor(index);
		
		try {
			addScatterPlot();
		} catch (IOException e) {
		}
		
		Program.window.repaint();
	}
	
	public static void populate(){
		tasks = new float[ToDo.toDos.length][2];
		for (int i = 0; i < tasks.length; i++) {
			if (ToDo.toDos[i] != null && ToDo.toDos[i].getDone() == false){
				tasks[i][0] = ToDo.toDos[i].getDueDate();
				tasks[i][1] = ToDo.toDos[i].getImportance();
			}
		}
		for (int i = 0; i < sizes.length; i++){
        	sizes[i] = 20;
        }
		for (int i = 0; i < colors.length; i++){
        	if (ToDo.toDos[i] != null && ToDo.toDos[i].getColour() != null){
        		colors[i] = ToDo.toDos[i].getColour();
        	}
        }
		for (int i = 0; i < shapes.length; i++){
        	shapes[i] = 10;
        }
		
	}
}
