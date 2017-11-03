package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.PlayerData;
import game.residents.Armory;
import game.residents.Upgrade;
import graphics.Sidebar;

public class ArmoryDetails extends JPanel implements Clickable {

	private static final int buyMenuStart = 200;
	private UpgradePanel upgradePanel;
	
	public ArmoryDetails(Armory resident, Upgrade[] upgrades) {
		setLayout(null);
		setOpaque(false);
		setBounds(0, Sidebar.detailsStart, Sidebar.width, 400);
				
		JLabel playerName = new JLabel("Player: " + resident.playerData().name, SwingConstants.CENTER);
		playerName.setOpaque(true);
		playerName.setBackground(resident.playerData().color);
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
		
		JLabel allowedUnitsLabel = new JLabel("<html>Allows units: <br> Spearman <br> Cavalry</html>", SwingConstants.CENTER);
		allowedUnitsLabel.setOpaque(true);
		allowedUnitsLabel.setBackground(new Color(234, 204, 187));
		allowedUnitsLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		allowedUnitsLabel.setBounds((Sidebar.width - 150) / 2, 105, 150, 70);
		allowedUnitsLabel.setVerticalTextPosition(AbstractButton.CENTER);		
		
		add(healthLabel);
		add(allowedUnitsLabel);
		
		if (resident.playerData() == PlayerData.me) {
			upgradePanel = new UpgradePanel(upgrades);
			upgradePanel.setBounds(0, buyMenuStart, Sidebar.width, 600);
			add(upgradePanel);
		}
	}
	
	@Override
	public void pressed(double x, double y) {
		upgradePanel.pressed(x, y - buyMenuStart);
	}
	
}
