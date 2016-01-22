package login;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import character.Character;
import dailies.Daily;
import todos.ToDo;

public class FileClass {
	
	// Import the user data from files located in "saves/"
	public static void importAll(String user) {
		try {
			importUserData(user);
			importDailies(user);
			importToDos(user);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid username.");
		}
	}

	// Imports the user's data (eg. EXP, health, level) from USERdata.txt file
	public static void importUserData(String user) throws FileNotFoundException {
		Scanner fInput = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		while (fInput.hasNext()) {
			String s = fInput.nextLine();
			int level = fInput.nextInt();
			double health = fInput.nextDouble();
			double EXP = fInput.nextDouble();
			String empty = fInput.nextLine();
			String pic = fInput.nextLine();
			BufferedImage profilePic = null;
			if (!pic.equals("none")) {
				try {
					profilePic = ImageIO.read(new File("res/" + pic));
				} catch (IOException e) {}
			} 
			else {
				try {
					profilePic = ImageIO.read(new File("res/Default.png"));
				} catch (IOException e) {}
			}
			String day = fInput.nextLine();
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			Date date;
			try {
				date = format.parse(day);
				Character.user = new Character(s, level, health, EXP, profilePic, date);
			} catch (ParseException e) {
			}
		}
		fInput.close();
	}

