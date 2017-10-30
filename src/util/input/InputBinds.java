package util.input;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class InputBinds {

	public static Keybind screenUp;
	public static Keybind screenDown;
	public static Keybind screenLeft;
	public static Keybind screenRight;
	public static Keybind showMinimap;
	
	public static MouseBind selectedTile = new MouseBind();
	
	public static void bind(JPanel bindTo) {		
		screenUp = new Keybind(KeyEvent.VK_UP, bindTo, "screenNorth");
		screenDown = new Keybind(KeyEvent.VK_DOWN, bindTo, "screenDown");
		screenLeft = new Keybind(KeyEvent.VK_LEFT, bindTo, "screenLeft");
		screenRight = new Keybind(KeyEvent.VK_RIGHT, bindTo, "screenRight");
		showMinimap = new Keybind(KeyEvent.VK_TAB, bindTo, "showMinimap");
		
		bindTo.addMouseMotionListener(selectedTile);
		bindTo.addMouseListener(selectedTile);
	}
	
}
