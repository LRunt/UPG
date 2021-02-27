import java.awt.*;
import javax.swing.JPanel;


/**
 * @author Lukas Runt
 * @version 1.0 (26-02-2020)
 */
public class DrawingPanel extends JPanel {
	
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(500, 400));
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		final int R = 150; //polomer
		final int R2 = 75;
		final int R3 = 15;
		final int SIRKA_CARY = 15;
		final int POCET_PAPRSKU = 8;
		final int DP = 20;
		final int POSUN = 60;
		final double POSUN2 = 59.57;
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		
		
		int sirka = this.getWidth() / 2;
		int vyska = this.getHeight() / 2;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, sirka*4, vyska*4); //Bile pozadi
		g.setColor(Color.BLACK);
		g.fillOval(sirka - R2, vyska - R2, 2*R2, 2*R2);
		
		double fi = 2*Math.PI/POCET_PAPRSKU;
		for(int i = 0; i < POCET_PAPRSKU; i++) {
			int x1 = (int)(sirka + R2 * Math.cos(i*fi + POSUN));
			int y1 = (int)(vyska + R2 * Math.sin(i*fi + POSUN));
			int x2 = (int)(sirka + (R2+DP) * Math.cos(i*fi + POSUN));
			int y2 = (int)(vyska + (R2+DP) * Math.sin(i*fi + POSUN));
			g.fillOval(x2 - R3, y2 - R3, 2*R3, 2*R3);
			
			g.drawLine(x1, y1, x2, y2);
		}
		
		int x3 = (int)(sirka + R * Math.cos(fi + POSUN2)); 
		int y3 = (int)(vyska + R * Math.sin(fi + POSUN2));
		int x4 = (int)(sirka + (R-2*R) * Math.cos(fi + POSUN2)); 
		int y4 = (int)(vyska + (R-2*R) * Math.sin(fi + POSUN2));
		g2.setStroke(new BasicStroke(SIRKA_CARY + 10));
		g.setColor(Color.WHITE);
		g.drawLine(x3+1, y3, x4, y4);
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g.setColor(Color.BLACK);
		g.drawLine(x3+1, y3, x4, y4);
		g.drawOval(sirka - R, vyska - R, 2*R, 2*R);
	}
}
