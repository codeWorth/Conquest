package game.residents.units;

import javax.swing.JComponent;

import game.PlayerData;
import game.network.NetworkClient;
import game.residents.TileResident;
import game.residents.OverTimeEffects.SlowDoT;
import game.residents.interfaces.units.OTUnitDetails;

public class Ice_Mage extends TileUnitResident {

	public int slowAmount = 1;
	public int duration = 4;
	
	public Ice_Mage(PlayerData playerData) {
		super(playerData, 3, "ice_mage.bmp");
	}
	
	@Override
	public int moveRange() {
		return 3;
	}

	@Override
	public int shootRange() {
		return 2;
	}
	
	@Override
	public int damage() {
		return 2 + damageIncrease();
	}

	@Override
	public String name() {
		return "Ice Mage";
	}

	@Override
	public int cost() {
		return 15;
	}
	
	@Override
	public void attack(TileResident other) {
		other.takeDamage(this.damage());
		
		if (this.distanceTo(other) <= 1) {
			this.takeDamage(other.damage());
		}
		
		new SlowDoT(duration, slowAmount, other); //the DoT adds itself boi ;)

		NetworkClient.sendAttack(this.location.x, this.location.y, other.location.x, other.location.y);

		
		this.canAttack = false;
	}

	@Override
	public JComponent statsPanel(boolean info) {
		return new OTUnitDetails(this, info);
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Academy"};
	}

	@Override
	public int damageIncrease() {
		return this.playerData.magicDamageIncrease;
	}
	
}
