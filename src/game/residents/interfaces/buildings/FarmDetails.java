package game.residents.interfaces.buildings;

import java.awt.Color;

import game.residents.buildings.Farm;
import game.residents.interfaces.DetailsPanel;

public class FarmDetails extends DetailsPanel {
	
	public FarmDetails(Farm resident, boolean info) {
		super(400, resident, info);
		
		String text = Integer.toString(resident.maxUnitsBuff()) + " More Units";
		Color color = new Color(234, 204, 187);
		this.addInfo(text, 160, 30, color);
	}
	
	public FarmDetails(Farm resident) {
		this(resident, true);
	}
	
}
