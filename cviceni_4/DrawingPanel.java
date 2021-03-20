import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	
	private long startTime = System.currentTimeMillis();
	final double RYCHLOST_S_KOLY = 1.3;
	final double RYCHLOST_BEZ_KOLA = 0.4;
	
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(1500, 600));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		final double r1 = 150;
		final double r2 = 100;
		final double roztec = 350;
		double delkaTrakturku = r1 + r2 + roztec;
		
		double posun = (System.currentTimeMillis() - startTime);
		double posunMax = (10/13) * (3*this.getWidth()/4 + delkaTrakturku) + 4 * (this.getWidth()/4) + 3500; 
		double posunNaObrazovce = posun % (posunMax); 
		
		double posunTraktoruPoUpadnutiKola;
		double triCtrvtinyObrazovky = 3*(delkaTrakturku+this.getWidth())/4;
		
		Graphics2D g2 = (Graphics2D)g;
		
		final double hr = 30;
		
		g2.translate(0,  this.getHeight()-hr);
		g2.setColor(Color.GREEN);
		g2.fill(new Rectangle2D.Double(0,0,this.getWidth(), hr));
		
		g2.translate(-delkaTrakturku, 0); //nastaveni trakturku na zacatek obrazovky
		
		if(RYCHLOST_S_KOLY * posunNaObrazovce <= (3*this.getWidth())/4) {
			g2.translate(RYCHLOST_S_KOLY * posunNaObrazovce, 0);
			drawTractor(r1, r2, roztec, g2);
		}
		else {
			AffineTransform oldTr = g2.getTransform();
			posunTraktoruPoUpadnutiKola = (triCtrvtinyObrazovky + RYCHLOST_BEZ_KOLA * (posunNaObrazovce - RYCHLOST_S_KOLY * triCtrvtinyObrazovky));
			g2.translate(posunTraktoruPoUpadnutiKola, 0);
			drawTractorBezKola(r1, r2, roztec, g2);
			g2.setTransform(oldTr);
			g2.translate(RYCHLOST_S_KOLY*posunNaObrazovce+delkaTrakturku-r2, -r2);
			drawWheel(r2,RYCHLOST_S_KOLY,g2);		
		}
	}
	
	private void drawTractorBezKola(double r1, double r2, double roztec, Graphics2D g2) {
		final double W = roztec + 0.5 * (r1+r2);
		final double H1 = 2*r1;
		final double H2 = 2*r2;
		
		g2.rotate(Math.toRadians(5.5));
		g2.translate(0.5*r1, -0.5*r2);
		drawCabin(W, H1, H2, 1.5* r1, g2);
		g2.translate(-0.5*r1, 0.5*r2);
		g2.rotate(-Math.toRadians(5.5));
		
		g2.translate(r1, -r1);
		drawWheel(r1, RYCHLOST_BEZ_KOLA, g2);
	}
	
	private void drawTractor(double r1, double r2, double roztec, Graphics2D g2) {
		final double W = roztec + 0.5 * (r1+r2);
		final double H1 = 2*r1;
		final double H2 = 2*r2;
		
		g2.translate(0.5*r1, -0.5*r2);
		drawCabin(W, H1, H2, 1.5* r1, g2);
		g2.translate(-0.5*r1, 0.5*r2);
		
		g2.translate(r1, 0);
		drawWheels(r1, r2, roztec, g2);
	}
	
	private void drawCabin(double w, double hc, double he, double wc, Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fill(new Rectangle2D.Double(0, -hc, wc, hc));
		g2.fill(new Rectangle2D.Double(wc, -he, w - wc, he));
	}
	
	private void drawWheels(double r1,double r2,double d, Graphics2D g2) {
		AffineTransform oldTr = g2.getTransform();
		
		g2.translate(0, -r1);
		drawWheel(r1,RYCHLOST_S_KOLY, g2);
		g2.translate(d, r1 - r2);
		drawWheel(r2,RYCHLOST_S_KOLY, g2);
		
		g2.setTransform(oldTr);
	}
	
	private void drawWheel(double r,double rychlost, Graphics2D g2) {
		
		//pneumatika
		g2.setColor(Color.BLACK);
		g2.fill(new Ellipse2D.Double(-r, -r, 2*r, 2*r));
		
		//disk
		double r2 = 0.5*r;
		g2.setColor(Color.DARK_GRAY);
		g2.fill(new Ellipse2D.Double(-r2, -r2, 2*r2, 2*r2));
		
		//oj
		final double r3 = 20;
		g2.setColor(Color.ORANGE);
		g2.fill(new Ellipse2D.Double(-r3, -r3, 2*r3, 2*r3));
		
		//sroub
		final double rb = 5;
		final double d = 45;
		AffineTransform oldTr = g2.getTransform();
		
		final double SA = 360; //360 stupnu za 1 sekundu
		double elapsed = rychlost*((System.currentTimeMillis() - startTime)/1000.0);
		
		g2.rotate(Math.toRadians(45 + SA*elapsed));
		g2.setColor(Color.ORANGE);
		for (int i = 0; i < 4; i++) {
			g2.translate(d, 0);
			g2.fill(new Ellipse2D.Double(-rb, -rb, 2*rb, 2*rb));
			g2.translate(-d, 0);
			g2.rotate(Math.toRadians(90));
		}
		g2.setTransform(oldTr);
	}
}
