package game.residents.interfaces.buildings;

import java.awt.Color;

import game.PlayerData;
import game.residents.Upgrade;
import game.residents.buildings.Hospice;
import game.residents.interfaces.Clickable;
import game.residents.interfaces.DetailsPanel;
import game.residents.interfaces.UpgradePanel;
import graphics.Sidebar;

public class HospiceDetails extends DetailsPanel implements Clickable {

	private UpgradePanel upgradePanel;
	
	public HospiceDetails(Hospice resident, Upgrade[] upgrades, boolean info) {
		super(400, resident, info);
		
		this.addInfo("Allows units:", 170, 30, new Color(216, 136, 132));		
		this.addInfo("Medic", 110, 30, new Color(242, 157, 152));
		this.addInfo("With Academy:", 150, 30, new Color(216, 136, 132));		
		this.addInfo("Cleric", 110, 30, new Color(242, 157, 152));

		if (resident.playerData() == PlayerData.me && !info) {
			upgradePanel = new UpgradePanel(upgrades);
			upgradePanel.setBounds(0, (addHeight + 30), Sidebar.width, 600);
			add(upgradePanel);
		}
	}
	
	public HospiceDetails(Hospice resident, Upgrade[] upgrades) {
		this(resident, upgrades, true);
	}

	
	@Override
	public void pressed(double x, double y) {
		upgradePanel.pressed(x, y - (addHeight + 30));
	}
	
}
