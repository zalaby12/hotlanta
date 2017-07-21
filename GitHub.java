import java.util.*;
import java.io.File;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class GitHub {

	private List<ModifiedFile> files;
    private List<ModifiedFile> modifiedFiles;
	private boolean hasHadCollision;

	public GitHub() {
		this.files = new ArrayList<>();
        this.modifiedFiles = new ArrayList<>();
        hasHadCollision = false;
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
		if (!this.contains(s.getPath())) {
			this.modifiedFiles.add(s);
		} else {
			hasHadCollision = true;
            ModifiedFile matching = this.getMatchingModifiedFileFor(s);
            if(matching != null) {
                matching.updateEdits(s.lastEditorName(), s.lastEditorEmail(), s.lastTimeModified());
            }
            this.notifyEditors(matching.getEdits(), matching);
		}
	}

    public ModifiedFile getMatchingModifiedFileFor(ModifiedFile toCompare) {
        for(ModifiedFile editedFiles : modifiedFiles) {
            if(editedFiles.getPath().equals(toCompare.getPath())) {
                return editedFiles;
            }
        }
        return null;
    }

    public void addModifiedFileList(List<ModifiedFile> modifiedFiles) {
        for(ModifiedFile file : modifiedFiles) {
            this.addModify(file);
        }
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
	public void notifyEditors(List<Editor> editors, ModifiedFile mutuallyModifiedFile) {
		
		System.out.println("Files Changed");
		
		for (Editor editor : editors) {
            this.sendEmail(mutuallyModifiedFile.getPath(), editor.getName(), editor.getEmail(), editor.getTimestamp().toString());
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

	private void sendEmail(String fileName, String editorName, String editorEmail, String timeModified) {
		printNotification(fileName, editorName, editorEmail);
		String userName = "git.smart.ibm@gmail.com";
		String password = "THESAFESTPASSWORD";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.username", userName);
		properties.setProperty("mail.password", password);
		properties.setProperty("mail.defaultEncoding", "UTF-8");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.required", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(editorEmail));
			message.setSubject("Warning: possible merge conflict in" + fileName);
			message.setText("Hello, " + editorName + ". You are currently working on a file (" + fileName.trim() +") that is being edited by another developer.");
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException x) {
			x.printStackTrace();
		}
	}

	public boolean hasHadFileOverlap() {
		return this.hasHadCollision;
	}

	private void printNotification(String fileName, String editorName, String editorEmail) {
		System.out.println("notifying " + editorName + " that " + fileName + " is also being modified by another user. " +
				"Sending email to " + editorEmail);
	}
	
	
}
