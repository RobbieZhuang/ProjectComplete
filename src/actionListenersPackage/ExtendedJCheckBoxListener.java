package actionListenersPackage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractButton;

import characterPackage.Character;
import characterPackage.CharacterClass;
import dailyPackage.Daily;

public class ExtendedJCheckBoxListener implements ActionListener{
	private int index;
	private int diff;
	
	public ExtendedJCheckBoxListener(int index, int diff){
		this.index = index;
		this.diff = diff;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractButton abstractButton = (AbstractButton) e.getSource();
        boolean selected = abstractButton.getModel().isSelected();
        
        if(selected){
        	Character.dailyComplete(diff);
        	Daily.dayList[index].setComplete(true);
        }
        else if(!selected){
        	Character.dailyUnComplete(diff);
        	Daily.dayList[index].setComplete(true);
        }
    	CharacterClass.updateStatsPanel();
	}
}
