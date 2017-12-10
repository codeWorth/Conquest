package game.residents.interfaces.units;

import java.awt.Color;

import game.residents.TileResident;
import game.residents.interfaces.DetailsPanel;

public class UnitDetails extends DetailsPanel {
	
	public UnitDetails(TileResident resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(229, 229, 229));
	}
	
	public UnitDetails(TileResident resident) {
		this(resident, true);
	}
	
}
