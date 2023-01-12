package MatrixOpperations;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Display {
	
	public static void text(int[][] matrix) {
		for(int[] row: matrix) {
			System.out.print("| ");
			for(int element: row) {
				System.out.print(element + ", ");
			}
			System.out.println("|"); 
		}
	} 
	
	public static void image(int[][] matrix) { 
		
		// Convert the matrix to a BufferedImage
	    BufferedImage image = new BufferedImage(matrix[0].length, matrix.length, BufferedImage.TYPE_BYTE_GRAY);
	    for (int y = 0; y < matrix.length; y++) {
	      for (int x = 0; x < matrix[0].length; x++) {
	        image.setRGB(x, y, matrix[y][x] << 16 | matrix[y][x] << 8 | matrix[y][x]);
	      }
	    }

	    // Display the image in a window
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(new JLabel(new ImageIcon(image)));
	    frame.pack();
	    frame.setVisible(true);
	}
}
