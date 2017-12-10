package game.residents.interfaces.units;

import java.awt.Color;

import game.residents.interfaces.DetailsPanel;
import game.residents.units.Cleric;
import game.residents.units.Medic;

public class HealerDetails extends DetailsPanel {

	public HealerDetails(Medic resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
		this.addInfo(resident.healing + " Healing", 110, 30, new Color(172, 216, 249));
	}
	
	public HealerDetails(Medic resident) {
		this(resident, true);
	}
	
	public HealerDetails(Cleric resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
		this.addInfo(resident.healing + " Healing", 110, 30, new Color(172, 216, 249));
	}
	
	public HealerDetails(Cleric resident) {
		this(resident, true);
	}
	
}
