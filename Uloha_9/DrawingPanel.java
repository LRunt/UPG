import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(600, 480));
	}
	
	@Override
	public void paint(Graphics g) {	
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g; 

	}
}
