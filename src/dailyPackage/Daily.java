package dailyPackage;

import java.util.ArrayList;

public class Daily {
	private String title;
	private String memo;
	private String[] checklist;
	private int difficulty;
	private boolean[] repeat;
	private boolean complete;

	public static Daily[] dayList = new Daily[100]; // Stores Dailies that need to be completed

	public Daily(String t, String m, String[] cklst, int d, boolean[] r, boolean comp) {

		title = t;
		memo = m;
		checklist = cklst;
		difficulty = d;
		repeat = r;
		complete = comp;
	}

	public Daily() {
	}

	// Getters and Setters
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.memo;
	}

	public void setDescription(String descrip) {
		this.memo = descrip;
	}

	public String[] getChecklist() {
		return this.checklist;
	}

	public void setChecklist(String[] c) {
		this.checklist = c;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int d) {
		this.difficulty = d;
	}

	public boolean[] getRepeat() {
		return this.repeat;
	}

	public void setRepeat(boolean[] r) {
		this.repeat = r;
	}

	public boolean getComplete() {
		return this.complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	// Find out the lowest index without an object in array
	public static int lowestIndex() {
		int lowest = dayList.length;
		if (dayList[0] == null)
			return 0;
		while (dayList[lowest - 1] == null && lowest != 0) {
			lowest--;
		}
		return lowest;
	}

	// pretty much removes all empty spaces in array
	public static void destroyEmpty() {

		Daily[] newDayList = new Daily[100];
		int index = 0;
		for (int i = 0; i < dayList.length; i++) {
			if (dayList[i] != null) {
				newDayList[index] = dayList[i];
				index++;
			}
		}
		dayList = newDayList;
	}
}
