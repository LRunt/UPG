import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	
	private long startTime = System.currentTimeMillis();
	private long celkovyCas = (System.currentTimeMillis() - startTime);
	private int cas = 4;
	public double a = startTime - celkovyCas/1000;
	private BufferedImage bg_img = new BufferedImage(1,1, BufferedImage.TYPE_3BYTE_BGR);
	private BufferedImage imageb;
	private BufferedImage pomocny;
	private BufferedImage imagef;
	private BufferedImage fr_img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
	private Path2D petiuhelnik = new Path2D.Double();
	
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));
	}
	
	public void setBackgroundImage(BufferedImage img) {
		this.bg_img = img;
		mirrorImage();
	}
	
	public void setFrontImage(BufferedImage img) {
		this.fr_img = img;
		rotateImage();
		deleteGreenScreen();
		
	}
	
	private void mirrorImage() {
		int iW = bg_img.getWidth();
		int iH = bg_img.getHeight();
		this.imageb = new BufferedImage(iW, iH, BufferedImage.TYPE_3BYTE_BGR);
		int[] rgbArray = new int[iW*iH];
		bg_img.getRGB(0, 0, iW, iH, rgbArray, 0, iW);
		
		for (int y = 0; y < iH; y++) {
			for (int x = 0; x < iW; x++) {
			    imageb.setRGB(iW - x - 1, y, bg_img.getRGB(x,y));
			  }
		}
	}
	
	private void rotateImage() {
		int iW = fr_img.getWidth();
		int iH = fr_img.getHeight();
		int[] rgbArray = new int[iW*iH];
		this.pomocny = new BufferedImage(iW, iH, BufferedImage.TYPE_4BYTE_ABGR);
		fr_img.getRGB(0, 0, iW, iH, rgbArray, 0, iW);
		
		for (int y = 0; y < iH; y++) {
			for (int x = 0; x < iW; x++) {
			    pomocny.setRGB(iW - x - 1, y, fr_img.getRGB(x,y));
			  }
		}
	}
	
	private void deleteGreenScreen() {
		int konst1 = 80;
		double konst2 = 1.5;
		int iW = fr_img.getWidth();
		int iH = fr_img.getHeight();
		celkovyCas = (System.currentTimeMillis() - startTime);
		a = ((double)celkovyCas/1000)/(double)cas;
		if(a > 1) {
				a = 1;
			}
		this.imagef = new BufferedImage(iW, iH, BufferedImage.TYPE_4BYTE_ABGR);
		int[] rgbArray = new int[iW*iH];
		pomocny.getRGB(0, 0, iW, iH, rgbArray, 0, iW);
		for (int i = 0; i < rgbArray.length; i++) {
			int in_rgb = rgbArray[i];
			int b = in_rgb & 0xFF;
			int g = (in_rgb >> 8) & 0xFF;
			int r = (in_rgb >> 16) & 0xFF;
			int p = (in_rgb >> 24) & 0xFF;
			
			if(g > konst1 && (r + b < g * konst2)) {
				p = 0;
			} else {
				p = (int)(255 * a + 0*(1-a));
			}
			/*int gr = (1*b + 3*r + 6*g) /10;
			b = gr;
			g = gr;
			r = gr;*/		
			int out_rgb =(p << 24) | (r << 16) | (g << 8) | b;
			rgbArray[i] = out_rgb;
		}
		imagef.setRGB(0, 0, iW, iH, rgbArray, 0, iW);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		drawJungle(g2, this.getWidth(), this.getHeight());
		drawGirl(g2, this.getWidth(), this.getHeight());
	}
	
	/**
	 * 
	 * @param g2
	 * @param W - pozadovana vyska
	 * @param H - pozadovana sirka
	 */
	public void drawJungle(Graphics2D g2, int W, int H) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, W, H);
		
		int iW = imageb.getWidth();
		int iH = imageb.getHeight();
		double scaleX = ((double)W) / iW;
		double scaleY = ((double)H) / iH;
		double scale = Math.min(scaleX, scaleY);
		
		int niW = (int)(iW*scale);
		int niH = (int)(iH*scale);
		int startX = (W - niW) / 2;
		int startY = (H - niH) / 2;
		
		g2.drawImage(imageb, startX, startY, niW, niH, null);
		
		drawPravidelny5Uhelnik(g2, startX, startY, niW, niH);
		//g2.fillOval(startX + niW * 467/1000 - R, startY + niH * 122/689 - R, 2*R, 2*R);
	}
	
	private void drawPravidelny5Uhelnik(Graphics2D g2, int startX, int startY, int niW, int niH) {
		AffineTransform oldTr = g2.getTransform();
		int N = 5;
		int R = this.getWidth()/10;
		double delta_fi = 2*Math.PI/N;
		g2.translate(startX + niW * 500/1000, startY + niH * 270/689);
		
		petiuhelnik.moveTo(0,  -R);
		for (int i = 0; i < N; i++) {
			double x = R*Math.cos(i*delta_fi + 1.5*Math.PI);
			double y = R*Math.sin(i*delta_fi + 1.5*Math.PI);
			petiuhelnik.lineTo(x, y);
		}
		petiuhelnik.closePath();
		g2.setColor(Color.YELLOW);
		g2.fill(petiuhelnik);
		g2.setTransform(oldTr);
	}
	
	public void drawGirl(Graphics2D g2, int W, int H) {
		int iW = imageb.getWidth();
		int iH = imageb.getHeight();
		double scaleX = ((double)W) / iW;
		double scaleY = ((double)H) / iH;
		double scale = Math.min(scaleX, scaleY);
		
		int niW = (int)(iW*scale);
		int niH = (int)(iH*scale);
		int startX = (W - niW) / 2;
		int startY = (H - niH) / 2;
		
		g2.drawImage(imagef, startX, startY, niW, niH, null);
	}
	
	/**
	 * Animace rozbila export, exportovala se jen cerna obrazovka.
	 * Uz musim delat PPA2 -> snazim se ocurat oko na zacatku nakreslim asiatku nepruhlednou a snad to oko nerozpozna :-)
	 * @param img
	 */
	public void fuj(BufferedImage img) {
		this.fr_img = img;
		rotateImage();
		hodneVelkyFuj();
	}
	
	private void hodneVelkyFuj() {
			int konst1 = 80;
			double konst2 = 1.5;
			int iW = fr_img.getWidth();
			int iH = fr_img.getHeight();
			this.imagef = new BufferedImage(iW, iH, BufferedImage.TYPE_4BYTE_ABGR);
			int[] rgbArray = new int[iW*iH];
			pomocny.getRGB(0, 0, iW, iH, rgbArray, 0, iW);
			for (int i = 0; i < rgbArray.length; i++) {
				int in_rgb = rgbArray[i];
				int b = in_rgb & 0xFF;
				int g = (in_rgb >> 8) & 0xFF;
				int r = (in_rgb >> 16) & 0xFF;
				int p = (in_rgb >> 24) & 0xFF;
				
				if(g > konst1 && (r + b < g * konst2)) {
					p = 0;
				}
				int out_rgb =(p << 24) | (r << 16) | (g << 8) | b;
				rgbArray[i] = out_rgb;
			}
		imagef.setRGB(0, 0, iW, iH, rgbArray, 0, iW);
		
	}
		
}