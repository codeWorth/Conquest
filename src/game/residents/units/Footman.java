package game.residents.units;

import game.PlayerData;

public class Footman extends TileUnitResident {
	
	public Footman(PlayerData playerData) {
		super(playerData, 2, "sword.bmp");
	}
	
	@Override
	public int moveRange() {
		return 1;
	}

	@Override
	public int shootRange() {
		return 1;
	}
	
	@Override
	public int damage() {
		return 1 + damageIncrease();
	}

	@Override
	public String name() {
		return "Footman";
	}

	@Override
	public int cost() {
		return 2;
	}

	@Override
	public int healthIncrease() {
		return this.playerData.heavyHealthIncrease;
	}

	@Override
	public int damageIncrease() {
		return this.playerData.heavyDamageIncrease;
	}
	
}
