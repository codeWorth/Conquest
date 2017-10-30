package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.residents.Base;
import graphics.Sidebar;

public class BaseDetails extends JPanel {
	
	public BaseDetails(Base resident) {
		setLayout(null);
		setOpaque(false);
		setBounds(0, Sidebar.detailsStart, Sidebar.width, 400);
		
		JLabel playerName = new JLabel("Player: " + resident.playerData().name, SwingConstants.CENTER);
		playerName.setOpaque(true);
		playerName.setBackground(resident.minimapColor());
		playerName.setFont(new Font("Helvetica", Font.PLAIN, 20));
		playerName.setBounds((Sidebar.width - 160) / 2, 0, 160, 30);
		playerName.setVerticalTextPosition(AbstractButton.CENTER);
		add(playerName);
		
		JLabel unitName = new JLabel(resident.name(), SwingConstants.CENTER);
		unitName.setOpaque(true);
		unitName.setBackground(Color.white);
		unitName.setFont(new Font("Helvetica", Font.PLAIN, 20));
		unitName.setBounds((Sidebar.width - 120) / 2, 35, 120, 30);
		unitName.setVerticalTextPosition(AbstractButton.CENTER);
		add(unitName);
		
		JLabel healthLabel = new JLabel(resident.health() + " Health", SwingConstants.CENTER);
		healthLabel.setOpaque(true);
		healthLabel.setBackground(new Color(150, 80, 80));
		healthLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		healthLabel.setBounds((Sidebar.width - 110) / 2, 70, 110, 30);
		healthLabel.setVerticalTextPosition(AbstractButton.CENTER);
				
		add(healthLabel);
	}
	
}
