package game.residents.units;

import javax.swing.JComponent;

import game.BoardTile;
import game.PlayerData;
import game.residents.TileResident;
import game.residents.interfaces.units.MagicUnitDetails;
import main.World;

public class Wizard extends TileUnitResident {

	public int chainLength = 2;
	public int maxChains = 3;
	
	public Wizard(PlayerData playerData) {
		super(playerData, 3, "spear.bmp");
	}
	
	@Override
	public int moveRange() {
		return 2;
	}

	@Override
	public int shootRange() {
		return 3;
	}
	
	@Override
	public int damage() {
		return 1 + damageIncrease();
	}

	@Override
	public String name() {
		return "Wizard";
	}

	@Override
	public int cost() {
		return 12;
	}
	
	@Override
	public JComponent statsPanel(boolean info) {
		return new MagicUnitDetails(this, info);
	}
	
	@Override
	public void attack(TileResident other) {
		int x = other.location.x;
		int y = other.location.y;
		
		chain(x, y, this.chainLength);
		
		this.canAttack = false;
	}
	
	private void chain(int x, int y, int chains) {
		if (chains == 0) {
			return;
		}
		
		int range = this.chainLength;
		World.board.tiles[x][y].resident().takeDamage(this.damage());
		
		for (int i = -range; i <= range; i++) {
			int width = -Math.abs(i) + range;
			for (int j = -width; j <= width; j++) {
				int newX = x + i;
				int newY = y + j;

				BoardTile tile = World.board.tiles[newX][newY];
				if(tile.resident().playerData() != PlayerData.me && tile.resident().health() > 0 && i != 0 && j != 0) {
					tile.resident().takeDamage(this.damage());
					chain(newX, newY, chains-1);
					return;
				}
			}
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
	
	@Override
	public boolean canTarget(TileResident resident) {
		return super.canTarget(resident) && !	resident.canBuildOn();
	}
	
}
