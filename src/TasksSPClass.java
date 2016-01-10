import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;

public class TasksSPClass{
	
	static JPanel cklstP;
	
	static float[][] tasks;
	
	static ArrayList <Point> checkTasks = new ArrayList <Point>();
	static ArrayList <Point> finiCheckTasks = new ArrayList <Point>();
	/**
	 * @wbp.parser.entryPoint
	 */
	public static JPanel initiateScatterPlot(){
		cklstP = new JPanel(new BorderLayout());
		cklstP.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		populate();
		add();
		
		JLabel titleL = new JLabel("Advanced Checklist");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Century", Font.PLAIN, 20));
		
		cklstP.add(titleL, BorderLayout.NORTH);
		
		NumberAxis domainAxis = new NumberAxis("Due Date");
        //domainAxis.setAutoRangeIncludesZero(false);
        NumberAxis rangeAxis = new NumberAxis("Importance");
        //rangeAxis.setAutoRangeIncludesZero(false);
        
        int [] sizes = new int[100];
        for (int i = 0; i < sizes.length; i++){
        	sizes[i] = 10;
        }
        
        Paint [] colors = new Paint[100];
        for (int i = 0; i < colors.length; i++){
        	colors[i] = new Color ((float)Math.random()/5, (float)Math.random()/2, (float)Math.random());
        }
        
        int [] shapes = new int[100];
        for (int i = 0; i < sizes.length; i++){
        	shapes[i] = 10;
        }
        
		ExtendedFastScatterPlot plot = new ExtendedFastScatterPlot(tasks, domainAxis, rangeAxis, sizes, colors, shapes);
		
		JFreeChart taskPlot = new JFreeChart(plot);
		//taskPlot.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
		ChartPanel cp = new ChartPanel(taskPlot, true);
		cklstP.add(cp, BorderLayout.CENTER);
		cklstP.setPreferredSize(new Dimension(700, 450));
		
		System.out.println();
		System.out.println(checkTasks.size());
		return cklstP;
	}
	public static void populate(){
		for (int i = 0; i < 100; i++){
			float x = (float)Math.random()*100 +1;
			float y = (float)Math.random()*100 + 1;
			Point n = new Point(x, y, "X");
			checkTasks.add(n);
			System.out.println(checkTasks.get(i).x + " "  + checkTasks.get(i).y);
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