	// Imports the user's dailies from USERdailies.txt file
	public static void importDailies(String user) throws FileNotFoundException {
		Scanner fInput = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "dailies.txt"));
		int index = 0;
		while (fInput.hasNext()) {
			String title = fInput.nextLine();
			if (title.equals("!@#$%^&*()")){
				title = "";
			}
			String description = fInput.nextLine();
			if (description.equals("!@#$%^&*()")){
				description = "";
			}
			int cklstSize = fInput.nextInt();
			String[] cklstItems = new String[cklstSize];
			String empty = fInput.nextLine();
			StringTokenizer sk = new StringTokenizer(fInput.nextLine(), "$");
			int cnt = 0;
			while (sk.hasMoreTokens()) {
				cklstItems[cnt] = sk.nextToken();
				cnt++;
			}
			String cklstDoneS = fInput.nextLine();
			boolean [] cklstDone = new boolean[cklstSize];
			for (int i = 0; i < cklstSize; i++) {
				if (cklstDoneS.charAt(i) == 'T'){
					cklstDone[i] = true;
				}
				else{
					cklstDone[i] = false;
				}
			}
			int difficulty = fInput.nextInt();
			empty = fInput.nextLine();
			String repeatS = fInput.nextLine();
			boolean[] repeatA = new boolean[7];
			for (int i = 0; i < repeatS.length(); i++) {
				System.out.println(repeatS);
				if (repeatS.charAt(i) == 'X') {
					repeatA[i] = true;
				}
				else{
					repeatA[i] = false;
				}
			}
			String compS = fInput.nextLine();
			boolean complete = false;
			if (compS.equals("Y")) {
				complete = true;
			}
			empty = fInput.nextLine();
			Daily.dayList[index] = new Daily(title, description, cklstItems, cklstDone, difficulty, repeatA, complete);
			for (int j = 0; j < repeatA.length; j++) {
				System.out.println(Daily.dayList[index].getRepeat()[j]);
			}
			index++;
		}
		fInput.close();
	}
	
	// Imports the user's to dos from USERtodos.txt file
	public static void importToDos(String user) throws FileNotFoundException {
		Scanner fInput = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "todos.txt"));
		int index = 0;
		while (fInput.hasNext()) {
			String title = fInput.nextLine();
			String description = fInput.nextLine();
			int importance = fInput.nextInt();
			int dueDate = fInput.nextInt();
			Color c = ToDo.generateRandomColor();
			boolean done = fInput.nextBoolean();
			String empty = fInput.nextLine();
			ToDo.toDoList[index] = new ToDo(title, description, importance, dueDate, c, done);
			index++;
		}
		fInput.close();
	}
	
	// Search if user is in database, if not then return false in console
	public static boolean findUser(String user){
		try {
			Scanner fInput = new Scanner(new File("saves/users.txt"));
			int cnt = fInput.nextInt();
			String s = fInput.nextLine();
			
			for (int i = 1; i <= cnt; i++) {
				s = fInput.nextLine();
				if (s.equals(user.toUpperCase())){
					fInput.close();
					return true;
				}
			}
			fInput.close();
			return false;
		} catch (FileNotFoundException e) {}
		return false;
	}
	
	// When user clicks this button, it will add a new user to the users.txt file as well as create 
	// new files for the user under a new folder that has their name in CAPS
	public static void newUser(String user) throws FileNotFoundException{
		Scanner in = new Scanner(new File("saves/users.txt"));
		ArrayList <String> data = new ArrayList <String>();
		while(in.hasNextLine()){
			data.add(in.nextLine());
		}
		data.set(0, String.valueOf(Integer.parseInt(data.get(0)) + 1));
		PrintWriter pw = new PrintWriter(new File("saves/users.txt"));
		while(!data.isEmpty()){
			pw.println(data.remove(0));
		}
		pw.println(user.toUpperCase());
		pw.close();
		new File("saves/" + user.toUpperCase()).mkdirs();
		PrintWriter pw1 = new PrintWriter(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		pw1.println(user);
		pw1.println("1");
		pw1.println("100");
		pw1.println("0");
		pw1.println("default.png");
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		pw1.println(format.format(new Date()));
		pw1.close();
		new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "dailies.txt");	
		new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "todos.txt");
	}
	
	// Print all data to file
	public static void exportData(String user) throws FileNotFoundException {
		
		// Export Dailies
		PrintWriter pw = new PrintWriter(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "dailies.txt"));
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (Daily.dayList[i] != null) {
				String title = Daily.dayList[i].getTitle();
				if (title.equals("")){
					title = "!@#$%^&*()";
				}
				pw.println(title);
				String description = Daily.dayList[i].getDescription();
				if (description.equals("")){
					description = "!@#$%^&*()";
				}
				pw.println(description);
				String[] cklst = Daily.dayList[i].getChecklist();
				pw.println(cklst.length);
				for (int j = 0; j < cklst.length - 1; j++) {
					pw.print(cklst[j] + "$");
				}
				pw.print(cklst[cklst.length - 1]);
				pw.println();
				String cklstDoneS = "";
				for (int j = 0; j < cklst.length; j++) {
					if (Daily.dayList[i].getChecklistDone()[j]){
						cklstDoneS += "T";
					}
					else{
						cklstDoneS += "F";
					}
				}
				pw.println(cklstDoneS);
				pw.println(Daily.dayList[i].getDifficulty());
				String s = "";
				for (int j = 0; j < Daily.dayList[i].getRepeat().length; j++) {
					if (Daily.dayList[i].getRepeat()[j] == true) {
						s += "X";
					} else {
						s += "Y";
					}
				}
				pw.println(s);
				if (Daily.dayList[i].getComplete()){
					pw.println("true");
				}
				else{
					pw.println("false");
				}
				pw.println("�END�");
			}
		}
		pw.close();
		
		// Exporting doDos
		PrintWriter pw2 = new PrintWriter(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "todos.txt"));
		for (int i = 0; i < ToDo.toDoList.length; i++){
			if (ToDo.toDoList[i] != null){
				String title = ToDo.toDoList[i].getTitle();
				if (title.equals("")){
					title = "!@#$%^&*()";
				}
				pw2.println(title);
				String description = ToDo.toDoList[i].getDescription();
				if (description.equals("")){
					description = "!@#$%^&*()";
				}
				pw2.println(description);
				pw2.println(ToDo.toDoList[i].getImportance());
				pw2.println(ToDo.toDoList[i].getDueDate());
				if (ToDo.toDoList[i].getDone()){
					pw2.println("true");
				}
				else{
					pw2.println("false");
				}
			}
		}	
		pw2.close();
		
		// Getting the file name of the image(from the previous data file
		Scanner in = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		in.nextLine();
		in.nextLine();
		in.nextLine();
		in.nextLine();
		String location = in.nextLine();
		in.close();
		
		// Add to file
		PrintWriter pw3 = new PrintWriter(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		pw3.println(user);
		pw3.println(Character.user.getLevel());
		pw3.println(Character.user.getHealth());
		pw3.println(Character.user.getEXP());
		pw3.println(location);
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		pw3.println(format.format(Character.user.getDate()));
		pw3.close();
	}
}
