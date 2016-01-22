package todos;

import java.awt.Color;
import java.util.Random;

// The ToDo object: This object contains all the data for a single to do (from the Advanced Checklist)
public class ToDo {
	private String title;
	private String description;
	private int importance;
	private int dueDate;
	private Color colour;
	private boolean done;

	// The toDoList array which contains all the toDos
	public static ToDo[] toDoList = new ToDo[1000];

	// Constructors
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

	// Getters and setters
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
		int lowest = toDoList.length - 1;
		if (toDoList[0] == null)
			return 0;
		for (int i = toDoList.length - 1; i >= 0; i--) {
			if (i > 1 && toDoList[i] == null && toDoList[i - 1] == null) {
				lowest--;
			}
		}
		return lowest;
	}

	// Add unique color to label
	public static void addColor(int index) {
		Color c = generateRandomColor();
		int cnt = 0;
		for (int j = 0; j < toDoList.length; j++) {
			if (toDoList[j] != null && toDoList[j].getColour() != null && toDoList[j].getColour().equals(c)) {
				cnt++;
			}
		}
		if (cnt > 0) {
			addColor(index);
		} else {
			toDoList[index].setColour(c);
		}
	}

	// Generates a random colour
	public static Color generateRandomColor() {
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);
		Color canvas = new Color(255, 255, 255);
		if (canvas != null) {
			red = (red + canvas.getRed()) / 2;
			green = (green + canvas.getGreen()) / 2;
			blue = (blue + canvas.getBlue()) / 2;
		}

		Color color = new Color(red, green, blue);
		return color;
	}

}
