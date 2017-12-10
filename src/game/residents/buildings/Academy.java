package game.residents.buildings;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.residents.TileResident;
import game.residents.Upgrade;
import game.residents.Upgradeable;
import game.residents.interfaces.buildings.AcademyDetails;

public class Academy extends TileResident implements Upgradeable {

	public int magicDamageBuff;
	public int magicHealthBuff;
	
	private Upgrade[] upgrades;
	
	public Academy(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 9;
		this.maxHealth = this.health;
		
		Upgrade upgrade1a = new Upgrade("Magic Health +1", 9, new Runnable() {
			@Override
			public void run() {
				magicHealthBuff = 1;
			}
		});
		upgrade1a.color = new Color(242, 133, 106);
		Upgrade upgrade1b = new Upgrade("Magic Health +2", 15, new Runnable() {
			@Override
			public void run() {
				magicHealthBuff = 2;
			}
		});
		upgrade1a.next = upgrade1b;
		Upgrade upgrade1c = new Upgrade("Magic Health +3", 25, new Runnable() {
			@Override
			public void run() {
				magicHealthBuff = 3;
			}
		});
		upgrade1b.next = upgrade1c;
		
		Upgrade upgrade2a = new Upgrade("Magic Damage +1", 8, new Runnable() {
			@Override
			public void run() {
				magicDamageBuff = 1;
			}
		});
		upgrade2a.color = new Color(190, 165, 247);
		Upgrade upgrade2b = new Upgrade("Magic Damage +2", 14, new Runnable() {
			@Override
			public void run() {
				magicDamageBuff = 2;
			}
		});
		upgrade2a.next = upgrade2b;
		Upgrade upgrade2c = new Upgrade("Magic Damage +3", 20, new Runnable() {
			@Override
			public void run() {
				magicDamageBuff = 3;
			}
		});
		upgrade2b.next = upgrade2c;
		
		upgrades = new Upgrade[]{upgrade1a, upgrade2a};
		
		URL file = getClass().getClassLoader().getResource("academy.bmp");
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
	public JComponent statsPanel(boolean info) {
		return new AcademyDetails(this, upgrades, info);
	}

	@Override
	public String name() {
		return "Academy";
	}

	@Override
	public int cost() {
		return 11;
	}

	@Override
	public String toString() {
		return "Academy:"+Integer.toString(this.health);
	}
	
	@Override
	public void upgrade(int slot) {
		upgrades[slot] = upgrades[slot].run(this.playerData());
	}
	
}
