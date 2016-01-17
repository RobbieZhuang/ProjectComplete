package taskScatterPlotPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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

import dailyPackage.Daily;
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
	static JSpinner spinner;
	
	static ArrayList <Point> checkTasks = new ArrayList <Point>();
	static ArrayList <Point> finiCheckTasks = new ArrayList <Point>();
	
	static class newToDoLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addNewToDoPanel();
		}
	}
	static class addToDoLis implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addToDoInfo();
		}
	}
	public static JPanel initiateScatterPlot() throws IOException{
		cklstP = new JPanel(new BorderLayout());
		cklstP.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		populate();
		add();
		
		JPanel topP = new JPanel(new BorderLayout());
		JLabel titleL = new JLabel("Advanced Checklist");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Century", Font.PLAIN, 20));
		newToDoB = new JButton("New ToDo");
		newToDoB.addActionListener(new newToDoLis());
		topP.add(titleL, BorderLayout.CENTER);
		topP.add(newToDoB, BorderLayout.EAST);
		cklstP.add(topP, BorderLayout.NORTH);
		
		NumberAxis domainAxis = new NumberAxis("Due Date");
        //domainAxis.setAutoRangeIncludesZero(false);
        NumberAxis rangeAxis = new NumberAxis("Importance");
        //rangeAxis.setAutoRangeIncludesZero(false);
        
        /*
        int [] sizes = new int[100];
        for (int i = 0; i < sizes.length; i++){
        	sizes[i] = 10;
        }
        
        Paint [] colors = new Paint[100];
        for (int i = 0; i < colors.length; i++){
        	colors[i] = new Color ((float)Math.random()/5, (float)Math.random()/2, (float)Math.random());
        }
        */
        int [] sizes = new int[100];
        Paint [] colors = new Paint[100];
        int [] shapes = new int[100];
        for (int i = 0; i < 100; i++){
        	shapes[i] = 10;
        }
        
		ExtendedFastScatterPlot plot = new ExtendedFastScatterPlot(tasks, domainAxis, rangeAxis, sizes, colors, shapes);
		plot.setBackgroundImage(ImageIO.read(new File("res/MR.jpg")));
		JFreeChart taskPlot = new JFreeChart(plot);
		//taskPlot.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
		cp = new ChartPanel(taskPlot, true);
		cklstP.add(cp, BorderLayout.CENTER);
		
		cklstP.setPreferredSize(new Dimension(700, 450));
		
		return cklstP;
	}
	
	/**
	 * @throws IOException 
	 * @wbp.parser.entryPoint
	 */
	public static void addNewToDoPanel(){
		cklstP.remove(cp);
		
		JPanel newToDoP = new JPanel();
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
		spinner = new JSpinner();
		dueP.add(dueL, BorderLayout.NORTH);
		dueP.add(spinner, BorderLayout.CENTER);
		
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
		if (newToDoP != null && newToDoP.getParent() == cklstP){
			cklstP.remove(newToDoP);
		}
		String title = newTitleT.getText();
		String descrip = newDescripA.getText();
		int i = impS.getValue();
		int dD = (int) spinner.getValue();
		
		ToDo.toDos[ToDo.lowestIndex()] = new ToDo(title, descrip, i, dD);
		cklstP.add(cp);
	}
	
	public static void populate(){
		for (int i = 0; i < 10; i++){
			float x = (float)Math.random()*10;
			float y = (float)Math.random()*10;
			Point n = new Point(x, y, "X");
			checkTasks.add(n);
			//System.out.println(checkTasks.get(i).x + " "  + checkTasks.get(i).y);
		}
	}
	public static void add(){
		tasks = new float[2][checkTasks.size()];
		for (int i = 0; i < checkTasks.size(); i++) {
			tasks[0][i] = checkTasks.get(i).x;
			tasks[1][i] = checkTasks.get(i).y;
		}
	}
	
	static class Point{
		float x;
		float y;
		String descrip;
		public Point(float x0, float y0, String s) { x = x0; y = y0; descrip = s;}
	}
}
