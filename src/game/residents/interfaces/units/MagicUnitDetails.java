package game.residents.interfaces.units;

import java.awt.Color;

import game.residents.interfaces.DetailsPanel;
import game.residents.units.Alchemist;
import game.residents.units.Warlock;
import game.residents.units.Wizard;

public class MagicUnitDetails extends DetailsPanel {

	public MagicUnitDetails(Warlock resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack and Healing", 180, 30, new Color(255, 251, 56), 14);
	}

	public MagicUnitDetails(Warlock resident) {
		this(resident, true);
	}
	
	public MagicUnitDetails(Wizard resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
		this.addInfo(resident.chainLength + " Chain Distance", 180, 30, new Color(204, 181, 255));
		this.addInfo(resident.maxChains + " Max Chains", 170, 30, new Color(204, 181, 255));
	}
	
	public MagicUnitDetails(Wizard resident) {
		this(resident, true);
	}
	
	public MagicUnitDetails(Alchemist resident, boolean info) {
		super(400, resident, info);
		
		this.addInfo(resident.shootRange() + " Range", 110, 30, Color.GREEN);
		this.addInfo(resident.moveRange() + " Speed", 110, 30, Color.YELLOW);
		this.addInfo(resident.damage() + " Attack", 110, 30, new Color(255, 251, 56));
		this.addInfo("AoE: " + resident.areaOfEffect, 110, 30, new Color(152, 226, 155));
	}
	
	public MagicUnitDetails(Alchemist resident) {
		this(resident, true);
	}
	
}
