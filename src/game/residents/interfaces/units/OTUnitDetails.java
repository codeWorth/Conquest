package game.residents.interfaces.units;

import java.awt.Color;

import game.residents.interfaces.DetailsPanel;
import game.residents.units.Fire_Mage;
import game.residents.units.Ice_Mage;

public class OTUnitDetails extends DetailsPanel {

	public OTUnitDetails(Fire_Mage resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
		this.addInfo("Fire DoT:", 140, 30, new Color(247, 124, 93));
		this.addInfo("Duration: " + resident.duration, 120, 30, new Color(247, 124, 93));
		this.addInfo("Damage: " + resident.fireDoT, 110, 30, new Color(247, 124, 93));
	}
	
	public OTUnitDetails(Fire_Mage resident) {
		this(resident, true);
	}
	
	public OTUnitDetails(Ice_Mage resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
		this.addInfo("Freeze DoT:", 140, 30, new Color(201, 216, 252));
		this.addInfo("Duration: " + resident.duration, 120, 30, new Color(201, 216, 252));
		this.addInfo("Slowing: " + resident.slowAmount, 110, 30, new Color(201, 216, 252));
	}
	
	public OTUnitDetails(Ice_Mage resident) {
		this(resident, true);
	}
	
}
