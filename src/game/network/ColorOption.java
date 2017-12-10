package game.network;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ColorOption extends JLabel implements ListCellRenderer<Color> {

	private boolean backSet = false;
	
	public ColorOption() {
		setOpaque(true);
	}
	
	@Override
	public void setBackground(Color bg) {
		if (backSet) {
			super.setBackground(bg);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
        return new Dimension(200, 50);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index,
			boolean isSelected, boolean cellHasFocus) {		
		backSet = true;
        setText(" ");           
        setBackground(value);        
        backSet = false;
        return this;  
	}

}
