package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.Timer;

import game.Board;
import game.Player;
import game.network.NetworkClient;
import graphics.Camera;
import graphics.Minimap;
import graphics.Sidebar;
import graphics.Surface;
import util.actions.Action;
import util.input.InputBinds;

public class World {

	public static Graphics2D ctx;
	
	public static boolean isMyTurn;
	public static boolean isInfo;
	public static Board board;
	public static Minimap minimap;
	private static final Color defaultMouseColor = new Color(0, 0, 0, 60);
	public static Color mouseColor = defaultMouseColor;
	public static Thread networkThread;
		
	public static Timer timer;
	public static int timerDelay = 100;
		
	public static void initialize() {	
		isInfo = false;
		isMyTurn = false;
		board = new Board(25, 25);
		minimap = new Minimap(50, 50, 4);
		
		InputBinds.mouse.addClickAction(new Action(null) {
			@Override
			public void execute() {
				if (isInfo) {
					isInfo = false;
					Sidebar.instance.removeStats();
					if (board.selectedTile != null) {
						Sidebar.instance.addStats(board.selectedTile.resident().statsPanel());
					}
					return;
				}
				
				if (InputBinds.mouse.mouseX < Camera.CAM_WIDTH) {
					board.pressTile(InputBinds.mouse.mouseX, InputBinds.mouse.mouseY);
					mouseColor = defaultMouseColor;
				} else {
					Sidebar.instance.press(InputBinds.mouse.mouseX - Camera.CAM_WIDTH, InputBinds.mouse.mouseY);
				}
			}
		});
		
		InputBinds.mouse.addRightClickAction(new Action(null) {
			@Override
			public void execute() {
				if (InputBinds.mouse.mouseX >= Camera.CAM_WIDTH) {
					Sidebar.instance.rightPress(InputBinds.mouse.mouseX - Camera.CAM_WIDTH, InputBinds.mouse.mouseY);
				}
			}
		});
		
		Sidebar.detailsStart = 20;
		Sidebar.instance.redrawUI();
	}
	
	public static void nextTurn(boolean myTurn) {
		if (!myTurn) {
			NetworkClient.sendEndTurn();
			isMyTurn = false;
			Sidebar.instance.removeStats();
			Surface.instance.removeUI();
			Sidebar.detailsStart = 100;
		} else {
			isMyTurn = true;
			Sidebar.detailsStart = 255;
			
			board.newTurn();
			Player.player.turnStart();
		}
		
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
		
		int x = (int)((InputBinds.mouse.mouseX + Camera.CAM_X) / Board.TOTAL_TILE_SIZE);
		int screenX = x * Board.TOTAL_TILE_SIZE - Camera.CAM_X;
		int y = (int)((InputBinds.mouse.mouseY + Camera.CAM_Y) / Board.TOTAL_TILE_SIZE);
		int screenY = y * Board.TOTAL_TILE_SIZE - Camera.CAM_Y;
		
		ctx.setPaint(mouseColor);
		Rectangle shape = new Rectangle(screenX, screenY, Board.TILE_SIZE, Board.TILE_SIZE);
		ctx.fill(shape);
		
		if (minimap.inCameraWindow()) {
			minimap.draw(ctx);
		}
	}
	
}
