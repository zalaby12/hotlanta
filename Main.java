import java.util.List;

public class Main {

    private static GitHub githubSimulator;

    private static String CURRENT_DIRECTORY;

    public static void main(String[] args) {
    
        System.out.println("starting analysis");

        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        initSimAndUpdateCurrentDirectory();

        populateGithubSimulator();

        updateGitHubWithNecessaryChanges();

    }

    private static void populateGithubSimulator() {
        githubSimulator.addFiles();
        List<ModifiedFile> anotherUserModifiedFiles = new ParseStatus("anotherGitStatus.txt", "anotherUserGitCredentials.txt").getModifiedFiles();
    }

    private static void initSimAndUpdateCurrentDirectory() {
        CURRENT_DIRECTORY = System.getProperty("user.dir");
        githubSimulator = new GitHub();
    }

    private static void updateGitHubWithNecessaryChanges() {
        List<ModifiedFile> myModifiedFiles = new ParseStatus("gitStatus.txt", "gitCredentials.txt").getModifiedFiles();
        githubSimulator.addModifiedFileList(myModifiedFiles);
    }
    
}
