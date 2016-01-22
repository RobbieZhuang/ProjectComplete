package character;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import mainPackage.Program;
import net.miginfocom.swing.MigLayout;

public class CharacterClass {
	static JPanel characterPanel;
	static JPanel statsP;
	static JPanel topP;
	/**
	 * @throws IOException
	 * @wbp.parser.entryPoint
	 */
	// Initializes the panel and sends it the main program to add to JFrame
	public static JPanel initiatePanel() {

		characterPanel = new JPanel();
		characterPanel.setLayout(new BorderLayout());

		JPanel picP = new JPanel();
		picP.setSize(new Dimension(100, 100));
		JLabel picL;
		BufferedImage avatar;
		avatar = Character.user.getPic();
		picL = new JLabel(new ImageIcon(avatar));
		picL.setSize(new Dimension(100, 100));
		picL.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		picP.add(picL);

		characterPanel.add(picP, BorderLayout.WEST);
		updateImportantStats();
		updateStatsPanel();
		
		return characterPanel;
	}
	// Updates the level and username panel
	public static void updateImportantStats(){
		if (topP != null && topP.getParent().equals(characterPanel)){
			characterPanel.remove(topP);
		}
		topP = new JPanel(new BorderLayout());

		JLabel userNameL = new JLabel(Character.user.getName());
		userNameL.setFont(new Font("Century", Font.PLAIN, 14));
		topP.add(userNameL, BorderLayout.NORTH);

		JLabel userLevelL = new JLabel();
		String level = "Level : " + String.valueOf(Character.user.getLevel());  
		userLevelL.setText(level);
		userLevelL.setFont(new Font("Century", Font.PLAIN, 14));
		topP.add(userLevelL, BorderLayout.CENTER);
		
		characterPanel.add(topP, BorderLayout.NORTH);
		
	}
	// Updates the JProgressbars containing health and EXP
	public static void updateStatsPanel() {

		if (statsP != null && statsP.getParent() == characterPanel) {
			characterPanel.remove(statsP);
		}

		statsP = new JPanel(new MigLayout("", "[][]", "[][]"));
		statsP.setSize(new Dimension(303, 56));

		JLabel healthL = new JLabel("HEALTH");
		healthL.setFont(new Font("Century", Font.PLAIN, 14));
		JProgressBar healthPB = new JProgressBar(0, 100);
		JLabel healthNumL = new JLabel();

		JLabel expL = new JLabel("EXP");
		expL.setFont(new Font("Century", Font.PLAIN, 14));
		JProgressBar expPB = new JProgressBar(0, 100);
		JLabel expNumL = new JLabel();

		healthPB.setValue((int) Character.user.getHealth());
		expPB.setValue((int) (((double) Character.user.getEXP() / Character.user.getEXPCap()) * 100.0));

		healthNumL.setText((int) Character.user.getHealth() + "/" + 100);
		healthNumL.setFont(new Font("Century", Font.PLAIN, 14));
		expNumL.setText((int) Character.user.getEXP() + "/" + Character.user.getEXPCap());
		expNumL.setFont(new Font("Century", Font.PLAIN, 14));

		statsP.add(healthL, "cell 0 0");
		statsP.add(healthPB, "cell 1 0");
		statsP.add(healthNumL, "cell 2 0");
		statsP.add(expL, "cell 0 1");
		statsP.add(expPB, "cell 1 1");
		statsP.add(expNumL, "cell 2 1");

		characterPanel.add(statsP, BorderLayout.CENTER);
		Program.window.repaint();
	}

}
