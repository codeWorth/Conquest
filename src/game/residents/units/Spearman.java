package game.residents.units;

import game.PlayerData;

public class Spearman extends TileUnitResident {
	
	public Spearman(PlayerData playerData) {
		super(playerData, 2, "spear.bmp");
	}
	
	@Override
	public int moveRange() {
		return 2;
	}

	@Override
	public int shootRange() {
		return 1;
	}
	
	@Override
	public int damage() {
		return 2 + damageIncrease();
	}

	@Override
	public String name() {
		return "Spearman";
	}

	@Override
	public int cost() {
		return 4;
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Armory"};
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
