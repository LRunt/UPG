import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel implements Printable{	
	private final double PACMAN_R = 350.0;
		
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));	
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(this.getWidth() / 2, this.getHeight() / 2);
		
		drawPacman(PACMAN_R, g2);
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
		
		//g2.setColor(Color.RED);
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
