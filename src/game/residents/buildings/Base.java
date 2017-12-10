package game.residents.buildings;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.BoardTile;
import game.Player;
import game.PlayerData;
import game.network.NetworkClient;
import game.residents.EmptyResident;
import game.residents.TileResident;
import game.residents.Upgrade;
import game.residents.Upgradeable;
import game.residents.interfaces.buildings.BaseDetails;
import main.World;

public class Base extends TileResident implements Upgradeable {
		
	private Upgrade[] upgrades;
	
	public Base(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 6;
		this.maxHealth = this.health;
		
		this.upgrades = new Upgrade[1];
		this.upgrades[0] = new Upgrade("Town", 25, new Runnable() {
			@Override
			public void run() {
				Player.player.baseLevel = 1;
				URL file = getClass().getClassLoader().getResource("base1.bmp");
				try {
					image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 2, Board.TILE_SIZE - 2, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					e.printStackTrace();
				}
				health = 10;
			}
		});
		this.upgrades[0].color = new Color(214, 214, 214);
		Upgrade upgrade1b = new Upgrade("City", 70, new Runnable() {
			@Override
			public void run() {
				if (playerData == PlayerData.me) {
					Player.player.baseLevel = 2;
				}
			}
		});
		this.upgrades[0].next = upgrade1b;
		
		URL file = getClass().getClassLoader().getResource("base.bmp");
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
			if (this.playerData() == PlayerData.me) {
				NetworkClient.spectateGame();
			}
		}
	}

	@Override
	public void turnStart() {
		Player.player.money += 1;
	}

	@Override
	public int damage() {
		return 0;
	}

	@Override
	public JComponent statsPanel(boolean info) {
		return new BaseDetails(this, this.upgrades, info);
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
	public void upgrade(int slot) {
		this.upgrades[slot] = this.upgrades[slot].run(this.playerData());
	}
}
