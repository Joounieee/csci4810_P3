import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GraphicsWindow {

	public static void main(String[] args) {
		Frame window = new Frame("Line Scan Conversion");
		
		window.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	            window.dispose();
	         }
	     });
		
		Canvas pic = new MyCanvas();
		window.add("Center", pic);
		
		window.setSize(720, 480);
		window.setVisible(true);
		
		// Repaint picture
		pic.repaint();
		
	} // main method

} // GraphicsWindow class
