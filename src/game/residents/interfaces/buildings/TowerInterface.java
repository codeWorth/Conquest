package game.residents.interfaces.buildings;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.World;

public class TowerInterface extends JPanel {

	public TowerInterface(boolean canAttack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0, 0, 0, 165));
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.white), new EmptyBorder(15, 15, 15, 15)));
		setBounds(30, 30, 170, 80);
	    
	    JButton b2 = new JButton("Attack");
		b2.setFont(new Font("Helvetica", Font.PLAIN, 40));
	    b2.setVerticalTextPosition(AbstractButton.CENTER);
	    b2.setHorizontalTextPosition(AbstractButton.LEADING);
	    b2.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		World.board.highlightRange();
	    	}
	    });
	    b2.setEnabled(canAttack);
	    
	    add(b2);	    
	}
	
}
