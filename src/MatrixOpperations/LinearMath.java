package MatrixOpperations;

import java.util.Random;

public class LinearMath {
	
	public static int[][] generate(int rowLength, int columnLength, int Max){
		
		Random random = new Random();
		
		int[][] matrix = new int[rowLength][columnLength];
		
		for(int i = 0; i < rowLength; i++) {
			for(int j = 0; j < columnLength; j++) {
				matrix[i][j] = random.nextInt(Max);
			}
		}
		
		return matrix;
	}
}
