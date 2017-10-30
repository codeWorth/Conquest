package game.residents;

import java.awt.Color;

import javax.swing.JPanel;

import game.Player;
import game.PlayerData;
import game.residents.interfaces.MineDetails;

public class Mine extends TileResident {

	public int moneyPerTurn = 3;
	private int cost = 7;
	
	public Mine(PlayerData playerData, int cost) {
		this.playerData = playerData;
		this.cost = cost;
		this.health = 4;	
	}
	
	public Mine(PlayerData playerData) {
		this(playerData, 4);
	}

	@Override
	public boolean canBuildOn() {
		return true;
	}

	@Override
	public Color mapColor() {
		return new Color(218, 242, 155);
	}

	@Override
	public JPanel userInterface() {
		return null;
	}

	@Override
	public void startOfTurnEffect() {
		Player.player.money += this.moneyPerTurn;
	}

	@Override
	public JPanel statsPanel() {
		return new MineDetails(this);
	}

	@Override
	public String name() {
		return "Gold Mine";
	}

	@Override
	public int cost() {
		return this.cost;
	}

	@Override
	public int maxUnitsBuff() {
		return 0;
	}
	

	@Override
	public String toString() {
		return "Base:"+Integer.toString(this.health)+","+Integer.toString(cost);
	}

}
