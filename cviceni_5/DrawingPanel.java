import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;

/**
 * @author Lukas Runt
 * @version 1.0 (19-03-2020)
 */
public class DrawingPanel extends Component {
	private final double DEFAULT_R = 100;
	private final double VELIKOST_POSUN = 30;
	private final double EPSILON = 30;
	private int N = 6; //pocet cipu
	private Path2D star = new Path2D.Double();
	private Path2D starOkoli = new Path2D.Double();
	private double R = DEFAULT_R; //polomer
	private double R2 = R + VELIKOST_POSUN;//polomer okoli hvezdy
	public Color barvaHvezdy = Color.YELLOW;
	private double posunX = 0;
	private double posunY = 0;
	
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(640, 480));
		this.setFocusable(true);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//System.out.println("x = " + e.getX() + ", y = " + e.getY());
				double x = e.getX() - getWidth()/2 - posunX;
				double y = e.getY() - getHeight()/2 - posunY;
				if(star.contains(x, y)) {
					//System.out.println("Zasah");
					R = R * 0.5;
					repaint();
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'r') {
					R = DEFAULT_R;
					posunX = 0;
					posunY = 0;
					barvaHvezdy = Color.YELLOW;
					repaint();
				}
				if (e.getKeyChar() == 'p') {
					posunX += 5;
					repaint();
				}
				if (e.getKeyChar() == 'z') {
					posunX -= 5;
					repaint();
				}
			}
		});
		
		/**
		 * Reahuje na pohyb mysi, kdyz je mys 30px od hvezdy, 
		 * hvezda se pohne do jednoho z 8 smeru
		 */
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				double x = e.getX() - getWidth()/2 - posunX;
				double y = e.getY() - getHeight()/2 - posunY;
				//System.out.println("X: " + x + " Y: " + y);
				if(starOkoli.contains(x, y)) {
					//System.out.println("Zasah");
					if (x < 0 - (EPSILON)) {
						posunX += VELIKOST_POSUN;
					} else if (x > 0 + (EPSILON)) {
						posunX -= VELIKOST_POSUN;
					}
					if (y < 0 - (EPSILON)) {
						posunY += VELIKOST_POSUN;
					} else if (y > 0 + (EPSILON)) {
						posunY -= VELIKOST_POSUN;
					}
					if (0 <= y && y < Math.abs(EPSILON) || 0 <= x && x < Math.abs(EPSILON)) {
						posunY += VELIKOST_POSUN;
						posunX += VELIKOST_POSUN;
					}
					repaint();
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.translate((this.getWidth() / 2) + posunX, this.getHeight() /2 + posunY);
		drawStar(g2);
	}
	
	/**
	 * Nakresleni hvezdy s reagujici 30px ohranicenim
	 * @param g2 grafarna
	 */
	private void drawStar(Graphics2D g2) {
		star = new Path2D.Double();
		starOkoli = new Path2D.Double();
		double delta_fi = 2*Math.PI/N;
		
		star.moveTo(0,  -R); //prvni vrchol hvezdy (nahore)
		starOkoli.moveTo(0, -R2);
		for (int i = 0; i < N; i++) {
			double x = R*Math.cos(i*delta_fi + 1.5*Math.PI);
			double y = R*Math.sin(i*delta_fi + 1.5*Math.PI);
			star.lineTo(x, y);
			x = R2*Math.cos(i*delta_fi + 1.5*Math.PI);
			y = R2*Math.sin(i*delta_fi + 1.5*Math.PI);
			starOkoli.lineTo(x, y);
			
			double x2 = 0.5*R*Math.cos(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			double y2 = 0.5*R*Math.sin(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			star.lineTo(x2, y2);
			x2 = 0.5*R2*Math.cos(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			y2 = 0.5*R2*Math.sin(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			starOkoli.lineTo(x2, y2);
		}
		star.closePath();
		starOkoli.closePath();
		
		g2.setColor(barvaHvezdy);
		g2.fill(star);
		//g2.fill(starOkoli);
	}

}
