package game;

import java.awt.Color;
import java.util.ArrayList;

import game.residents.Armory;
import game.residents.Base;
import game.residents.Farm;
import game.residents.Footman;
import game.residents.Mine;
import game.residents.Spearman;
import game.residents.TileResident;
import main.World;

public class Player {
	
	public static Player[] players = {new Player(new Color(114, 181, 204)), new Player(new Color(230,230,250))};
	public static Player currentPlayer = players[0];

	public boolean needPlaceBase = true;
	public int money = 0;
	private boolean boughtBuildingThisTurn = false;
	public Color color;
	private ArrayList<TileResident> limitedSupply = new ArrayList<>();
	private ArrayList<TileResident> canMake = new ArrayList<>();
	
	public Player(Color color) {
		this.color = color;
		
		this.addLimited(new Base(this));
	}
	
	public void addLimited(TileResident limited) {
		this.limitedSupply.add(limited);
		this.canMake.add(limited);
	}
	
	public void removeCanMake(TileResident resident) {
		this.canMake.remove(resident);
		
		if (resident instanceof Base) {
			this.addLimited(new Mine(this, 0));
			this.addLimited(new Mine(this, 0));
			this.limitedSupply.remove(resident);
			this.needPlaceBase = false;
		}
		
		if (resident.canBuildOn() == true) {
			this.boughtBuildingThisTurn = true;
		}
	}
		
	public void turnStart() {
		boughtBuildingThisTurn = false;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident.player() == this) {
					currentTile.resident.startOfTurnEffect();
				}
			}
		}
	}
	
	public TileResident[] makeOptions() {
		if (this.needPlaceBase) {
			return canMake.toArray(new TileResident[1]);
		}
		
		int size = canMake.size();
		for (int i = size - 1; i >= 0; i--) { //remove any remaining non limited options
			TileResident resident = canMake.get(i);
			if (!limitedSupply.contains(resident)) {
				canMake.remove(i);
			}
		}
		
		//add back non limited resources
		if (!boughtBuildingThisTurn) {
			canMake.add(new Mine(this));
			canMake.add(new Farm(this));
			canMake.add(new Armory(this));
		}
		
		if (this.ownedUnits() < this.maxUnits()) {
			canMake.add(new Footman(this));
			canMake.add(new Spearman(this));
		}
		
		return canMake.toArray(new TileResident[canMake.size()]);
	}
	
	public int ownedUnits() {
		int owned = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (!currentTile.resident.canBuildOn() && currentTile.resident.player() == this) {
					owned++;
				}
			}
		}
		
		return owned;
	}
	
	public int maxUnits() {
		int max = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident.player() == this) {
					max += currentTile.resident.maxUnitsBuff();
				}
			}
		}
		
		return max;
	}
	
	public ArrayList<String> ownedNames() {
		ArrayList<String> owned = new ArrayList<>();
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident.player() == this) {
					if (!owned.contains(currentTile.resident.name())) {
						owned.add(currentTile.resident.name());
					}
				}
			}
		}
		
		return owned;
	}
	
}
