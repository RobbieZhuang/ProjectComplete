package characterPackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import mainPackage.Program;
import net.miginfocom.swing.MigLayout;

public class CharacterClass{
	static JPanel characterPanel;
	static JPanel statsP;
	
	public static JPanel initiatePanel(){
		
		characterPanel = new JPanel();
		characterPanel.setLayout(new BorderLayout());
		
		JPanel topP = new JPanel(new MigLayout("", "[]", "[][]"));
		characterPanel.add(topP, BorderLayout.NORTH);
		topP.setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel userNameL = new JLabel(Character.user.getName());
		userNameL.setFont(new Font("Century", Font.PLAIN, 14));
		topP.add(userNameL, "cell 0 0, shrink");
		JLabel userLevelL = new JLabel("Level: " + Character.user.getLevel());
		userLevelL.setFont(new Font("Century", Font.PLAIN, 14));
		topP.add(userLevelL, "cell 0 1, shrink");
		
		JPanel picP = new JPanel();
		picP.setSize(new Dimension(100, 100));
		JLabel picL;
		BufferedImage avatar;
		try {
			avatar = ImageIO.read(new File("res/Profile1.png"));
			picL = new JLabel(new ImageIcon(avatar));
			picL.setSize(new Dimension(100, 100));
			picL.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
			picP.add(picL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		characterPanel.add(picP, BorderLayout.WEST);
		
		updateStatsPanel();
		
		return characterPanel;
	}
	/**
	 * @throws IOException 
	 * @wbp.parser.entryPoint
	 */
	public static void updateStatsPanel(){
		
		if (statsP != null && statsP.getParent() == characterPanel){
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
		
		System.out.println("Character data from user Character object EXP: " + Character.user.getEXP());
	
		healthPB.setValue((int) Character.user.getHealth());
		expPB.setValue((int)(((double)Character.user.getEXP()/2000.0) * 100.0));
		
		healthNumL.setText((int)Character.user.getHealth() + "/" + 100);
		healthNumL.setFont(new Font("Century", Font.PLAIN, 14));
		expNumL.setText((int)Character.user.getEXP() + "/" + 2000);
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
