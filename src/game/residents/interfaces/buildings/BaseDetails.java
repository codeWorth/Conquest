package game.residents.interfaces.buildings;

import game.PlayerData;
import game.residents.Upgrade;
import game.residents.buildings.Base;
import game.residents.interfaces.Clickable;
import game.residents.interfaces.DetailsPanel;
import game.residents.interfaces.UpgradePanel;
import graphics.Sidebar;

public class BaseDetails extends DetailsPanel implements Clickable {
	
	private UpgradePanel upgradePanel;
	
	public BaseDetails(Base resident, Upgrade[] upgrades, boolean info) {
		super(400, resident, info);
		
		if (resident.playerData() == PlayerData.me && !info) {
			upgradePanel = new UpgradePanel(upgrades);
			upgradePanel.setBounds(0, (addHeight + 30), Sidebar.width, 600);
			add(upgradePanel);
		}
	}
	
	public BaseDetails(Base resident, Upgrade[] upgrades) {
		this(resident, upgrades, true);
	}

	@Override
	public void pressed(double x, double y) {
		upgradePanel.pressed(x, y - (addHeight + 30));
	}
	
}
