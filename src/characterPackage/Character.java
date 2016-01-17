package characterPackage;
import java.awt.image.BufferedImage;

import Login.LoginClass;
import mainPackage.PopupMessage;

public class Character {
	private String name;
	private int level;
	private double health;
	private double EXP;
	private BufferedImage pic;

	// Declare character (for testing purposes)
	static Character user = new Character("GoldenHippo", 85, 80, 500, LoginClass.pic());
	//static Character user = LoginClass.c();
	
	public Character (String name, int level, double health, double EXP, BufferedImage pic){
		this.name = name;
		this.level = level;
		this.health = health;
		this.EXP = EXP;
		this.pic = pic;
	}
	public Character (String name, int level, double health, double EXP){
		this.name = name;
		this.level = level;
		this.health = health;
		this.EXP = EXP;
	}
	public Character (String name, int level){
		this.name = name;
		this.level = level;
		this.health = 100;
		this.EXP = 100;
	}
	public void setName(String name){
		this.name = name;	
	}
	public String getName(){
		return this.name;
	}
	public int getLevel(){
		return this.level;
	}
	public double getHealth(){
		return this.health;
	}
	public double getEXP(){
		return this.EXP;
	}
	public BufferedImage getPic(){
		return this.pic;
	}
	public void setImage(BufferedImage pic){
		this.pic = pic;
	}
	public static void dailyComplete(int difficulty){
		double n = 5*difficulty*Math.random()*10;
		user.EXP += n;
		new PopupMessage("+" + (int)n + " EXP.");
	}
	public static void dailyUnComplete(int difficulty){
		double n = 5*difficulty*Math.random()*10;
		user.EXP -= n;
		new PopupMessage("-" + (int)n + " EXP.");
	}
	public static void dailyIncomplete(int difficulty){
		double n = 5*(1/difficulty);
		user.health -= n;
		new PopupMessage("Oh no, you have lost " + (int)n + " health!");
	}
}
