package Login;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoginClass {
	
	
	public static BufferedImage pic(){
		BufferedImage avatar = null;
		try{
			avatar = ImageIO.read(new File("res/Profile.png"));
			avatar = ImageIO.read(new File("res/CBdance.gif"));
		}
		catch (IOException e){
		}
		
		return avatar;
	}
}
