package game.residents.interfaces.buildings;

import java.awt.Color;

import game.PlayerData;
import game.residents.Upgrade;
import game.residents.buildings.Mine;
import game.residents.interfaces.Clickable;
import game.residents.interfaces.DetailsPanel;
import game.residents.interfaces.UpgradePanel;
import graphics.Sidebar;

public class MineDetails extends DetailsPanel implements Clickable {

	private UpgradePanel upgradePanel;
	
	public MineDetails(Mine resident, Upgrade[] upgrades, boolean info) {
		super(400, resident, info);
		
		String text = Integer.toString(resident.moneyPerTurn) + " Gold per Turn";
		Color color = new Color(234, 242, 145);
		this.addInfo(text, 160, 30, color);
		
		if (resident.playerData() == PlayerData.me && !info) {
			upgradePanel = new UpgradePanel(upgrades);
			upgradePanel.setBounds(0, (addHeight + 30), Sidebar.width, 600);
			add(upgradePanel);
		}
	}
	
	public MineDetails(Mine resident, Upgrade[] upgrades) {
		this(resident, upgrades, true);
	}

	@Override
	public void pressed(double x, double y) {
		upgradePanel.pressed(x, y - (addHeight + 30));
	}
	
}
