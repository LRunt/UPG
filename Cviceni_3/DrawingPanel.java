import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;

public class DrawingPanel extends Component {
	
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(500, 400));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		drawArrow(this.getWidth(), this.getHeight() , 237, 347, 30, g2);
	}

	private void drawArrow(double x1, double y1, double x2, double y2, double tip_length,Graphics2D g2) {

		double u_x = x2 - x1;
		double u_y = y2 - y1;
		double u_len1 = 1 / Math.sqrt(u_x * u_x + u_y*u_y);
		u_x *= u_len1;
		u_y *= u_len1;
		//u ma delku 1
		
		g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				
		//smer kolmy (jednotkova delka)
		double v_x = u_y;
		double v_y = -u_x;
		
		//smer kolmy - delka o 1/2 sirky hrotu
		v_x *= 0.5*tip_length;
		v_y *= 0.5*tip_length;
		
		double c_x = x2 - u_x*tip_length;
		double c_y = y2 - u_y*tip_length;
		
		g2.draw(new Line2D.Double(c_x + v_x, c_y + v_y, x2, y2));
		g2.draw(new Line2D.Double(c_x - v_x, c_y - v_y, x2, y2));
		
		double[] hrot_x = {x2, c_x + v_x,x2-u_x*(2*tip_length/3), c_x - v_x};//vyplneny hrot do 2/3
		double[] hrot_y = {y2, c_y + v_y,y2-u_y*(2*tip_length/3), c_y - v_y};
		
		Path2D hrot = new Path2D.Double();
		
		hrot.moveTo(hrot_x[0], hrot_y[0]);
		for(int i = 1; i < hrot_x.length;i++) {
			hrot.lineTo(hrot_x[i],hrot_y[i]);
		}
		hrot.closePath();
		g2.fill(hrot);
		g2.draw(hrot);
		
		g2.setStroke(new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		
		g2.draw(new Line2D.Double(x1, y1, x2-u_x*(2*tip_length/3), y2-u_y*(2*tip_length/3)));// cara nejde do x2, y2 kvuli sirce cary 
	}

}
