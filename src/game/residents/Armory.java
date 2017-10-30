package game.residents;

import java.awt.Color;

import javax.swing.JPanel;

import game.Player;
import game.residents.interfaces.ArmoryDetails;

/**
 * @author cHriS The BIIiGgeSt man EVeR
 *
 */

public class Armory extends TileResident {
	
	public Armory(Player player) {
		this.player = player;
		this.health = 5;
	}
	
	@Override
	public boolean canBuildOn() {
		return true;
	}

	@Override
	public Color mapColor() {
		return new Color(175, 179, 188);
	}

	@Override
	public JPanel userInterface() {
		return null;
	}

	@Override
	public JPanel statsPanel() {
		return new ArmoryDetails(this);
	}

	@Override
	public String name() {
		return "Armory";
	}

	@Override
	public int cost() {
		return 6;
	}

	@Override
	public String toString() {
		return "Armory:"+Integer.toString(this.health);
	}

}
