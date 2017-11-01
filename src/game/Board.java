package game;

import java.awt.Graphics2D;

import game.network.NetworkClient;
import game.residents.EmptyResident;
import game.residents.TileResident;
import graphics.Camera;
import graphics.Drawable;
import graphics.Sidebar;
import graphics.Surface;
import graphics.TileTint;
import main.World;
import util.input.InputBinds;

public class Board implements Drawable {
	//grace is gud
	private enum SelectMode {
		NONE, ATTACK, MOVE;
	}

	public static final int TILE_SIZE = 64;
	public static final int TILE_SPACING = 6;
	public static final int TOTAL_TILE_SIZE = TILE_SIZE + TILE_SPACING;
	public int totalWidth, totalHeight;
	
	public BoardTile selectedTile;
	private TileTint[][] tileTints;
		
	public BoardTile[][] tiles;
	public void setResident(int x, int y, TileResident resident) {
		tiles[x][y].resident = resident;
		NetworkClient.sendChange(x, y);
	}
	public void setSelectedResident(TileResident resident) {
		selectedTile.resident = resident;
		NetworkClient.sendChange(selectedTile.x, selectedTile.y);
	}
	
	private int[][] moveStepsLeft;
	
	private int actionsForPos(int x, int y, int residentRange) {
		return residentRange - moveStepsLeft[x][y] + 1;
	}
	
	private boolean[][] canShootHere;
	private SelectMode selectMode = SelectMode.NONE;
	
	public Board(int width, int height) {
		tiles = new BoardTile[width][height];
		tileTints = new TileTint[width][height];
		moveStepsLeft = new int[width][height];
		canShootHere = new boolean[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new BoardTile(i, j);
				tileTints[i][j] = TileTint.NONE;
			}
		}
		
