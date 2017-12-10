	package game.residents.units;

import javax.swing.JComponent;

import game.BoardTile;
import game.PlayerData;
import game.network.NetworkClient;
import game.residents.TileResident;
import game.residents.interfaces.units.MagicUnitDetails;
import main.World;

public class Alchemist extends TileUnitResident {

	public int areaOfEffect = 1;
	
	public Alchemist(PlayerData playerData) {
		super(playerData, 2, "alchemist.bmp");
	}
	
	@Override
	public int moveRange() {
		return 3;
	}

	@Override
	public int shootRange() {
		return 2;
	}
	
	@Override
	public int damage() {
		return 1 + damageIncrease();
	}

	@Override
	public String name() {
		return "Alchemist";
	}
	
	@Override
	public JComponent statsPanel(boolean info) {
		return new MagicUnitDetails(this, info);
	}

	@Override
	public int cost() {
		return 10;
	}
	
	@Override
	public void attack(TileResident other) {
		int x = other.location.x;
		int y = other.location.y;
		int range = this.areaOfEffect;
		
		for (int i = -range; i <= range; i++) {
			int width = -Math.abs(i) + range;
			for (int j = -width; j <= width; j++) {
				int newX = x + i;
				int newY = y + j;
				
				BoardTile tile = World.board.tiles[newX][newY];
				if(tile.resident().playerData() != PlayerData.me && tile.resident().health() > 0) {
					tile.resident().takeDamage(this.damage());
					NetworkClient.sendDamage(tile.x, tile.y, this.damage());
				}
			}
		}
		
		if (this.distanceTo(other) <= 1) {
			this.takeDamage(other.damage());
			NetworkClient.sendDamage(this.location.x, this.location.y, other.damage());
		}
		
		this.canAttack = false;
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Laboratory"};
	}

	@Override
	public int damageIncrease() {
		return this.playerData.magicDamageIncrease;
	}
	
}
