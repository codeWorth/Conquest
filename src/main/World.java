package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.Timer;

import game.Board;
import game.Player;
import graphics.Camera;
import graphics.Minimap;
import graphics.Sidebar;
import util.actions.Action;
import util.input.InputBinds;

public class World {

	public static Graphics2D ctx;
	
	public static int currentTurn = 0;
	public static Board board;
	public static Minimap minimap;
	private static final Color defaultMouseColor = new Color(0, 0, 0, 60);
	public static Color mouseColor = defaultMouseColor;
		
	public static Timer timer;
	public static int timerDelay = 100;
		
	public static void initialize() {		
		board = new Board(25, 25);
		minimap = new Minimap(50, 50, 4);
		
		InputBinds.selectedTile.addClickAction(new Action(null) {
			@Override
			public void execute() {
				if (InputBinds.selectedTile.mouseX < Camera.CAM_WIDTH) {
					board.pressTile(InputBinds.selectedTile.mouseX, InputBinds.selectedTile.mouseY);
					mouseColor = defaultMouseColor;
				} else {
					Sidebar.instance.press(InputBinds.selectedTile.mouseX - Camera.CAM_WIDTH, InputBinds.selectedTile.mouseY);
				}
			}
		});
		
		Player.currentPlayer.turnStart();
		board.newTurn();
		Sidebar.instance.redrawUI();
	}
	
	public static void nextTurn() {
		currentTurn++;
		if (currentTurn >= Player.players.length) {
			currentTurn = 0;
		}
		
		Player.currentPlayer = Player.players[currentTurn];
		Player.currentPlayer.turnStart();
		
		board.newTurn();
		Sidebar.instance.redrawUI();
	}
	
	public static void graphicsUpdate() {
		if (InputBinds.screenUp.state && Camera.CAM_Y > -Camera.ALLOWED_BUFFER) {
			Camera.CAM_Y -= Camera.MOVE_SPEED;
		} else if (InputBinds.screenDown.state && Camera.CAM_Y < board.totalWidth + Camera.ALLOWED_BUFFER - Camera.CAM_HEIGHT) {
			Camera.CAM_Y += Camera.MOVE_SPEED;
		}
		if (InputBinds.screenLeft.state && Camera.CAM_X > -Camera.ALLOWED_BUFFER) {
			Camera.CAM_X -= Camera.MOVE_SPEED;
		} else if (InputBinds.screenRight.state && Camera.CAM_X < board.totalWidth + Camera.ALLOWED_BUFFER - Camera.CAM_WIDTH) {
			Camera.CAM_X += Camera.MOVE_SPEED;
		}
		
		board.draw(ctx);
		
		int x = (int)((InputBinds.selectedTile.mouseX + Camera.CAM_X) / Board.TOTAL_TILE_SIZE);
		int screenX = x * Board.TOTAL_TILE_SIZE - Camera.CAM_X;
		int y = (int)((InputBinds.selectedTile.mouseY + Camera.CAM_Y) / Board.TOTAL_TILE_SIZE);
		int screenY = y * Board.TOTAL_TILE_SIZE - Camera.CAM_Y;
		
		ctx.setPaint(mouseColor);
		Rectangle shape = new Rectangle(screenX, screenY, Board.TILE_SIZE, Board.TILE_SIZE);
		ctx.fill(shape);
		
		if (minimap.inCameraWindow()) {
			minimap.draw(ctx);
		}
	}
	
}
