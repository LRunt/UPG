import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
class CourseDataItem
{
	public String Label;
	public int Zapsano;
	public int Grade4;
	public int Grade3;
	public int Grade2;
	public int Grade1;

}

class Course
{
	public String Name;
	public ArrayList<CourseDataItem> Statistics;
}

public class BasicDrawing {
	public static String[] predmety = {"UPG", "BZINF", "PPR"};
	private static ArrayList<Course> loadCourses(String dataDir, String masterFile) throws IOException{
		List<String> lines = Files.readAllLines(Path.of(dataDir + masterFile));
		
		ArrayList<Course> courses = new ArrayList<Course>();
		for (String courseName : lines) {
			courses.add(loadCourse(courseName, 
					dataDir + courseName + ".txt"));
		}
		
		return courses;
	}
	
	private static Course loadCourse(String courseName, String fileName) throws IOException{
		List<String> lines = Files.readAllLines(Path.of(fileName));
		//predmety = (String[]) lines.toArray();
		
		Course c = new Course();
		c.Name = courseName;
		c.Statistics = new ArrayList<CourseDataItem>();
		
		int lineIndex = -1;
		for (String line : lines) {
			if (++lineIndex == 0) {
				continue; //skip header
			}
			
			CourseDataItem dt = new CourseDataItem();
			line = line.trim();
			line = line.replace(" ", "");
			String[] parts = line.split("\\|");
			dt.Label = parts[0];
			dt.Zapsano = Integer.parseInt(parts[1]);
			dt.Grade4 = Integer.parseInt(parts[5]);
			dt.Grade3 = Integer.parseInt(parts[4]);
			dt.Grade2 = Integer.parseInt(parts[3]);
			dt.Grade1 = Integer.parseInt(parts[2]);
			c.Statistics.add(dt);
		}
		
		return c;
	}

	private static String DATA_DIR = "data/";
	private static String MASTER_FILE = "index.txt";
	
	public static void main(String[] args) {
		JFrame win = new JFrame();
		win.setTitle("cv9 - Lukas Runt, A20B0226P - R98ZKST");
	
		try {
			ArrayList<Course> data = loadCourses(DATA_DIR, MASTER_FILE);
		
			for (Course course : data) {
				System.out.println(course.Name);
				System.out.println("----------");
				for (CourseDataItem di : course.Statistics) {
					System.out.format("%s\t%d\t%d\t%d\t%d\t%d\n",
							di.Label, di.Zapsano,
							di.Grade4, di.Grade3,
							di.Grade2, di.Grade1);	
				}
			}
			
			ChartPanel panel = new ChartPanel(
					createBarChart(data)
					);
			win.add(panel);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		win.pack();
		
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
	}

	private static JFreeChart createBarChart(ArrayList<Course> data) {
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		int i = 0;
		for (Course course : data) {
			List values = new ArrayList();
			for(CourseDataItem di : course.Statistics) {
				int seZkouskou = di.Grade1 + di.Grade2 + di.Grade3;
				values.add((100.0 * seZkouskou)/di.Zapsano);
			}
			dataset.add(values, predmety[i], predmety[i]);
			i++;
		}
		
		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart("Procento studentu se slozenou zkouskou",  "Predmet", "Procento studentu se zkouskou", (BoxAndWhiskerCategoryDataset) dataset, true);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(0xFF, 0xE4, 0x2B));
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setRangeGridlinesVisible(true);
		//plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		/*CategoryItemRenderer render = plot.getRenderer();
		render.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(
				"{2}%", NumberFormat.getIntegerInstance()));
		render.setDefaultItemLabelFont(new Font("Calibri", Font.PLAIN, 10));
		render.setDefaultItemLabelsVisible(true);*/
		
		
		return chart;
	}
}

