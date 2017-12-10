package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.residents.EmptyResident;
import game.residents.TileResident;
import graphics.Camera;
import graphics.Drawable;

public class BoardTile implements Drawable {
	
	private TileResident resident;
	public TileResident resident() {
		return this.resident;
	}
	public void setResident(TileResident resident) {
		this.resident = resident;
		resident.location = this;
	}
	
	public int x, y;
	
	private int animIndex = 0;
	private static final int animLength = 30;
	private static final int colorR = 255;
	private static final int colorG = 0;
	private static final int colorB = 0;
	
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
			ctx.drawImage(this.resident.mapImage(), this.screenX(), this.screenY(), null);
		} else {
			
			if (this.resident.canBuildOn()) {
				
				ctx.setPaint(this.resident.playerData().color);
				Rectangle shape = new Rectangle(this.screenX(), this.screenY(), Board.TILE_SIZE, Board.TILE_SIZE);
				ctx.fill(shape);
				
				ctx.drawImage(this.resident.mapImage(), this.screenX() + 1, this.screenY() + 1, null);
				
			} else {
							
				ctx.setPaint(this.resident.playerData().color);
				Rectangle shape = new Rectangle(this.screenX(), this.screenY(), Board.TILE_SIZE, Board.TILE_SIZE);
				ctx.fill(shape);
				
				ctx.drawImage(this.resident.mapImage(), this.screenX() + 7, this.screenY() + 7, null);
				
			}
		}
	}
	
	public void animateSelected(Graphics2D ctx) {
		double half = animLength / 2;
		double opacity = (-Math.abs(half - animIndex) + half)/half * 100 + 155;
		ctx.setPaint(new Color(colorR, colorG, colorB, (int)opacity));
		
		int leftX = this.screenX() - Board.TILE_SPACING;
		int rightX = this.screenX() + Board.TILE_SIZE + Board.TILE_SPACING;
		int topY = this.screenY() - Board.TILE_SPACING;
		int bottomY = this.screenY() + Board.TILE_SIZE + Board.TILE_SPACING;
		
		Rectangle shape = new Rectangle(leftX, topY, Board.TILE_SPACING, Board.TILE_SIZE + Board.TILE_SPACING*2);
		ctx.fill(shape);
		shape = new Rectangle(leftX + Board.TILE_SPACING, topY, Board.TILE_SIZE, Board.TILE_SPACING);
		ctx.fill(shape);
		
		shape = new Rectangle(rightX - Board.TILE_SPACING, topY, Board.TILE_SPACING, Board.TILE_SIZE + Board.TILE_SPACING*2);
		ctx.fill(shape);
		shape = new Rectangle(leftX + Board.TILE_SPACING, bottomY - Board.TILE_SPACING, Board.TILE_SIZE, Board.TILE_SPACING);
		ctx.fill(shape);
		
		animIndex++;
		if (animIndex > animLength) {
			animIndex = 0;
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