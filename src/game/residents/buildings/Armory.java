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
import game.residents.interfaces.buildings.ArmoryDetails;

/**
 * @author cHriS The BIIiGgeSt man EVeR
 *
 */

public class Armory extends TileResident implements Upgradeable {
	
	public int lightHealthBuff;
	public int heavyHealthBuff;
	public int lightDamageBuff;
	public int heavyDamageBuff;
	
	private Upgrade[] upgrades;
	
	public Armory(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 5;
		
		Upgrade upgrade1a = new Upgrade("Light Health +1", 5, new Runnable() {
			@Override
			public void run() {
				lightHealthBuff = 1;
			}
		});
		upgrade1a.color = new Color(242, 133, 106);
		Upgrade upgrade1b = new Upgrade("Light Health +2", 9, new Runnable() {
			@Override
			public void run() {
				lightHealthBuff = 2;
			}
		});
		upgrade1a.next = upgrade1b;
		Upgrade upgrade1c = new Upgrade("Light Health +3", 15, new Runnable() {
			@Override
			public void run() {
				lightHealthBuff = 3;
			}
		});
		upgrade1b.next = upgrade1c;
		
		Upgrade upgrade2a = new Upgrade("Light Damage +1", 7, new Runnable() {
			@Override
			public void run() {
				lightDamageBuff = 1;
			}
		});
		upgrade2a.color = new Color(244, 236, 127);
		Upgrade upgrade2b = new Upgrade("Light Damage +2", 12, new Runnable() {
			@Override
			public void run() {
				lightDamageBuff = 2;
			}
		});
		upgrade2a.next = upgrade2b;
		Upgrade upgrade2c = new Upgrade("Light Damage +3", 17, new Runnable() {
			@Override
			public void run() {
				lightDamageBuff = 3;
			}
		});
		upgrade2b.next = upgrade2c;
		
		Upgrade upgrade3a = new Upgrade("Heavy Health +1", 4, new Runnable() {
			@Override
			public void run() {
				heavyHealthBuff = 1;
			}
		});
		upgrade3a.color = new Color(178, 100, 76);
		Upgrade upgrade3b = new Upgrade("Heavy Health +2", 8, new Runnable() {
			@Override
			public void run() {
				heavyHealthBuff = 2;
			}
		});
		upgrade3a.next = upgrade3b;
		Upgrade upgrade3c = new Upgrade("Heavy Health +3", 13, new Runnable() {
			@Override
			public void run() {
				heavyHealthBuff = 3;
			}
		});
		upgrade3b.next = upgrade3c;
		
		Upgrade upgrade4a = new Upgrade("Heavy Damage +1", 7, new Runnable() {
			@Override
			public void run() {
				lightDamageBuff = 1;
			}
		});
		upgrade4a.color = new Color(205, 214, 109);
		Upgrade upgrade4b = new Upgrade("Heavy Damage +2", 12, new Runnable() {
			@Override
			public void run() {
				lightDamageBuff = 2;
			}
		});
		upgrade4a.next = upgrade4b;
		Upgrade upgrade4c = new Upgrade("Heavy Damage +3", 17, new Runnable() {
			@Override
			public void run() {
				lightDamageBuff = 3;
			}
		});
		upgrade4b.next = upgrade4c;
		
		upgrades = new Upgrade[]{upgrade1a, upgrade2a, upgrade3a, upgrade4a};
		
		URL file = getClass().getClassLoader().getResource("armory.bmp");
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
		return new ArmoryDetails(this, upgrades, info);
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

	

	@Override
	public void upgrade(int slot) {
		upgrades[slot] = upgrades[slot].run(this.playerData());
	}

}
