package toDoPackage;

import java.awt.Color;
import java.util.Random;

public class ToDo {
	private String title;
	private String description;
	private int importance;
	private int dueDate;
	private Color colour;
	private boolean done;

	public static ToDo[] toDos = new ToDo[100];

	public ToDo(String t, String d, int i, int dD, Color c, boolean done) {
		this.title = t;
		this.description = d;
		this.importance = i;
		this.dueDate = dD;
		this.colour = c;
		this.done = done;
	}

	public ToDo(String t, String d, int i, int dD, Color c) {
		this.title = t;
		this.description = d;
		this.importance = i;
		this.dueDate = dD;
		this.colour = c;
	}

	public ToDo(String t, String d, int i, int dD) {
		this.title = t;
		this.description = d;
		this.importance = i;
		this.dueDate = dD;
		this.done = false;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	public int getImportance() {
		return this.importance;
	}

	public void setImportance(int i) {
		this.importance = i;
	}

	public int getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(int d) {
		this.dueDate = d;
	}

	public boolean getDone() {
		return this.done;
	}

	public void setDone(boolean d) {
		this.done = d;
	}

	public void setColour(Color c) {
		this.colour = c;
	}

	public Color getColour() {
		return this.colour;
	}

	// Find out the lowest index without an object in array
	public static int lowestIndex() {
		int lowest = toDos.length - 1;
		if (toDos[0] == null)
			return 0;
		for (int i = toDos.length - 1; i >= 0; i--) {
			if (i > 1 && toDos[i] == null && toDos[i - 1] == null) {
				lowest--;
			}
		}
		return lowest;
	}

	// Add unique color to label
	public static void addColor(int index) {
		Color c = generateRandomColor();
		int cnt = 0;
		for (int j = 0; j < toDos.length; j++) {
			if (toDos[j] != null && toDos[j].getColour() != null && toDos[j].getColour().equals(c)) {
				cnt++;
			}
		}
		if (cnt > 0) {
			addColor(index);
		} else {
			toDos[index].setColour(c);
		}
	}

	// http://stackoverflow.com/questions/43044/algorithm-to-randomly-generate-an-aesthetically-pleasing-color-palette
	public static Color generateRandomColor() {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		Color mix = new Color(255, 255, 255);
		// mix the color
		if (mix != null) {
			red = (red + mix.getRed()) / 2;
			green = (green + mix.getGreen()) / 2;
			blue = (blue + mix.getBlue()) / 2;
		}

		Color color = new Color(red, green, blue);
		return color;
	}

}