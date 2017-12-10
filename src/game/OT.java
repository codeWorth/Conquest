package game;

import java.awt.Color;
import java.util.ArrayList;

public abstract class OT {

	public int ticksLeft = 0;
	private ArrayList<OT> ots;
	public Object object;
	
	public OT(int ticks, ArrayList<OT> ots) {
		this.ticksLeft = ticks;
		this.ots = ots;
		ots.add(this);
	}
	
	public OT(int ticks, ArrayList<OT> ots, Object object) {
		this(ticks, ots);
		this.object = object;
	}
	
	protected abstract void effect();
	public void startOfTurnTick() {
		this.ticksLeft--;
		
		if (this.ticksLeft < 0) {
			ots.remove(this);
		} else {
			effect();
		}
	}
	
	public abstract String description();
	protected abstract boolean goodEffect();
	public Color color() {
		if (goodEffect()) {
			return new Color(124, 249, 139);
		} else {
			return new Color(255, 137, 117);
		}
	}
	
}
