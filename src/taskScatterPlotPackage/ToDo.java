package taskScatterPlotPackage;

public class ToDo {
	private String title;
	private String description;
	private int importance;
	private int dueDate;
	private boolean done;
	
	public static ToDo [] toDos = new ToDo[101];
	
	public ToDo(String t, String d, int i, int dD, boolean done){
		this.title = t;
		this.description = d;
		this.importance = i;
		this.dueDate = dD;
		this.done = done;
	}
	public ToDo(String t, String d, int i, int dD){
		this.title = t;
		this.description = d;
		this.importance = i;
		this.dueDate = dD;
		this.done = false;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String t){
		this.title = t;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String d){
		this.description = d;
	}
	
	public int getImportance(){
		return this.importance;
	}
	
	public void setImportance(int i){
		this.importance = i;
	}
	
	public int getDueDate(){
		return this.dueDate;
	}
	
	public void setDueDate(int d){
		this.dueDate = d;
	}
	
	public boolean getDone(){
		return this.done;
	}
	
	public void setDone(boolean d){
		this.done = d;
	}
	
	// Find out the lowest index without an object in array
	public static int lowestIndex(){
		int lowest = toDos.length;
		while(toDos[lowest-1] == null && lowest != 0){
			lowest--;
		}
		return lowest;
	}
}
