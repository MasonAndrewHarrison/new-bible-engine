package MatrixOpperations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class read and write matrixs in binary
 * <p>
 * 	The purpose of this class is efficiency in both speed and storage space while
 * 	being easy to use.
 * </p>
 * @author Mason Andrew Harrison
 */

public class MatrixFile {
	
	/**
	 * This save the matrix in binary format
	 * <p>
	 * 	This first save the length, second the width, and after that saves the resest 
	 * 	of the matrix in a one dimensional line.
	 * </p>
	 * @param matrix is what is being saved.
	 * @param fileName is the name of the file.
	 * @throws IOException
	 */
	
	public static void save(int[][] matrix, String fileName) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName))) {
        	
            out.writeInt(matrix.length);				//Stores the length of the matrix
            out.writeInt(matrix[0].length);				//Stores the width of the matrix
            
            //Loops through the matrix
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    out.writeInt(matrix[i][j]);			//Stores each element in the matrix
                }
            }
        }   
    }
	
	/**
	 * This reads an uncompressed matrix stored in binary
	 * <p>
	 * 	This first read metadata which is the length and columns of the matrix then
	 * 	defines the matrix.
	 * </p>
	 * @param fileName is the name of the file being read
	 * @return 
	 * @throws IOException
	 */
	
	public static int[][] read(String fileName) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(fileName))) {
        	
            int rows = in.readInt();					//Reads the length of the matrix
            int cols = in.readInt();					//Reads the width of the matrix
            int[][] matrix = new int[rows][cols];		//Sets up matrix dimensions
            
            //Loops through the matrix
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = in.readInt();		//Adds each element to the matrix
                }
            }
            return matrix;
        }
    }
	
	/**
	 * This take a binary matrix file and compresses it.
	 * <p>
	 * 	This takes out long list of zeros and replaces it with the number of times
	 * 	that the zero was repeated. An example of this is "0, 0, 0, 0" would be 
	 * 	"0, 4" instead. 
	 * </p>
	 * @param fileName is the name of the file that will be compressed
	 * @param compressedFileName is the name of the new compressed file
	 * @throws IOException
	 */
	
	public static void saveCompressedCopy(String fileName, String compressedFileName) throws IOException{
		
		int[][] matrix = read(fileName);
		
		ArrayList<Integer> flattenedMatrix = new ArrayList<>();
		ArrayList<Integer> compressedVector = new ArrayList<>();
		
		flattenedMatrix.add(matrix.length);				//Adds Length to flattened matrix
		flattenedMatrix.add(matrix[0].length);			//Adds Width to flattened matrix
		
		//Loops throw matrix and flattens it
		for(int[] row: matrix) {
			for(int element: row) {
				flattenedMatrix.add(element);			//Adds element from matrix to flattened matrix
			}
		}
		
		//Sets up zero counter
		int zeroCounter = 0;
		
		//Defines the vector
		for(int element: flattenedMatrix) {
			
			//Checks if elements is zero
			if(element == 0) {
				zeroCounter++;							//Counts the number of zeros
			} 
				
			//Checks when the zeros stops repeating
			else if(element != 0 && zeroCounter > 0){
				compressedVector.add(0);				//Step 1: adds special number (0)
				compressedVector.add(zeroCounter);		//Step 2: adds number of zeros that repeated
				compressedVector.add(element);			//Step 3: adds elements that appears after the list of zeros
				zeroCounter = 0;						//Resets zeros counter
			}
				
			//Checks when elements is not zero and not after a zero
			else if(element != 0 && zeroCounter == 0){		
				compressedVector.add(element);			//Adds element
			}
				
			//Checks if something weird is going on
			else {
				System.out.println("Error");
			}
		}
		
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(compressedFileName))) {
			
			out.writeInt(compressedVector.size());		//Saves the length of the file
			
			//Loops through compressed vector and saves it in a binary file
			for(int i: compressedVector) { 
				out.writeInt(i);						//Saves each element
			}
		}
	}
	
	/**
	 * This reads compressed binary matrix files
	 * <p>
	 * 	This take the compressed binary matrix and uncompresses it and save the
	 * 	uncompressed matrix in RAM.
	 * </p>
	 * @param fileNames is the name of the file that will be uncompressed
	 * @return Uncompressed matrix
	 * @throws IOException
	 */
	
	public static int[][] readCompressedMatrix(String fileNames) throws IOException{
		
		ArrayList<Integer> uncompressedVector = new ArrayList<>();
		ArrayList<Integer> compressedVector = new ArrayList<>();
		
		try (DataInputStream in = new DataInputStream(new FileInputStream(fileNames))) { 
			
			int totalLength = in.readInt();				//Reads the metadata about file size
			
			//Repeats for every element in the file
			for(int i = 0; i < totalLength; i++) {
				compressedVector.add(in.readInt());		//Adds element to compressed vector
			}
		}   
		
		//Loops through the compressed vector skipping the metadata
		for(int i = 2; i < compressedVector.size(); i++) {
			
			//Checks if a list of zeros is about to start
			if(compressedVector.get(i) == 0) {
				
				//Repeats for every zero that needs to be added
				for(int j = 0; j < compressedVector.get(i + 1); j++) {
					uncompressedVector.add(0);			//Adds zeros back into the uncompressed vector
				}
				i++;									//Makes loops skip the next number which is the number of zeros that repeat
			}
			
			//Checks if elements is not a list of zeros
			else {
				
				//Adds element to uncompressed vector
				uncompressedVector.add(compressedVector.get(i));
			}
		}
		
		//Sets up uncompressed matrix dimensions
		int[][] uncompressedMatrix = new int[compressedVector.get(0)][compressedVector.get(1)];
		
		int k = 0;
		
		//Loops through uncompressed matrix
		for(int i = 0; i < uncompressedMatrix.length; i++) {
			for(int j = 0; j < uncompressedMatrix[0].length; j++) {
				
				//Defines uncompressed matrix
				uncompressedMatrix[i][j] = uncompressedVector.get(k); 
				k++;
			}
		}
		return uncompressedMatrix;
	}
}