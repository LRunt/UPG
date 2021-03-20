import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


/**
 * @author Lukas Runt
 * @version 1.0 (12-03-2020)
 */
public class BasicDrawing {

	/**
	 * Vstupni bod programu
	 * @param args
	 */
	public static void main(String[] args) {
		//Vytvoreni okna
		JFrame okno = new JFrame();
		okno.setTitle("cv4 - Lukas Runt - A20B0226P");
		okno.setSize(640, 480);
		
		DrawingPanel panel = new DrawingPanel();
		okno.add(panel);//pridani komponenty
		okno.pack(); //udela resize okna dle komponent
		
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
		okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
		okno.setVisible(true);
		
		Timer tm = new Timer();
		tm.schedule(new TimerTask() {
			@Override
			public void run() {
				panel.repaint();
			}
		}, 0, 20);
	}

}
