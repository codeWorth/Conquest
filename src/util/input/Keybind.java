package util.input;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import util.actions.Action;

public class Keybind {

	public boolean state = false;
	public int key = 0;
	private ArrayList<Action> downActions = new ArrayList<Action>();
	private ArrayList<Action> upActions = new ArrayList<Action>();
	
	public Keybind(int key, JPanel panel, String name) {
		this.key = key;
		
		InputMap inputMap = panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionMap = panel.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(key, 0, false), name);
		inputMap.put(KeyStroke.getKeyStroke(key, 0, true), name+"up");
		
		actionMap.put(name, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = true;
				
				for (Action act : downActions) {
					act.execute();
				}
			}
			
		});
		actionMap.put(name+"up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = false;
				
				for (Action act : upActions) {
					act.execute();
				}
			}
			
		});
	}
	
	public void addDownAction(Action action) {
		this.downActions.add(action);
	}
	
	public void addUpAction(Action action) {
		this.upActions.add(action);
	}
	
}
