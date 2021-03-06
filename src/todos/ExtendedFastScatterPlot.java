package todos;

//Learned from Shadow @ http://stackoverflow.com/questions/27017772/how-to-change-point-size-or-shape-in-fastscatterplot-jfreechart
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.ui.RectangleEdge;

//Extension of FastScatterPlot
public class ExtendedFastScatterPlot extends FastScatterPlot {
	private static final long serialVersionUID = 1L;
	int[] sizes;
	Paint[] colors;
	float[][] data;

	// Gets the data, axis, size and colours
	public ExtendedFastScatterPlot(float[][] data, NumberAxis domainAxis, NumberAxis rangeAxis, int[] s, Paint[] c) {
		super(data, domainAxis, rangeAxis);
		this.sizes = s;
		this.colors = c;
		this.data = data;
	}

	// Overrides the render class to add the data, axis and particularly 
	// changes the colour and shape of the points that are plotted
	@Override
	public void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, CrosshairState crosshairState) {

		if (this.data != null) {
			for (int i = 0; i < data.length; i++) {
				float x = data[i][0];
				float y = data[i][1];
				int size = sizes[i];

				int transX = (int) this.getDomainAxis().valueToJava2D(x, dataArea, RectangleEdge.BOTTOM);
				int transY = (int) this.getRangeAxis().valueToJava2D(y, dataArea, RectangleEdge.LEFT);

				g2.setPaint(this.colors[i]);
				g2.fillOval(transX, transY, size, size);
			}
		}
	}
}