		totalWidth = TOTAL_TILE_SIZE * width;
		totalHeight = TOTAL_TILE_SIZE * height;
	}
	
	@Override
	public void draw(Graphics2D ctx) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				BoardTile tile = tiles[i][j];
				if (tile.inCameraWindow()) {
					tile.draw(ctx);
					tile.drawTint(ctx, tileTints[i][j].color);
				}
			}
		}
		
		if (selectedTile != null) {
			selectedTile.animateSelected(ctx);
		}
	}

	@Override
	public boolean inCameraWindow() {
		return true;
	}
		
	public void pressTile(double mouseX, double mouseY) {		
		int x = (int)((InputBinds.selectedTile.mouseX + Camera.CAM_X) / Board.TOTAL_TILE_SIZE);
		int y = (int)((InputBinds.selectedTile.mouseY + Camera.CAM_Y) / Board.TOTAL_TILE_SIZE);
		
		if (!World.isMyTurn) {
			BoardTile tile = tiles[x][y];
			if (tile.resident.canSelect() && tile.resident.playerData() != PlayerData.noPlayer) {
				deselectTile();
				Surface.instance.removeUI();
				Sidebar.instance.addStats(tile.resident.statsPanel());
			} else {
				deselectTile();
			}
			
			return;
		}
		
		if (Player.player.needPlaceBase) {
			if (tiles[x][y].resident.playerData() == PlayerData.noPlayer) {
				resetTiles();
				
				selectedTile = tiles[x][y];
				
				Surface.instance.addUI(tiles[x][y].resident.userInterface());
				Sidebar.instance.addStats(tiles[x][y].resident.statsPanel());
			} else {
				deselectTile();
			}
			
			return;
		}
		
		switch (selectMode) {
		case ATTACK:
			if (canShootHere[x][y]) {
				selectedTile.resident.attack(tiles[x][y].resident);
				NetworkClient.sendAttack(selectedTile.x, selectedTile.y, x, y);
				selectedTile.resident.reduceActionsLeft(1);
				
				if (selectedTile.resident.health() <= 0) {
					setSelectedResident(new EmptyResident());
				}
				if (tiles[x][y].resident.health() <= 0) {
					setResident(x, y, new EmptyResident());
				}	
			}
			
			deselectTile();
			break;
		case MOVE:
			if (selectedTile != null && actionsForPos(x, y, selectedTile.resident.actionsLeft()) <= selectedTile.resident.actionsLeft() && moveStepsLeft[x][y] != -1) {
				moveResident(x, y);
				deselectTile();
			} else {
				deselectTile();
			}
			
			break;
		case NONE:
			if (tiles[x][y].resident.playerData() == PlayerData.noPlayer) {
				if (hasFriendlyBordering(x, y)) {
					resetTiles();
					selectedTile = tiles[x][y];					
					Surface.instance.addUI(tiles[x][y].resident.userInterface());
					Sidebar.instance.addStats(tiles[x][y].resident.statsPanel());
				} else {
					deselectTile();
				}
			} else if (tiles[x][y].resident.playerData() != PlayerData.me) {
				deselectTile();

				if (tiles[x][y].resident.playerData() != PlayerData.noPlayer) {
					Surface.instance.removeUI();
					Sidebar.instance.addStats(tiles[x][y].resident.statsPanel());
				}
			} else {
				selectedTile = tiles[x][y];
				
				if (selectedTile.resident.canSelect()) {
					resetTiles();

					if (selectedTile.resident.actionsLeft() > 0) {
						Surface.instance.addUI(tiles[x][y].resident.userInterface());
					} else {
						Surface.instance.removeUI();
					}
					
					Sidebar.instance.addStats(selectedTile.resident.statsPanel());
				} else {
					deselectTile();
				}
			}
			
			break;
		}
	}
	
	private boolean hasFriendlyBordering(int x, int y) {
		if (x > 0 && tiles[x-1][y].resident.canBuildOn() && tiles[x-1][y].resident.playerData() == PlayerData.me) {
			return true;
		}
		if (x < tiles.length - 1 && tiles[x+1][y].resident.canBuildOn() && tiles[x+1][y].resident.playerData() == PlayerData.me) {
			return true;
		}
		if (y > 0 && tiles[x][y-1].resident.canBuildOn() && tiles[x][y-1].resident.playerData() == PlayerData.me) {
			return true;
		}
		if (y < tiles[0].length - 1 && tiles[x][y+1].resident.canBuildOn() && tiles[x][y+1].resident.playerData() == PlayerData.me) {
			return true;
		}
		
		return false;
	}
	
	public void highlightAllowed() {
		resetTiles();
		selectMode = SelectMode.MOVE;
		
		int stepsLeft = selectedTile.resident.actionsLeft();
		int x = selectedTile.x;
		int y = selectedTile.y;
		
		floodFillMovement(x, y, stepsLeft + 1);
		moveStepsLeft[x][y] = -1;
	
	}
	
	private void floodFillMovement(int x, int y, int stepsLeft) {
		if (stepsLeft > 0) {
			tileTints[x][y] = TileTint.GREY;
			int originalSteps = moveStepsLeft[x][y];
			moveStepsLeft[x][y] = stepsLeft;
			
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int a = x + i;
					int b = y + j;
					
					if (a >= 0 && a < tiles.length && b >= 0 && b < tiles[0].length) {
						int newSteps = stepsLeft - Math.abs(i) - Math.abs(j);
						if (newSteps > originalSteps && tiles[a][b].resident.canMoveThrough()) {
							floodFillMovement(a, b, newSteps);
						}
					}
				}
			}
		}
	}
	
	private void resetTiles() {
		selectMode = SelectMode.NONE;
		
		for (int i = 0; i < tileTints.length; i++) {
			for (int j = 0; j < tileTints[i].length; j++) {
				tileTints[i][j] = TileTint.NONE;
				moveStepsLeft[i][j] = -1;
				canShootHere[i][j] = false;
			}
		}
	}
	
	public void highlightRange() {
		resetTiles();
		selectMode = SelectMode.ATTACK;
		
		int range = selectedTile.resident.shootRange();
		int x = selectedTile.x;
		int y = selectedTile.y;
		
		for (int i = -range; i <= range; i++) {
			int width = -Math.abs(i) + range;
			for (int j = -width; j <= width; j++) {
				int newX = x + i;
				int newY = y + j;
				
				BoardTile tile = tiles[newX][newY];
				if (tile.resident.health() > 0 && tile.resident.playerData() != PlayerData.me) {
					tileTints[newX][newY] = TileTint.RED;
					canShootHere[newX][newY] = true;
				} else {
					tileTints[newX][newY] = TileTint.LIGHTRED;
				}
			}
		}
		
		canShootHere[x][y] = false;
	}
	
	public void deselectTile() {
		Surface.instance.removeUI();
		Sidebar.instance.removeStats();
		selectedTile = null;
		resetTiles();
	}
	
	private void moveResident(int newX, int newY) {
		selectedTile.resident.reduceActionsLeft(actionsForPos(newX, newY, selectedTile.resident.actionsLeft()));
		setResident(newX, newY, selectedTile.resident);
		setSelectedResident(new EmptyResident());
	}
	
	public void newTurn() {
		deselectTile();
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j].resident.setActionsLeft(0);
			}
		}
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				BoardTile tile = tiles[i][j];
				if (tile.resident.playerData() == PlayerData.me) {
					tiles[i][j].resident.setActionsLeft(tile.resident.moveRange());
				}
			}
		}
	}
	
	public void graceSucksDick(int timeTillDone) {
		if (timeTillDone < 3) {
			System.out.println("she succ gud");
		} else if (timeTillDone > 8) {
			System.out.println("she suck");
		} else {
			System.out.println("she succ");
		}
		
		if (timeTillDone > 0) {
			graceSucksDick(timeTillDone - 1);
		} else {
			System.out.println("she swallow !!");
		}
	}
	
}


