package game.residents.units;

import game.PlayerData;

public class Cavalry extends TileUnitResident {

	public Cavalry(PlayerData playerData) {
		super(playerData, 2, "cavalry.bmp");
	}
	
	@Override
	public int moveRange() {
		return 3;
	}

	@Override
	public int shootRange() {
		return 1;
	}
	
	@Override
	public int damage() {
		return 3 + damageIncrease();
	}

	@Override
	public String name() {
		return "Cavalry";
	}

	@Override
	public int cost() {
		return 6;
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Armory"};
	}
	

	@Override
	public int healthIncrease() {
		return this.playerData.lightHealthIncrease;
	}

	@Override
	public int damageIncrease() {
		return this.playerData.lightDamageIncrease;
	}
	
}
