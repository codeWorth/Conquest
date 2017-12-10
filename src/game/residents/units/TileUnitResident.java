package game.residents.units;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.OT;
import game.PlayerData;
import game.network.NetworkClient;
import game.residents.TileResident;
import game.residents.interfaces.units.UnitDetails;
import game.residents.interfaces.units.UnitInterface;

public abstract class TileUnitResident extends TileResident {

	protected int actionsLeft;
	protected boolean canAttack = true;
	
	public TileUnitResident(PlayerData playerData, int health, String fileName) {
		this.playerData = playerData;
		this.health = health;
		this.maxHealth = this.health;

		URL file = getClass().getClassLoader().getResource(fileName);
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 14, Board.TILE_SIZE - 14, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	public JPanel userInterface() {
		return new UnitInterface(canAttack);
	}

	@Override
	public void attack(TileResident other) {
		other.takeDamage(this.damage());
		
		if (this.distanceTo(other) <= 1 && !other.canBuildOn()) {
			this.takeDamage(other.damage());
		}
		this.canAttack = false;
		
		NetworkClient.sendAttack(this.location.x, this.location.y, other.location.x, other.location.y);
	}

	@Override
	public JComponent statsPanel(boolean info) {
		return new UnitDetails(this, info);
	}
	
	@Override
	public void startOfTurnEffect() {
		this.canAttack = true;
		for (OT ot : this.ots) {
			ot.startOfTurnTick();
		}
		turnStart();
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
		return this.name()+":"+Integer.toString(this.health)+","+Integer.toString(this.actionsLeft)+","+Boolean.toString(canAttack);
	}
	
}
