public static void main(String[] args) {
	GitHub gitHub = new GitHub();
	Path status = Paths.get("gitStatus.txt"); 
	try (InputStream in Files.newInputStream(status);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
		String line = null;
		Boolean untracked = false;
		while ((line = reader.readLine()) != null) {
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
				if (gitHub.contains(fileName)) {
					modifiedFile = gitHub.get(fileName);
				} else {
					modifiedFile = new ModifiedFile(fileName);
				}
				Path credentials = Paths.get("gitCredentials.txt");
				String name;
				String userName;
				try (InputStream in Files.newInputStream(credentials);
					BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
					name = reader.readLine();
					userName = reader.readLine();
				} catch (IOException e) {
					System.err.println(x);
				}
				modifiedFile.updateEdits(name, userName, System.currentTimeMillis());
			}
		}
	} catch (IOException x) {
		System.err.println(x);
	}

}
