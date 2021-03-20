import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author Lukas Runt
 * @version 1.0 (19-03-2020)
 */
public class BasicDrawing {

	/**
	 * Vstupni bod programu
	 * @param args
	 */
	public static void main(String[] args) {
		//Vytvoreni okna
		JFrame okno = new JFrame();
		okno.setTitle("cv5 - Lukas Runt - A20B0226P, Program reaguje na klavesy p a z");
		okno.setSize(640, 480);
		
		DrawingPanel panel = new DrawingPanel();
		okno.add(panel, BorderLayout.CENTER);//pridani komponenty
		
		JPanel buttonPanel = new JPanel();
		
		//Tlacitko ke skonceni aplikace
		JButton btnExit = new JButton("Exit");
		buttonPanel.add(btnExit);
		btnExit.addActionListener(e -> okno.dispose());
		
		JButton btnBarva = new JButton("L575");
		buttonPanel.add(btnBarva);
		btnBarva.addActionListener(e -> {panel.barvaHvezdy = Color.GREEN; okno.repaint();});
		
		okno.add(buttonPanel, BorderLayout.SOUTH);
		
		okno.pack(); //udela resize okna dle komponent
		
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
		okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
		okno.setVisible(true);
	}

}
