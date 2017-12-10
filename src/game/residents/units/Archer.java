package game.residents.units;

import game.PlayerData;

public class Archer extends TileUnitResident {
	
	public Archer(PlayerData playerData) {
		super(playerData, 1, "bow.bmp");
	}
	
	@Override
	public int moveRange() {
		return 1;
	}

	@Override
	public int shootRange() {
		return 2;
	}
	
	@Override
	public int damage() {
		return 1 + damageIncrease();
	}

	@Override
	public int cost() {
		return 2;
	}
	
	@Override
	public String name() {
		return "Archer";
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

