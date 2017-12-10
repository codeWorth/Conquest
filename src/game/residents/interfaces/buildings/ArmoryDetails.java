package game.residents.interfaces.buildings;

import java.awt.Color;

import game.PlayerData;
import game.residents.Upgrade;
import game.residents.buildings.Armory;
import game.residents.interfaces.Clickable;
import game.residents.interfaces.DetailsPanel;
import game.residents.interfaces.UpgradePanel;
import graphics.Sidebar;

public class ArmoryDetails extends DetailsPanel implements Clickable {

	private UpgradePanel upgradePanel;
	
	public ArmoryDetails(Armory resident, Upgrade[] upgrades, boolean info) {
		super(400, resident, info);
		
		this.addInfo("Allows units:", 170, 30, new Color(224, 194, 177));		
		this.addInfo("Spearman", 115, 30, new Color(234, 204, 187));
		this.addInfo("Cavalry", 115, 30, new Color(234, 204, 187));	
		
		if (resident.playerData() == PlayerData.me && !info) {
			upgradePanel = new UpgradePanel(upgrades);
			upgradePanel.setBounds(0, (addHeight + 30), Sidebar.width, 600);
			add(upgradePanel);
		}
	}

	
	public ArmoryDetails(Armory resident, Upgrade[] upgrades) {
		this(resident, upgrades, true);
	}
	
	@Override
	public void pressed(double x, double y) {
		upgradePanel.pressed(x, y - (addHeight + 30));
	}
	
}
