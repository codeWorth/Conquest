package game.residents;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.residents.interfaces.TowerDetails;
import game.residents.interfaces.TowerInterface;

public class Tower extends TileResident {

	private int actionsLeft;
	private boolean canAttack = true;
	
	public Tower(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 1;
		
		File file = new File("src/graphics/Icons/tower.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 2, Board.TILE_SIZE - 2, Image.SCALE_SMOOTH);
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
		return true;
	}

	@Override
	public Image mapImage() {
		return image;
	}

	@Override
	public int shootRange() {
		return 3;
	}

	@Override
	public JPanel userInterface() {
		return new TowerInterface(canAttack);
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
		return 2;
	}

	@Override
	public JPanel statsPanel() {
		return new TowerDetails(this);
	}

	@Override
	public String name() {
		return "Tower";
	}

	@Override
	public int cost() {
		return 8;
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
		return "Tower:"+Integer.toString(this.health)+","+Integer.toString(this.actionsLeft)+","+Boolean.toString(canAttack);
	}
	
}
