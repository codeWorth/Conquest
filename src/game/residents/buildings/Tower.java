package game.residents.buildings;

import java.awt.Image;
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.network.NetworkClient;
import game.residents.TileResident;
import game.residents.interfaces.buildings.TowerDetails;
import game.residents.interfaces.buildings.TowerInterface;

public class Tower extends TileResident {

	private int actionsLeft;
	private boolean canAttack = true;
	
	public Tower(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 4;
		this.maxHealth = this.health;

		URL file = getClass().getClassLoader().getResource("tower.bmp");
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
	public void turnStart() {
		this.canAttack = true;
	}

	@Override
	public void attack(TileResident other) {
		other.takeDamage(this.damage());
		
		if (this.distanceTo(other) <= 1) {
			this.takeDamage(other.damage());
		}
		
		NetworkClient.sendAttack(this.location.x, this.location.y, other.location.x, other.location.y);
		
		this.canAttack = false;
	}
	
	@Override
	public int damage() {
		return 2;
	}

	@Override
	public JComponent statsPanel(boolean info) {
		return new TowerDetails(this, info);
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
	
	@Override
	public boolean canTarget(TileResident resident) {
		return super.canTarget(resident) && !resident.canBuildOn();
	}
	
}

