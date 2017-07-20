package hotlanta;

import java.util.ArrayList;

public class GitHub {

	ArrayList<String> files;
	
	public GitHub() {
		files = new ArrayList<>();
	}
	
	public void add(String s) {
		files.add(s);
	}
	
	public void remove(String s) {
		files.remove(s);
	}
	
	public String toString() {
	
		String toReturn = "";
		
		for (String s : files) {
			toReturn += s + "\n";
		}
		
		return toReturn;
	}
	
	public void notify(String... s) {
		
		System.out.println("Files Changed");
		
		for (String st : s ) {
			System.out.println(st);
		}
	}
	
	
}
