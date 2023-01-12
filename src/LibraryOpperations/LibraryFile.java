package LibraryOpperations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryFile {

	public static ArrayList<String> read(String fileName) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader("library.txt"));
		ArrayList<String> library = new ArrayList<>();
		
		for(int i = 0; i < 12677; i++) {
			library.add(reader.readLine());
		}
		
		return library;
		
	}
	
	public static int findIndex(ArrayList<String> library, String searchWord) {
		
		for(int i = 0; i < library.size(); i++) {
			if(library.get(i).equals(searchWord)) {
				return i;
			}
		}
		return 0; 
	}
}
