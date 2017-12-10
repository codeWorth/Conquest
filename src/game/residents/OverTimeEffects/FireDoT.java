package game.residents.OverTimeEffects;

import game.OT;
import game.network.NetworkClient;
import game.residents.TileResident;

public class FireDoT extends OT {

	private int damage;
	private TileResident target;
	public FireDoT(int ticks, int damage, TileResident target) {
		super(ticks, target.ots);
		this.damage = damage;
		this.target = target;
	}

	@Override
	protected void effect() {
		target.takeDamage(this.damage);
		NetworkClient.sendDamage(target.location.x, target.location.y, this.damage);
	}

	@Override
	public String description() {
		return damage + " damage per turn";
	}

	@Override
	protected boolean goodEffect() {
		return false;
	}

}
