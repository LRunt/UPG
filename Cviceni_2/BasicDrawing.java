import javax.swing.JFrame;

/**
 * @author Lukas Runt
 * @version 1.0 (26-02-2020)
 */
public class BasicDrawing {

	/**
	 * Vstupni bod programu
	 * @param args
	 */
	public static void main(String[] args) {
		//Vytvoreni okna
		JFrame okno = new JFrame();
		okno.setTitle("cv2 - Lukas Runt - A20B0226P");
		okno.setSize(640, 480);
		
		okno.add(new DrawingPanel());//pridani komponenty
		okno.pack(); //udela resize okna dle komponent
		
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
		okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
		okno.setVisible(true);

	}

}
