package Login;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import characterPackage.Character;
import dailyPackage.Daily;
import toDoPackage.ToDo;

public class FileClass { // Need to still be able to check against a list of
							// usernames & also output files!!!
	public static void importAll(String user) {
		try {
			importUserData(user);
			importDailies(user);
			importToDos(user);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid username.");
		}
	}

	public static void importUserData(String user) throws FileNotFoundException {
		Scanner fInput = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		while (fInput.hasNext()) {
			String s = fInput.nextLine();
			//System.out.println(s);
			int level = fInput.nextInt();
			//System.out.println(level);
			double health = fInput.nextDouble();
			//System.out.println(health);
			double EXP = fInput.nextDouble();
			//System.out.println(EXP);
			String empty = fInput.nextLine();
			String pic = fInput.nextLine();
			//System.out.println(pic);
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
			Character.user = new Character(s, level, health, EXP, profilePic);

		}
		fInput.close();
	}

	public static void importDailies(String user) throws FileNotFoundException {
		Scanner fInput = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "dailies.txt"));
		int index = 0;
		while (fInput.hasNext()) {
			String title = fInput.nextLine();
			//System.out.println(index + " " + title);
			if (title.equals("!@#$%^&*()")){
				title = "";
			}
			String description = fInput.nextLine();
			//System.out.println(index + " " + description);
			if (description.equals("!@#$%^&*()")){
				description = "";
			}
			int cklstSize = fInput.nextInt();
			// //System.out.println(cklstSize);
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
			//System.out.println(title + difficulty);
			// System.out.println(difficulty);
			empty = fInput.nextLine();
			String repeatS = fInput.nextLine();
			//System.out.println(repeatS);
			boolean[] repeatA = new boolean[7];
			for (int i = 0; i < repeatS.length(); i++) {
				if (repeatS.charAt(i) == 'X') {
					repeatA[i] = true;
				}
			}
			String compS = fInput.nextLine();
			// System.out.println(compS);
			boolean complete = false;
			if (compS.equals("Y")) {
				complete = true;
			}
			// System.out.println("And this is another one.");
			empty = fInput.nextLine();
			// System.out.println(title);
			Daily.dayList[index] = new Daily(title, description, cklstItems, cklstDone, difficulty, repeatA, complete);
			index++;
		}
		fInput.close();
	}

	public static void importToDos(String user) throws FileNotFoundException {
		Scanner fInput = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "todos.txt"));
		int index = 0;
		while (fInput.hasNext()) {
			String title = fInput.nextLine();
			//System.out.println(title);
			String description = fInput.nextLine();
			//System.out.println(description);
			int importance = fInput.nextInt();
			//System.out.println(importance);
			int dueDate = fInput.nextInt();
			//System.out.println(dueDate);
			Color c = ToDo.generateRandomColor();
			boolean done = fInput.nextBoolean();
			String empty = fInput.nextLine();
			ToDo.toDos[index] = new ToDo(title, description, importance, dueDate, c, done);
			index++;
		}
		fInput.close();
	}
	
	// Search if user is in database
	public static boolean findUser(String user){
		try {
			Scanner fInput = new Scanner(new File("saves/users.txt"));
			int cnt = fInput.nextInt();
			String s = fInput.nextLine();
			
			for (int i = 1; i <= cnt; i++) {
				s = fInput.nextLine();
				//System.out.println(s);
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
	
	// Add new user & add file system
	public static void newUser(String user) throws FileNotFoundException{
		Scanner in = new Scanner(new File("saves/users.txt"));
		ArrayList <String> data = new ArrayList <String>();
		while(in.hasNextLine()){
			data.add(in.nextLine());
			//System.out.println(data.get(data.size()-1));
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
				boolean[] repeat = Daily.dayList[i].getRepeat();
				String s = "";
				for (int j = 0; j < repeat.length; j++) {
					if (repeat[j] = true) {
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
		
		// Exporting doDoes
		PrintWriter pw2 = new PrintWriter(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "todos.txt"));
		for (int i = 0; i < ToDo.toDos.length; i++){
			if (ToDo.toDos[i] != null){
				String title = ToDo.toDos[i].getTitle();
				if (title.equals("")){
					title = "!@#$%^&*()";
				}
				pw2.println(title);
				String description = ToDo.toDos[i].getDescription();
				if (description.equals("")){
					description = "!@#$%^&*()";
				}
				pw2.println(description);
				pw2.println(ToDo.toDos[i].getImportance());
				pw2.println(ToDo.toDos[i].getDueDate());
				//System.out.println(ToDo.toDos[i].getDone());
				if (ToDo.toDos[i].getDone()){
					pw2.println("true");
				}
				else{
					pw2.println("false");
				}
			}
		}	
		pw2.close();
		// Get location of image
		Scanner in = new Scanner(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		in.nextLine();
		in.nextLine();
		in.nextLine();
		in.nextLine();
		String location = in.nextLine();
		//System.out.println(location);
		in.close();
		// Add to file
		PrintWriter pw3 = new PrintWriter(new File("saves/" + user.toUpperCase() + "/" + user.toUpperCase() + "data.txt"));
		//System.out.println("Username: " + user);
		pw3.println(user);
		pw3.println(Character.user.getLevel());
		pw3.println(Character.user.getHealth());
		pw3.println(Character.user.getEXP());
		pw3.println(location);
		pw3.close();
	}
}
