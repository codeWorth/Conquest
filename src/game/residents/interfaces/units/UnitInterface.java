package game.residents.interfaces.units;

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

public class UnitInterface extends JPanel {
	
	public UnitInterface(boolean canAttack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0, 0, 0, 165));
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.white), new EmptyBorder(15, 15, 15, 15)));
		setBounds(30, 30, 170, 130);
				
		JButton b1 = new JButton("Move");
		b1.setFont(new Font("Helvetica", Font.PLAIN, 40));
	    b1.setVerticalTextPosition(AbstractButton.CENTER);
	    b1.setHorizontalTextPosition(AbstractButton.LEADING);
	    b1.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		World.board.highlightAllowed();
	    	}
	    });
	    
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
	    
	    add(b1);
	    add(b2);	    
	}
	
}
