import java.util.ArrayList;
import java.util.List;
import java.io.File;

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
		if (!contains(s.getPath()) {
			this.modifiedFiles.add(s);
		} else {

		}
		
        //TODO -> check if files coming in exist already
	}

    //TODO -> take in an arraylist of modified files
    
    public void addModifiedFileList(List<ModifiedFile> modifiedFiles) {
        for(ModifiedFile file : modifiedFiles) {
            this.modifiedFiles.add(file);
        }
    }
	
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
	public void notifyEditors(ArrayList<Editor> editors) {
		
		System.out.println("Files Changed");
		
		for (String st : s ) {
			System.out.println(st);
		}
	}

	public void populate(File file) {
		if (!file.isDirectory()) {
			ModifiedFile newFile = new ModifiedFile(file.getPath()); 
			addModify(newFile);
		} else {
			File[] children = file.listFiles();
			for (File f : children) {
				populate(f);
			}
		}
	}

	private void sendEmail(ModifiedFile file) {
		String fileName = file.getPath();
		String editorName = file.lastEditorName();
		String editorEmail = file.lastEditorEmail();
		String timeModified = file.lastTimeModified();
		String fromName = "gitsmart@ibm.com";
		String host = "localhost";
		Properties properties = System.properties();
		properties.setProperty("mail.smpt.host", host);
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromName));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(editorEmail));
			message.setSubject("Warning: possible merge conflict in" + fileName);
			message.setText("Hello, " + editorName + ". You are currently working on a file that is being edited by another developer.");
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException x) {
			x.printStackTrace();
		}
	}
	
	
}
