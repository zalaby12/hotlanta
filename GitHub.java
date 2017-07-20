import java.util.ArrayList;
import java.util.List;

public class GitHub {

	private List<ModifiedFile> files;
    private List<ModifiedFile> modifiedFiles;
	
	public GitHub() {
		this.files = new ArrayList<>();
        this.modifiedFiles = new ArrayList<>();
	}

    public GitHub(List<ModifiedFile> modifiedFiles) {
        this.files = new ArrayList<>();
        this.modifiedFiles = modifiedFiles;
    }

    public void setModifiedFileList(List<ModifiedFile> modifiedFiles) {
        this.modifiedFiles = modifiedFiles;
    }
	
	public void addCompleteList(ModifiedFile s) {
		this.files.add(s);
	}
	
	public void removeCompleteList(ModifiedFile s) {
		this.files.remove(s);
	}
	
	public void addModify(ModifiedFile s) {
		this.modifiedFiles.add(s);
        //TODO -> check if files coming in exist already
	}

    //TODO -> take in an arraylist of modified files
	
    public void addFiles() {
        System.out.println("nothing");
    }

	public void removeModify(ModifiedFile s) {
		this.modifiedFiles.remove(s);
	}
	
	public boolean contains(String fileName) {
		for (ModifiedFile s : modifiedFiles) {
			if (s.getName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}

	public ModifiedFile get(String fileName) {
		for (ModifiedFile s : modifiedFiles) {
			if (s.getName().equals(fileName)) {
				return s;
			}
		}
		return null;
	}
	
	public String toString() {
	
		String toReturn = "";
		
		for (ModifiedFile s : modifiedFiles) {
			toReturn += s + "\n";
		}
		
		return toReturn;
	}
	
    //TODO this should take an Editor, I think... and then contact them somehow? not sure. 
	public void notify(String... s) {
		
		System.out.println("Files Changed");
		
		for (String st : s ) {
			System.out.println(st);
		}
	}
	
	
}
