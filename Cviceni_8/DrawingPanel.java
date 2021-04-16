import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel implements Printable{	
	private final double PACMAN_R = 350.0;
	private final double VYSKA = 100;
	private final double SIRKA = 300;
	private final int VELIKOST_TEXTU = 80;
	private final String text = "TEXT";
		
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));	
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(this.getWidth() / 2, 0);
		
		Area area = new Area(new Rectangle2D.Double(-SIRKA/2, 100, SIRKA, VYSKA));
		area.add(new Area(new Rectangle2D.Double(-SIRKA/2, 300, SIRKA, VYSKA)));
		g2.setClip(area);
		
		drawObrazek1(-SIRKA/2, 100, g2);
		drawObrazek2(-SIRKA/2, 300, g2);
		
	}
	
	/**
	 * Kresli obrazek 1
	 * @param x pozice x
	 * @param y pozice y
	 * @param g2 grafika
	 */
	private void drawObrazek1(double x, double y, Graphics2D g2) {
		drawPulvalec(x, y, SIRKA, VYSKA, 10, g2);
		g2.setFont(new Font("Arial", Font.BOLD, VELIKOST_TEXTU));
		FontMetrics font = g2.getFontMetrics();
		g2.setPaint(new LinearGradientPaint(new Point2D.Float((float)x, (float)y), new Point2D.Float((float)(x), (float)(y+VYSKA)), new float[] {0 , 1f}, new Color[] {new Color(1f, 1f, 1f) , new Color(0.8f, 0.8f, 0.9f)}));
		
		g2.drawString(text,- font.stringWidth(text)/2,(int)(y + VYSKA - ((VYSKA - (3*VELIKOST_TEXTU)/4)/2)));
	}
	
	/**
	 * Krasli obrazek 2
	 * @param x pozice x
	 * @param y pozice y
	 * @param g2 grafika
	 */
	private void drawObrazek2(double x, double y, Graphics2D g2) {
		double vyska = VYSKA/5;
		for(int i = 0; i < 5; i++) {
			drawPulvalec(x, y, SIRKA, vyska, 5, g2);
			y += vyska;
		}
		
		g2.setFont(new Font("Arial", Font.BOLD, VELIKOST_TEXTU));
		FontMetrics font = g2.getFontMetrics();
		g2.setColor(Color.WHITE);
		g2.drawString(text,- font.stringWidth(text)/2,(int)(y - VYSKA + VELIKOST_TEXTU - 2)); //Random vypocet :-)
	}
	
	/**
	 * Kresli pulvalecek
	 * @param x pozice x
	 * @param y pozice y
	 * @param sirka sirka valecku
	 * @param vyska vyska valecku
	 * @param sirkaBoku sikra pulkruhu na boku
	 * @param g2 grafika
	 */
	private void drawPulvalec(double x, double y, double sirka, double vyska, double sirkaBoku, Graphics2D g2) {
		Rectangle2D obdelnik = new Rectangle2D.Double(x, y, sirka, vyska);
		Ellipse2D bok1 = new Ellipse2D.Double(x-sirkaBoku, y, sirkaBoku*2, vyska);
		Ellipse2D bok2 = new Ellipse2D.Double(x + sirka - sirkaBoku, y, sirkaBoku*2, vyska);
		
		g2.setPaint(new LinearGradientPaint(new Point2D.Float((float)x, (float)y), new Point2D.Float((float)(x), (float)(y+vyska)), new float[] {0 , 0.3f, 0.8f}, new Color[] {new Color(0.5f, 0.5f, 0.5f) , new Color(0.7f, 0.7f, 0.7f), new Color(0.2f, 0.2f, 0.2f)}));
		
		g2.fill(obdelnik);
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(bok1);
		
		g2.setColor(Color.DARK_GRAY);
		g2.fill(bok2);
	}
	
	private void drawPacman(double R, Graphics2D g2) {
		double ecx = 0;
		double ecy = -R * 0.5;
		double ro = R * 0.25;
		double ri = ro * 0.5;
		
		Path2D eye = new Path2D.Double();
		eye.moveTo(ecx + ro, ecy);
		eye.lineTo(ecx, ecy + ro);
		eye.lineTo(ecx - ro, ecy);
		eye.lineTo(ecx, ecy - ro);
		eye.closePath();
		
		eye.moveTo(ecx + ri, ecy);
		eye.lineTo(ecx, ecy - ri);
		eye.lineTo(ecx - ri, ecy);
		eye.lineTo(ecx, ecy + ri);
		eye.closePath();
		
		
		Area pacman = new Area(new Ellipse2D.Double(-R, -R, 2*R, 2*R));
		pacman.subtract(new Area(eye));
		
		double D = 0.5 * R;
		Path2D trojuhelnik = new Path2D.Double();
		trojuhelnik.moveTo(0, 0);
		trojuhelnik.lineTo(R, D);
		trojuhelnik.lineTo(R, -D);
		trojuhelnik.closePath();
		pacman.subtract(new Area(trojuhelnik));
		
		g2.setPaint(new RadialGradientPaint(new Point2D.Float(0f, 0f), (float)(2*R), new float[] {0 , 1}, new Color[] {Color.YELLOW , Color.RED}));
		g2.fill(pacman);
		g2.setPaint(new LinearGradientPaint(new Point2D.Float(0f, 0f), new Point2D.Float(0, (float)R), new float[] {0 , 0.8f, 1}, new Color[] {new Color(0f, 0f, 0f, 0f) , new Color(0f, 0f, 0f, 0.2f), new Color(0f, 0f, 0f, 0.8f)}));
		g2.fill(pacman);
		
		g2.setClip(pacman);
		
		g2.setColor(Color.GREEN);
		
		double delta = R*0.2;
		double fr = R*0.05;
		for (double y = -R; y < R; y += delta) {
			for (double x = -R; x < R; x += delta) {
				g2.fill(new Ellipse2D.Double(x - fr, y - fr, 2*fr, 2*fr));
			}
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			return NO_SUCH_PAGE;
		}

		Graphics2D g2 = (Graphics2D)graphics;
		g2.translate(pageFormat.getWidth() / 2, pageFormat.getHeight() / 2);
		
		final double PR_MM = 50;
		final double mmToPt = 72 / 25.4;
		
		drawPacman(PR_MM * mmToPt, g2);
		
		return 0;
	}
	
}
