package graphics;

import java.awt.Color;

public enum TileTint {
	NONE(null),
	GREY(new Color(100, 100, 100, 150)),
	RED(new Color(255, 0, 0, 150)),
	LIGHTRED(new Color(255, 0, 0, 60)),
	WHITE(new Color(255, 255, 255, 150))
	;
	
	public Color color;
	
	private TileTint(Color color) {
		this.color = color;
	}
}
