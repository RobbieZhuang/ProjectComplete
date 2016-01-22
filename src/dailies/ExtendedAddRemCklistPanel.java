package dailies;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainPackage.Program;

public class ExtendedAddRemCklistPanel implements ActionListener{
	static JPanel mainP;
	static int index;
	public ExtendedAddRemCklistPanel(int ind) {
		// TODO Auto-generated constructor stub
		index = ind;
		mainP = new JPanel(new BorderLayout());
		System.out.println("Element " + index);
		JPanel checklistP = new JPanel(new GridLayout(Daily.dayList[index].getChecklistDone().length, 1));
		for (int i = 0; i < Daily.dayList[index].getChecklistDone().length; i++) {
			JCheckBox cb = new JCheckBox(Daily.dayList[index].getChecklist()[i]);
			cb.setSelected(Daily.dayList[index].getChecklistDone()[i]);
			checklistP.add(cb);
		}
		JButton dayTaskB = new JButton("EDIT");
		dayTaskB.addActionListener(new ExtendedCloseUDListener(index));
		mainP.add(new JLabel("     "), BorderLayout.WEST);
		mainP.add(checklistP, BorderLayout.CENTER);
		mainP.add(dayTaskB, BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractButton abstractButton = (AbstractButton) e.getSource();
		if ("small".equals(e.getActionCommand())){
			abstractButton.setActionCommand("large");
			abstractButton.setText("-");
			abstractButton.getParent().setPreferredSize(new Dimension(325, 100 + 10*Daily.dayList[index].getChecklistDone().length));
			//Program.window.repaint();
			abstractButton.getParent().add(mainP, BorderLayout.SOUTH);
		}
		else if ("large".equals(e.getActionCommand())){
			abstractButton.setActionCommand("small");
			abstractButton.setText("+");
			abstractButton.getParent().setPreferredSize(new Dimension(325, 30));
			abstractButton.getParent().remove(mainP);
		}
	}
	
	
}
