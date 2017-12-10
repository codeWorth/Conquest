package game.residents.units;

import javax.swing.JComponent;

import game.PlayerData;
import game.residents.TileResident;
import game.residents.interfaces.units.MagicUnitDetails;

public class Warlock extends TileUnitResident {
	
	public Warlock(PlayerData playerData) {
		super(playerData, 4, "spear.bmp");
	}
	
	@Override
	public int moveRange() {
		return 1;
	}

	@Override
	public int shootRange() {
		return 3;
	}
	
	@Override
	public int damage() {
		return 2 + damageIncrease();
	}

	@Override
	public String name() {
		return "Warlock";
	}

	@Override
	public int cost() {
		return 18;
	}
	
	@Override
	public JComponent statsPanel(boolean info) {
		return new MagicUnitDetails(this, info);
	}
	
	@Override
	public void attack(TileResident other) {
		super.attack(other);
		this.takeDamage(-this.damage());
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Academy"};
	}
	
}
