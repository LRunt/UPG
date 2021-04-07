import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;



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
		okno.setTitle("cv6 - Lukas Runt - A20B0226P");
		okno.setSize(640, 480);
		
		DrawingPanel panel = new DrawingPanel();
		okno.add(panel, BorderLayout.CENTER);//pridani komponenty
		
		
		try {
			BufferedImage im = (ImageIO.read (new File("data\\jungle.jpg")));
			double pomer = im.getWidth()/(double)im.getHeight();
			double sirka = 1043;
			panel.setBackgroundImage(im);
			im = (ImageIO.read(new File("data\\girl.jpg")));
			panel.fuj(im);
			im = new BufferedImage((int)sirka, (int)(sirka/pomer), BufferedImage.TYPE_3BYTE_BGR);
				
			panel.drawJungle(im.createGraphics(), im.getWidth(), im.getHeight());
			panel.drawGirl(im.createGraphics(), im.getWidth(), im.getHeight());
			
			ImageIO.write(im, "jpeg", new File("data\\vystup.jpg"));
			
			BufferedImage girl = (ImageIO.read(new File("data\\girl.jpg")));
			panel.setFrontImage(girl);
			
			Timer tm = new Timer();
			tm.schedule(new TimerTask() {
				@Override
				public void run() {
					panel.setFrontImage(girl);
					panel.repaint();
				}
			}, 0, 20);
		} catch (IOException e) {
			System.out.println("Soubor nenalezen!");
			e.printStackTrace();
		}
		
		okno.pack(); //udela resize okna dle komponent
		
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
		okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
		okno.setVisible(true);
	}

}