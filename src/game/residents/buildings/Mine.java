package game.residents.buildings;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.TileResident;
import game.residents.Upgrade;
import game.residents.Upgradeable;
import game.residents.interfaces.buildings.MineDetails;

public class Mine extends TileResident implements Upgradeable {

	public int moneyPerTurn = 3;
	private int cost = 5;
	private Upgrade[] upgrades;
	
	public Mine(PlayerData playerData, int cost) {
		this.playerData = playerData;
		this.cost = cost;
		this.health = 4;	
		this.maxHealth = this.health;

		Upgrade upgrade1a = new Upgrade("Money +1", 8, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 4;
			}
		});
		upgrade1a.color = new Color(242, 133, 106);
		Upgrade upgrade1b = new Upgrade("Money +2", 13, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 5;
			}
		});
		upgrade1a.next = upgrade1b;
		Upgrade upgrade1c = new Upgrade("Money +4", 20, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 7;
			}
		});
		upgrade1b.next = upgrade1c;
		Upgrade upgrade1d = new Upgrade("Money +6", 28, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 9;
			}
		});
		upgrade1c.next = upgrade1d;
		
		this.upgrades = new Upgrade[]{upgrade1a};
		
		URL file = getClass().getClassLoader().getResource("goldmine.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 2, Board.TILE_SIZE - 2, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Mine(PlayerData playerData) {
		this(playerData, 5);
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
	public void turnStart() {
		Player.player.money += this.moneyPerTurn;
	}

	@Override
	public JComponent statsPanel(boolean info) {
		return new MineDetails(this, this.upgrades, info);
	}

	@Override
	public String name() {
		return "Gold Mine";
	}

	@Override
	public int cost() {
		return this.cost + Player.player.ownedMines * 2;
	}

	@Override
	public int maxUnitsBuff() {
		return 0;
	}
	

	@Override
	public String toString() {
		return "Mine:"+Integer.toString(this.health)+","+Integer.toString(cost);
	}

	@Override
	public void upgrade(int slot) {
		upgrades[slot] = upgrades[slot].run(this.playerData());
	}
	
}
