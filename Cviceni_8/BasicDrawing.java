import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Lukas Runt 
 * @version 1.0 (2021-04-16)
 */
public class BasicDrawing {
public static DrawingPanel panel;
	public static void main(String[] args) {
		
		JFrame okno = new JFrame();
		okno.setTitle("cv8 - Lukas Runt, A20B0226P - L132");
		okno.setSize(640, 480);
		
		panel = new DrawingPanel();
		okno.add(panel, BorderLayout.CENTER); //prida komponentu		
		
		JPanel buttonPanel = new JPanel();
		okno.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnPrint = new JButton("Print...");
		buttonPanel.add(btnPrint, BorderLayout.WEST);
		btnPrint.addActionListener(e -> vytiskni(e));
		
		
		JButton btnExit = new JButton("Exit");
		buttonPanel.add(btnExit, BorderLayout.WEST);
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				okno.dispose();	//close okno => exit
			}
		});
		
		okno.pack(); //udela resize okna dle komponent
		
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null); //vycentrovat na obrazovce
		okno.setVisible(true);
	}

	private static void vytiskni(ActionEvent e) {
		PrinterJob job = PrinterJob.getPrinterJob();
		if (job.printDialog()) {
			job.setPrintable(panel);
			try {
				job.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
			
		}
		
		
	}

}

