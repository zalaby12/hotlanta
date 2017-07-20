public class ParseStatus() {

	private Path status;
	private Path credentials;
	private List<ModifiedFile> modifiedFiles;

	public ParseStatus(String statusFile, String credentialsFile) {
		status = Paths.get(statusFile);
		credentials = Paths.get(credentialsFile);
		modifiedFiles = new ArrayList<>();
	}

	private void readFile() {
		try (InputStream in Files.newInputStream(status);
		BufferedReader statusReader = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			Boolean untracked = false;
			while ((line = statusReader.readLine()) != null) {
				String fileName = null;
				ModificationType modificationType = null;
				ModifiedFile modifiedFile;
				if (line.contains("modified")) {
					fileName = line.split(":")[1];
					modificationType = ModificationType.CHANGED;
				} else if (line.contains("deleted")) {
					fileName = line.split(":")[1];
					modificationType = ModificationType.DELETED;
				} else if (line.contains("Untracked files")) {
					untracked = true;
				} else if (untracked) {
					fileName = line;
					modificationType = ModificationType.ADDED;
				}
				if (fileName != null) {
					modifiedFile = new ModifiedFile(fileName);
					String name;
					String userName;
					try (InputStream in Files.newInputStream(credentials);
						BufferedReader credentialReader = new BufferedReader(new InputStreamReader(in))) {
						name = credentialReader.readLine();
						userName = credentialReader.readLine();
					} catch (IOException e) {
						System.err.println(x);
					}
					modifiedFile.updateEdits(name, userName, System.currentTimeMillis());
					modifiedFiles.add(modifiedFile);
				}
			}
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	public ArrayList<ModifiedFile> getModifiedFiles() {
		return modifiedFiles;
	}
	

}
