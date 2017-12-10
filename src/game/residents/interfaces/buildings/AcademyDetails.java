package game.residents.interfaces.buildings;

import java.awt.Color;

import game.PlayerData;
import game.residents.Upgrade;
import game.residents.buildings.Academy;
import game.residents.interfaces.Clickable;
import game.residents.interfaces.DetailsPanel;
import game.residents.interfaces.UpgradePanel;
import graphics.Sidebar;

public class AcademyDetails extends DetailsPanel implements Clickable {

	private UpgradePanel upgradePanel;
	
	public AcademyDetails(Academy resident, Upgrade[] upgrades, boolean info) {
		super(400, resident, info);
		
		this.addInfo("Allows units:", 170, 30, new Color(198, 154, 209));		
		this.addInfo("Warlock", 110, 30, new Color(219, 179, 229));
		this.addInfo("Fire Mage", 110, 30, new Color(219, 179, 229));
		this.addInfo("Ice Mage", 110, 30, new Color(219, 179, 229));
		this.addInfo("With Infirmary:", 150, 30, new Color(198, 154, 209));		
		this.addInfo("Cleric", 110, 30, new Color(219, 179, 229));
		
		if (resident.playerData() == PlayerData.me && !info) {
			upgradePanel = new UpgradePanel(upgrades);
			upgradePanel.setBounds(0, (addHeight + 30), Sidebar.width, 600);
			add(upgradePanel);
		}
	}
	
	public AcademyDetails(Academy resident, Upgrade[] upgrades) {
		this(resident, upgrades, true);
	}
	
	@Override
	public void pressed(double x, double y) {
		upgradePanel.pressed(x, y - (addHeight + 30));
	}
	
}
