package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.residents.Armory;
import graphics.Sidebar;

public class ArmoryDetails extends JPanel {

	public ArmoryDetails(Armory resident) {
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
		
		JLabel healthLabel = new JLabel(resident.health() + " Health", SwingConstants.CENTER);
		healthLabel.setOpaque(true);
		healthLabel.setBackground(new Color(150, 80, 80));
		healthLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		healthLabel.setBounds((Sidebar.width - 110) / 2, 35, 110, 30);
		healthLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		JLabel allowedUnitsLabel = new JLabel("<html>Allows units: <br> Spearman <br> Cavalry</html>", SwingConstants.CENTER);
		allowedUnitsLabel.setOpaque(true);
		allowedUnitsLabel.setBackground(new Color(234, 204, 187));
		allowedUnitsLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		allowedUnitsLabel.setBounds((Sidebar.width - 150) / 2, 70, 150, 70);
		allowedUnitsLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		add(healthLabel);
		add(allowedUnitsLabel);
	}
	
}
