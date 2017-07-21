import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;

public class ParseStatus {

	private Path status;
	private Path credentials;
	private List<ModifiedFile> modifiedFiles;

	public ParseStatus(String statusFile, String credentialsFile) {
		status = Paths.get(statusFile);
		credentials = Paths.get(credentialsFile);
		modifiedFiles = new ArrayList<>();
		readFile();
	}

	private void readFile() {
		try {
            InputStream in  = Files.newInputStream(status);
	    	BufferedReader statusReader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			Boolean untracked = false;
			while ((line = statusReader.readLine()) != null) {
				String fileName = null;
				ModifiedType modificationType;
				ModifiedFile modifiedFile;
				if (line.contains("modified")) {
					fileName = line.split(":")[1];
					modificationType = ModifiedType.CHANGED;
				} else if (line.contains("deleted")) {
					fileName = line.split(":")[1];
					modificationType = ModifiedType.DELETED;
				} else if (line.contains("Untracked files")) {
					untracked = true;
				} else if (untracked) {
					fileName = line;
					modificationType = ModifiedType.ADDED;
				}
				if (fileName != null) {
					modifiedFile = new ModifiedFile(fileName);
					String name = "";
					String userName = "";
					try {
                        InputStream inCredentials  = Files.newInputStream(credentials);
						BufferedReader credentialReader = new BufferedReader(new InputStreamReader(inCredentials));
						name = credentialReader.readLine();
						userName = credentialReader.readLine();
					} catch (IOException e) {
                        e.printStackTrace();
					}
					modifiedFile.updateEdits(name, userName, new Date());
					modifiedFiles.add(modifiedFile);
				}
			}
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	public List<ModifiedFile> getModifiedFiles() {
		return modifiedFiles;
	}
	

}
