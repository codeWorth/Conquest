package game.residents.units;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.PlayerData;
import game.residents.TileResident;
import game.residents.interfaces.units.HealInterface;
import game.residents.interfaces.units.HealerDetails;

public class Medic extends TileUnitResident {

	public int healing = 1;
	
	public Medic(PlayerData playerData) {
		super(playerData, 2, "spear.bmp");
	}
	
	@Override
	public int moveRange() {
		return 4;
	}

	@Override
	public int shootRange() {
		return 1;
	}
	
	@Override
	public int damage() {
		return damageIncrease();
	}

	@Override
	public String name() {
		return "Medic";
	}

	@Override
	public int cost() {
		return 13;
	}
	
	@Override
	public void attack(TileResident other) {
		other.takeDamage(-this.healing);
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Infirmary"};
	}
	
	@Override
	public JPanel userInterface() {
		return new HealInterface(this.canAttack);
	}

	@Override
	public JComponent statsPanel(boolean info) {
		return new HealerDetails(this, info);
	}
	
	@Override
	public int healthIncrease() {
		return this.playerData().lightHealthIncrease;
	}
	
	@Override
	public boolean canTarget(TileResident resident) {
		return resident.playerData() == PlayerData.me;
	}
	
}
