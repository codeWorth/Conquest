package game.residents.OverTimeEffects;

import game.OT;
import game.residents.TileResident;

public class SlowDoT extends OT {

	private int slowAmount;
	private TileResident target;
	
	public SlowDoT(int ticks, int slowAmount, TileResident target) {
		super(ticks, target.ots);
		this.slowAmount = slowAmount;
		this.target = target;
	}

	@Override
	protected void effect() {
		int actionsLeft = target.actionsLeft() - slowAmount;
		if (actionsLeft < 0) {
			actionsLeft = 0;
		}
		
		target.setActionsLeft(actionsLeft);		
	}

	@Override
	public String description() {
		return slowAmount + " less actions";
	}

	@Override
	protected boolean goodEffect() {
		return false;
	}

}
