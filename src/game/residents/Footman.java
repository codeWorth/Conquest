package game.residents;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.interfaces.UnitDetails;
import game.residents.interfaces.UnitInterface;

public class Footman extends TileResident {
	
	private int actionsLeft;
	private boolean canAttack = true;
	
	public Footman(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 2;
		
		File file = new File("src/graphics/Icons/sword.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 14, Board.TILE_SIZE - 14, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int moveRange() {
		return 1;
	}

	@Override
	public boolean canBuildOn() {
		return false;
	}

	@Override
	public Image mapImage() {
		return image;
	}

	@Override
	public int shootRange() {
		return 1;
	}

	@Override
	public JPanel userInterface() {
		return new UnitInterface(canAttack);
	}
	
	@Override
	public void startOfTurnEffect() {
		this.canAttack = true;
	}

	@Override
	public void attack(TileResident other) {
		other.takeDamage(this.damage());
		
		if (this.shootRange() == 1 && other.shootRange() == 1) {
			this.takeDamage(other.damage());
		}
		this.canAttack = false;
	}
	
	@Override
	public int damage() {
		return 1 + damageIncrease();
	}

	@Override
	public JPanel statsPanel() {
		return new UnitDetails(this);
	}

	@Override
	public String name() {
		return "Footman";
	}

	@Override
	public int cost() {
		return 2;
	}

	public int actionsLeft() {
		return this.actionsLeft;
	}
	public void setActionsLeft(int actions) {
		this.actionsLeft = actions;
	}
	public void reduceActionsLeft(int amount) {
		this.actionsLeft -= amount;
		if (this.actionsLeft < 0) {
			this.actionsLeft = 0;
		}
	}
	
	@Override
	public String toString() {
		return "Footman:"+Integer.toString(this.health)+","+Integer.toString(this.actionsLeft)+","+Boolean.toString(canAttack);
	}
	

	@Override
	public int healthIncrease() {
		return Player.player.heavyHealthIncrease;
	}

	@Override
	public int damageIncrease() {
		return Player.player.heavyDamageIncrease;
	}
	
}
