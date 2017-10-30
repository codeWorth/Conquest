package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.residents.Farm;
import graphics.Sidebar;

public class FarmDetails extends JPanel {
	
	public FarmDetails(Farm resident) {
		setLayout(null);
		setOpaque(false);
		setBounds(0, Sidebar.detailsStart, Sidebar.width, 400);
		
		JLabel unitName = new JLabel(resident.name(), SwingConstants.CENTER);
		unitName.setOpaque(true);
		unitName.setBackground(Color.white);
		unitName.setFont(new Font("Helvetica", Font.PLAIN, 20));
		unitName.setBounds((Sidebar.width - 120) / 2, 0, 120, 30);
		unitName.setVerticalTextPosition(AbstractButton.CENTER);
		add(unitName);
		
		JLabel healthLabel = new JLabel("4 Health", SwingConstants.CENTER);
		healthLabel.setOpaque(true);
		healthLabel.setBackground(new Color(150, 80, 80));
		healthLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		healthLabel.setBounds((Sidebar.width - 110) / 2, 35, 110, 30);
		healthLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		JLabel maxUnitsLabel = new JLabel("3 More Units", SwingConstants.CENTER);
		maxUnitsLabel.setOpaque(true);
		maxUnitsLabel.setBackground(new Color(234, 204, 187));
		maxUnitsLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		maxUnitsLabel.setBounds((Sidebar.width - 160) / 2, 70, 160, 30);
		maxUnitsLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		healthLabel.setText(Integer.toString(resident.health()) + " Health");
		maxUnitsLabel.setText(Integer.toString(resident.maxUnitsBuff()) + " More Units");
		
		add(healthLabel);
		add(maxUnitsLabel);
	}
	
}
