package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.BoardTile;
import main.World;
import util.actions.Action;
import util.input.InputBinds;

public class Minimap implements Drawable {

	private static final int buffer = 10;

	private int x, y, tileSize;
	private int width, height;
	
	private boolean shouldShow = false;
	
	public Minimap(int x, int y, int tileSize) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		
		this.width = this.tileSize * World.board.tiles.length;
		this.height = this.tileSize * World.board.tiles[0].length;
		
		InputBinds.showMinimap.addDownAction(new Action(null) {
			@Override
			public void execute() {
				shouldShow = !shouldShow;
			}
		});
	}

	@Override
	public void draw(Graphics2D ctx) {
		ctx.setPaint(new Color(0, 0, 0, 165));
		Rectangle background = new Rectangle(x - buffer, y - buffer, width + buffer * 2, height + buffer * 2);
		ctx.fill(background);
		
		ctx.setPaint(Color.white);
		ctx.setStroke(new BasicStroke(3));
		ctx.draw(background);
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile tile = World.board.tiles[i][j];
				Color color = tile.resident.minimapColor();
				ctx.setColor(color);
				Rectangle rect = new Rectangle(x + i * tileSize, y + j * tileSize, tileSize, tileSize);
				ctx.fill(rect);
			}
		}
		
		int camX = Camera.CAM_X * width / World.board.totalWidth + x;
		int camY = Camera.CAM_Y * height / World.board.totalHeight + y;
		int camWidth = Camera.CAM_WIDTH * width / World.board.totalWidth;
		int camHeight = Camera.CAM_HEIGHT * height / World.board.totalHeight;
		
		Rectangle cameraIcon = new Rectangle(camX, camY, camWidth, camHeight);
		ctx.setColor(Color.white);
		ctx.setStroke(new BasicStroke(1));
		ctx.draw(cameraIcon);
	}

	@Override
	public boolean inCameraWindow() {
		return shouldShow;
	}
	
	
	
}
