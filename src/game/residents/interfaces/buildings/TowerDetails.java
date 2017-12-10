package game.residents.interfaces.buildings;

import java.awt.Color;

import game.residents.TileResident;
import game.residents.interfaces.DetailsPanel;

public class TowerDetails extends DetailsPanel {

	public TowerDetails(TileResident resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
	}
	
	public TowerDetails(TileResident resident) {
		this(resident, true);
	}
	
}
