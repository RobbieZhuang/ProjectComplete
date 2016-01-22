package character;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;

import login.LoginClass;

public class Character {
	private String name;
	private int level;
	private double health;
	private double EXP;
	private BufferedImage pic;
	private int levelEXPCap;
	public static Character user;
	private Date lastLogin;

	// Constructors
	public Character(String name, int level, double health, double EXP, BufferedImage pic, Date day) {
		this.name = name;
		this.level = level;
		this.health = health;
		this.EXP = EXP;
		this.levelEXPCap = 100 + (this.level-1)*50;
		this.pic = pic;
		this.lastLogin = day;
	}

	public Character(String name, int level, double health, double EXP, Date day) {
		this.name = name;
		this.level = level;
		this.health = health;
		this.EXP = EXP;
		this.levelEXPCap = 100 + (this.level-1)*50;
		this.lastLogin = day;
	}

	public Character(String name, int level) {
		this.name = name;
		this.level = level;
		this.health = 100;
		this.EXP = 100;
		this.levelEXPCap = 100 + (this.level-1)*50;
		this.lastLogin = new Date();
	}

	// Getters and Setters
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int l){
		this.level = l;
	}
	
	public double getHealth() {
		return this.health;
	}
	
	public void setHealth(double h){
		this.health = h;
	}
	
	public double getEXP() {
		return this.EXP;
	}

	public void setEXP(double e){
		this.EXP = e;
	}
	
	public int getEXPCap(){
		return this.levelEXPCap;
	}
	
	public void setEXPCap(){
		this.levelEXPCap = 100 + (this.level-1)*50;
	}
	
	public BufferedImage getPic() {
		return this.pic;
	}

	public void setImage(BufferedImage pic) {
		this.pic = pic;
	}
	
	public Date getDate() {
		return lastLogin;
	}
	public void setDate(){
		lastLogin = new Date();
	}
	
	// When daily is complete, Character gains EXP
	public static void dailyComplete(int difficulty) {
		double n = 10 * difficulty * Math.random();
		user.EXP += n;
		new PopupMessage("+" + (int) n + " EXP.", Color.DARK_GRAY, false);
		gainLevel();
		CharacterClass.updateStatsPanel();
	}

	// When daily is unchecked, EXP is deducted from the character (can go into negatives on purpose)
	public static void dailyUnComplete(int difficulty) {
		double n = 10 * difficulty * Math.random();
		user.EXP -= n;
		new PopupMessage("-" + (int) n + " EXP.", Color.DARK_GRAY, true);
		CharacterClass.updateStatsPanel();
	}

	// If character does not finish daily, then health is deducted
	// There is a check for losing a level
	public static void dailyIncomplete(int difficulty) {
		double n = (difficulty)*0.5;
		user.health -= n;
		if (user.getHealth() <= 0){
			loseLevel();
		}
		else{
			new PopupMessage("You have lost some health.", Color.DARK_GRAY, false);
		}
		CharacterClass.updateStatsPanel();
	}

	// When task is complete, character is rewarded EXP and there is a check to see if character leveled up
	public static void taskComplete(int difficulty) {
		double n = 10 * difficulty * Math.random();
		user.EXP += n;
		new PopupMessage("+" + (int) n + " EXP.", Color.DARK_GRAY, false);
		gainLevel();
		CharacterClass.updateStatsPanel();
	}
	
	// When user gains a level, EXP set to 0 or however much over the amount to complete level
	// health set to 100 and popup messages appear
	public static void gainLevel(){
		double EXP = user.getEXP();
		if (EXP >= user.getEXPCap()){
			user.setLevel(user.getLevel() + 1);
			user.setEXP(EXP - user.getEXPCap());
			user.setHealth(100.0);
			new PopupMessage("Level up to level " + user.getLevel() + " .", generateRandomColor(), true);
			user.setEXPCap();
		}
		CharacterClass.updateImportantStats();
	}
	
	// When user loses a level, EXP set to 0, health set to 100 and popup messages appear
	// Update the character JPanels
	public static void loseLevel(){
		if (user.level == 1){
			user.setEXP(0.0);
			user.setHealth(100.0);
			new PopupMessage("You cannot lose another level " + user.getLevel() + " .", generateRandomColor(), true);
		}
		else{
			user.setEXP(0.0);
			user.setHealth(100.0);
			user.setLevel(user.getLevel()-1);
			new PopupMessage("Oh no, you have lost a level! You are now level " + user.getLevel() + " .", generateRandomColor(), true);
		}
		CharacterClass.updateStatsPanel();
		CharacterClass.updateImportantStats();
	}
	
	// Generates a random colour
	public static Color generateRandomColor() {
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);
		Color background = new Color(255, 255, 255);
		if (background != null) {
			red = (red + background.getRed()) / 2;
			green = (green + background.getGreen()) / 2;
			blue = (blue + background.getBlue()) / 2;
		}

		Color color = new Color(red, green, blue);
		return color;
	}

	
}
