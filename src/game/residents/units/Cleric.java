package game.residents.units;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.PlayerData;
import game.residents.TileResident;
import game.residents.interfaces.units.HealInterface;
import game.residents.interfaces.units.HealerDetails;

public class Cleric extends TileUnitResident {

	public int healing = 2;
	
	public Cleric(PlayerData playerData) {
		super(playerData, 2, "cleric.bmp");
	}
	
	@Override
	public int moveRange() {
		return 2;
	}

	@Override
	public int shootRange() {
		return 3;
	}
	
	@Override
	public int damage() {
		return damageIncrease();
	}

	@Override
	public String name() {
		return "Cleric";
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
	public int cost() {
		return 20;
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Infirmary", "Academy"};
	}
	
	@Override
	public boolean canTarget(TileResident resident) {
		return resident.playerData() == PlayerData.me;
	}
	
}
