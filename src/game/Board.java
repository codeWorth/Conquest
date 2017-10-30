package game;

import java.awt.Graphics2D;

import game.residents.EmptyResident;
import graphics.Camera;
import graphics.Drawable;
import graphics.Sidebar;
import graphics.Surface;
import graphics.TileTint;
import util.input.InputBinds;

public class Board implements Drawable {
	//grace is gud
	private enum SelectMode {
		NONE, ATTACK, MOVE;
	}

	public static final int TILE_SIZE = 50;
	public static final int TILE_SPACING = 5;
	public static final int TOTAL_TILE_SIZE = TILE_SIZE + TILE_SPACING;
	public int totalWidth, totalHeight;
	
	public BoardTile selectedTile;
	public BoardTile[][] tiles;
	private TileTint[][] tileTints;
		
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
	}

	@Override
	public boolean inCameraWindow() {
		return true;
	}
		
	public void pressTile(double mouseX, double mouseY) {		
		int x = (int)((InputBinds.selectedTile.mouseX + Camera.CAM_X) / Board.TOTAL_TILE_SIZE);
		int y = (int)((InputBinds.selectedTile.mouseY + Camera.CAM_Y) / Board.TOTAL_TILE_SIZE);
		
		if (Player.currentPlayer.needPlaceBase) {
			if (tiles[x][y].resident.player() == null) {
				resetTiles();
				
				selectedTile = tiles[x][y];
				tileTints[x][y] = TileTint.WHITE;
				
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
				selectedTile.resident.reduceActionsLeft(1);
				
				if (selectedTile.resident.health() <= 0) {
					selectedTile.resident = new EmptyResident();
				}
				if (tiles[x][y].resident.health() <= 0) {
					tiles[x][y].resident = new EmptyResident();
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
			if (tiles[x][y].resident.player() == null) {
				if (hasFriendlyBordering(x, y)) {
					resetTiles();
					
					selectedTile = tiles[x][y];
					tileTints[x][y] = TileTint.WHITE;
					
					Surface.instance.addUI(tiles[x][y].resident.userInterface());
					Sidebar.instance.addStats(tiles[x][y].resident.statsPanel());
				} else {
					deselectTile();
				}
			} else if (tiles[x][y].resident.player() != Player.currentPlayer) {
				deselectTile();

				if (tiles[x][y].resident.player() != null) {
					tileTints[x][y] = TileTint.WHITE;
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
					
					tileTints[x][y] = TileTint.WHITE;
					Sidebar.instance.addStats(selectedTile.resident.statsPanel());
				} else {
					deselectTile();
				}
			}
			
			break;
		}
	}
	
	private boolean hasFriendlyBordering(int x, int y) {
		Player upPlayer = null;
		Player downPlayer = null;
		Player leftPlayer = null;
		Player rightPlayer = null;
		if (x > 0 && tiles[x-1][y].resident.canBuildOn()) {
			leftPlayer = tiles[x-1][y].resident.player();
		}
		if (x < tiles.length - 1 && tiles[x+1][y].resident.canBuildOn()) {
			rightPlayer = tiles[x+1][y].resident.player();
		}
		if (y > 0 && tiles[x][y-1].resident.canBuildOn()) {
			upPlayer = tiles[x][y-1].resident.player();
		}
		if (y < tiles[0].length - 1 && tiles[x][y+1].resident.canBuildOn()) {
			downPlayer = tiles[x][y+1].resident.player();
		}
		
		return upPlayer == Player.currentPlayer || 
				downPlayer == Player.currentPlayer || 
				leftPlayer == Player.currentPlayer || 
				rightPlayer == Player.currentPlayer;
	}
	
	public void highlightAllowed() {
		resetTiles();
		selectMode = SelectMode.MOVE;
		
		int stepsLeft = selectedTile.resident.actionsLeft();
		int x = selectedTile.x;
		int y = selectedTile.y;
		
		floodFillMovement(x, y, stepsLeft + 1);
		tileTints[x][y] = TileTint.WHITE;
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
				if (tile.resident.health() > 0 && tile.resident.player() != Player.currentPlayer) {
					tileTints[newX][newY] = TileTint.RED;
					canShootHere[newX][newY] = true;
				} else {
					tileTints[newX][newY] = TileTint.LIGHTRED;
				}
			}
		}
		
		tileTints[x][y] = TileTint.WHITE;
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
		tiles[newX][newY].resident = selectedTile.resident;
		selectedTile.resident = new EmptyResident();
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
				if (tile.resident.player() == Player.currentPlayer) {
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


