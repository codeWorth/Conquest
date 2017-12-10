package util.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import util.actions.Action;

public class MouseBind implements MouseMotionListener, MouseListener {

	private static final double mouseFudgeX = 0;
	private static final double mouseFudgeY = 0;
	
	public double mouseX = 0;
	public double mouseY = 0;
	private ArrayList<Action> moveActions = new ArrayList<Action>();
	private ArrayList<Action> clickActions = new ArrayList<Action>();
	private ArrayList<Action> rightClickActions = new ArrayList<Action>();
	
	public double centerX = 0;
	public double centerY = 0;
	
	public double angle = -Math.PI/2;
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	public void addMoveAction(Action action) {
		this.moveActions.add(action);
	}
	
	public void addClickAction(Action action) {
		this.clickActions.add(action);
	}
	
	public void addRightClickAction(Action action) {
		this.rightClickActions.add(action);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX() - centerX + mouseFudgeX;
		this.mouseY  = e.getY() - centerY + mouseFudgeY;
				
		this.angle = Math.atan2(mouseY, mouseX);
		
		for (Action act : moveActions) {
			act.execute();
		}
	}
	
	/**
	 * Length squared, high performace
	 * @return The length as a double
	 */
	public double length2() {
		return mouseX*mouseX + mouseY*mouseY;
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (Action act : clickActions) {
				act.execute();
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			for (Action act : rightClickActions) {
				act.execute();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}
	
}
