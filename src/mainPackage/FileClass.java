package mainPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import dailyPackage.Daily;

public class FileClass {

	public static void importDailies() throws FileNotFoundException{
		Scanner fInput = new Scanner(new File(Program.username + "dailies.txt"));
		//System.out.println("FROM FILE CLASS:");
		int index = 0;
		while (fInput.hasNext()){
			String title = fInput.nextLine();
			//System.out.println(title);
			String description = fInput.nextLine();
			//System.out.println(description);
			int cklstSize = Integer.parseInt(fInput.nextLine());
			//System.out.println(cklstSize);
			String [] cklstItems = new String[cklstSize];
			StringTokenizer sk = new StringTokenizer(fInput.nextLine(), "$");
			for (int i = 0; i < cklstSize; i++){
				cklstItems[i] = sk.nextToken();
				//System.out.println(cklstItems[i]);
			}
			int difficulty = Integer.parseInt(fInput.nextLine());
			//System.out.println(difficulty);
			String repeatS = fInput.nextLine();
			//System.out.println(repeatS);
			boolean [] repeatA = new boolean[7];
			for (int i = 0; i < repeatS.length(); i++){
				if(repeatS.equals('X')){
					repeatA[i] = true;
				}
			}
			String compS = fInput.nextLine();
			//System.out.println(compS);
			boolean complete = false;
			if (compS.equals("Y")){
				complete = true;
			}
			//System.out.println("And this is another one.");
			String empty = fInput.nextLine();
			System.out.println(title);
			Daily.dayList[index] = new Daily(title, description, cklstItems, difficulty, repeatA, complete);
			index++;
		}
		fInput.close();
		
		for (int i = 0; i < Daily.dayList.length; i++) {
			if (Daily.dayList[i] != null) System.out.println(Daily.dayList[i].getTitle());
		}
	}
	public static void importToDos() throws FileNotFoundException{
		Scanner fInput = new Scanner(new File(Program.username + "cklst.txt"));
		
	}
	public static void exportData(){
	}
}
