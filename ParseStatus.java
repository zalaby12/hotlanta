public static void main(String[] args) {
	Path status = new Path(".../currentGitStatus.txt"); //file path?
	try (InputStream in Files.newInputStream(status);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
		String line = null;
		Boolean untracked = false;
		while ((line = reader.readLine()) != null) {
			String fileName = null;
			ModificationType modificationType = null;
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
		}
	} catch (IOException x) {
		System.err.println(x);
	}

}

public enum ModificationType {
	CHANGED, DELETED, ADDED
}