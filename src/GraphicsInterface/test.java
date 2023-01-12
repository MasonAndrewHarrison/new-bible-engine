package GraphicsInterface;

import java.io.IOException;
import java.util.ArrayList;
import LibraryOpperations.LibraryFile;
import MatrixOpperations.MatrixFile;

public class test {

	public static void main(String[] args) throws IOException {
		
		int[][] matrix = MatrixFile.readCompressedMatrix("Compressed Frequency Matrix");
		
		System.out.println(matrix[0].length);
		
		ArrayList<String> library = LibraryFile.read("library.txt");
		
		int searchIndex1 = LibraryFile.findIndex(library, "god");
		int searchIndex2 = LibraryFile.findIndex(library, "word");
		
		for(int i = 0; i < matrix.length; i++) {
			System.out.println(matrix[i][searchIndex1] + matrix[i][searchIndex2]);
		}
	} 
}
