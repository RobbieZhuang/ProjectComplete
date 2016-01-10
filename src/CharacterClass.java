import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class CharacterClass {
	/**
	 * @wbp.parser.entryPoint
	 */
	public static JPanel initiatePanel() throws IOException{
		
		double level = 93;
		
		JPanel characterP = new JPanel(new BorderLayout());
		JPanel topP = new JPanel(new BorderLayout());
		JPanel lowerP = new JPanel();
		JPanel picP = new JPanel();
		
		JLabel nameL = new JLabel("GoldenHippo");
		topP.add(nameL, BorderLayout.NORTH);
		JLabel levL = new JLabel("Level " + String.valueOf((int)level));
		topP.add(levL, BorderLayout.WEST);
		
		picP.setSize(100, 100);
		BufferedImage avatar = ImageIO.read(new File("res/profile.png"));
		JLabel picL = new JLabel(new ImageIcon(avatar));
		picP.add(picL);
		
		characterP.add(picP, BorderLayout.WEST);
		
		characterP.add(topP, BorderLayout.NORTH);
		characterP.add(lowerP, BorderLayout.CENTER);
		lowerP.setLayout(new MigLayout("", "[220px]", "[50px]"));
		JPanel statsP = new JPanel();
		
		JProgressBar healthPB = new JProgressBar();
		JProgressBar expPB = new JProgressBar();
		
		healthPB.setValue(100);
		expPB.setValue(10);
		
		healthPB.setPreferredSize(new Dimension(1, 1));
		expPB.setPreferredSize(new Dimension(1, 1));
		statsP.setLayout(new MigLayout("", "[110px][110px]", "[25px][25px]"));
		
		JLabel lblNewLabel = new JLabel("Health");
		statsP.add(lblNewLabel, "cell 0 0,alignx left,growy");
		statsP.add(healthPB, "cell 1 0,grow");
		
		JLabel lblExp = new JLabel("EXP");
		statsP.add(lblExp, "cell 0 1,alignx left,growy");
		statsP.add(expPB, "cell 1 1,grow");
		lowerP.add(statsP, "cell 0 0,grow");
		
		return characterP;
		
	}
	
}
