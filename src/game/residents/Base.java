package game.residents;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.interfaces.BaseDetails;

public class Base extends TileResident {
		
	public Base(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 6;
		
		File file = new File("src/graphics/Icons/base.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 2, Board.TILE_SIZE - 2, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public JPanel userInterface() {
		return null;
	}

	@Override
	public void takeDamage(int damage) {
		this.health -= damage;
		
		if (this.health <= 0) {
			System.out.println("You lost!");
			System.exit(0);
		}
	}

	@Override
	public void startOfTurnEffect() {
		Player.player.money += 1;
	}

	@Override
	public int damage() {
		return 0;
	}

	@Override
	public JPanel statsPanel() {
		return new BaseDetails(this);
	}

	@Override
	public String name() {
		return "Village";
	}

	@Override
	public int cost() {
		return 0;
	}

	@Override
	public int maxUnitsBuff() {
		return 3;
	}

	@Override
	public String toString() {
		return "Base:"+Integer.toString(this.health);
	}

	@Override
	public int healthIncrease() {
		return 0;
	}

	@Override
	public int damageIncrease() {
		return 0;
	}
}
