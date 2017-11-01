package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.residents.TileResident;
import graphics.Sidebar;

public class TowerDetails extends JPanel {

	public TowerDetails(TileResident resident) {
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
		
		JLabel actionsLeftLabel = new JLabel(Integer.toString(resident.actionsLeft()) + " Actions Left", SwingConstants.CENTER);
		actionsLeftLabel.setOpaque(true);
		actionsLeftLabel.setBackground(Color.lightGray);
		actionsLeftLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		actionsLeftLabel.setBounds((Sidebar.width - 150) / 2, 70, 150, 30);
		actionsLeftLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		JLabel healthLabel = new JLabel(Integer.toString(resident.health()) + " Health", SwingConstants.CENTER);
		healthLabel.setOpaque(true);
		healthLabel.setBackground(new Color(150, 80, 80));
		healthLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		healthLabel.setBounds((Sidebar.width - 110) / 2, 105, 110, 30);
		healthLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		JLabel rangeLabel = new JLabel(resident.shootRange() + " Range", SwingConstants.CENTER);
		rangeLabel.setOpaque(true);
		rangeLabel.setBackground(Color.green);
		rangeLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		rangeLabel.setBounds((Sidebar.width - 110) / 2, 140, 110, 30);
		rangeLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		JLabel attackLabel = new JLabel(Integer.toString(resident.damage()) + " Attack", SwingConstants.CENTER);
		attackLabel.setOpaque(true);
		attackLabel.setBackground(new Color(255, 251, 56));
		attackLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		attackLabel.setBounds((Sidebar.width - 110) / 2, 175, 110, 30);
		attackLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		add(unitName);
		add(actionsLeftLabel);
		add(healthLabel);
		add(rangeLabel);
		add(attackLabel);
	}
	
}
