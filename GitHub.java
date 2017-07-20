package hotlanta;

import java.util.ArrayList;

public class GitHub {

	ArrayList<ModifiedFile> files;
    ArrayList<ModifiedFile> modifiedFiles;
	
	public GitHub() {
		files = new ArrayList<>();
        modifiedFiles = new ArrayList<>();
	}
	
	public void addCompleteList(ModifiedFile s) {
		files.add(s);
	}
	
	public void removeCompleteList(ModifiedFile s) {
		files.remove(s);
	}
	
	public void addModify(ModifiedFile s) {
		modifiedFiles.add(s);
	}
	
	public void removeModify(ModifiedFile s) {
		modifiedFiles.remove(s);
	}
	
	public boolean contains(ModifiedFile s) {
		return modifiedFiles.contains(s);
	}
	
	public String toString() {
	
		String toReturn = "";
		
		for (ModifiedFile s : modifiedFiles) {
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
