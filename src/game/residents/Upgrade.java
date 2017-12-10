package game.residents;

import java.awt.Color;

import game.Player;
import game.PlayerData;

public class Upgrade {

	public Upgrade next = null;
	public Runnable action;
	public String name;
	public int cost;
	public Color color;
	
	public Upgrade(String name, int cost, Runnable upgradeAction) {
		this.name = name;
		this.action = upgradeAction;
		this.cost = cost;
	}
	
	public Upgrade run(PlayerData playerData) {
		this.action.run();
		if (playerData == PlayerData.me) {
			Player.player.money -= this.cost;
		}
		if (next != null) {
			next.color = this.color;
		}
		playerData.setBuffs();
		return next;
	}
	
}
