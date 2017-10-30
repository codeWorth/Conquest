package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.residents.EmptyResident;
import game.residents.TileResident;
import graphics.Camera;
import graphics.Drawable;

public class BoardTile implements Drawable {
	
	public TileResident resident;

	public int x, y;
	public int screenX() {
		return this.x * Board.TOTAL_TILE_SIZE - Camera.CAM_X;
	}
	public int screenY() {
		return this.y * Board.TOTAL_TILE_SIZE - Camera.CAM_Y;
	}
	
	public BoardTile(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.resident = new EmptyResident();
	}
	
	@Override
	public void draw(Graphics2D ctx) {
		if (this.resident.playerData() == PlayerData.noPlayer) {
			ctx.setPaint(this.resident.mapColor());
			Rectangle shape = new Rectangle(this.screenX(), this.screenY(), Board.TILE_SIZE, Board.TILE_SIZE);
			ctx.fill(shape);
		} else {
			ctx.setPaint(this.resident.minimapColor());
			Rectangle shape = new Rectangle(this.screenX(), this.screenY(), Board.TILE_SIZE, Board.TILE_SIZE);
			ctx.fill(shape);
			
			ctx.setPaint(this.resident.mapColor());
			shape = new Rectangle(this.screenX() + 7, this.screenY() + 7, Board.TILE_SIZE - 14, Board.TILE_SIZE - 14);
			ctx.fill(shape);
		}
		
		
	}
	
	public void drawTint(Graphics2D ctx, Color tintColor) {
		if (tintColor == null) {
			return;
		}
		
		ctx.setPaint(tintColor);
		Rectangle shape = new Rectangle(this.screenX(), this.screenY(), Board.TILE_SIZE, Board.TILE_SIZE);
		ctx.fill(shape);
	}
	
	@Override
	public boolean inCameraWindow() {
		// screen bounds
		int x1 = -Board.TILE_SIZE;
		int y1 = -Board.TILE_SIZE;
		int x2 = Camera.CAM_WIDTH;
		int y2 = Camera.CAM_HEIGHT;
		
		int myX = this.screenX();
		int myY = this.screenY();
		
		return !(myX < x1 || myX > x2 || myY < y1 || myY > y2);
	}
	
}
