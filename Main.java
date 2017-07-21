import java.io.File;
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

        printFinalOutcome();

    }

    private static void initSimAndUpdateCurrentDirectory() {
        CURRENT_DIRECTORY = System.getProperty("user.dir");
        githubSimulator = new GitHub();
    }

    private static void populateGithubSimulator() {
        githubSimulator.populate(new File(System.getProperty("user.dir")));
        List<ModifiedFile> anotherUserModifiedFiles = new ParseStatus("anotherGitStatus.txt", "anotherUserGitCredentials.txt").getModifiedFiles();
        githubSimulator.addModifiedFileList(anotherUserModifiedFiles);
    }

    private static void updateGitHubWithNecessaryChanges() {
        List<ModifiedFile> myModifiedFiles = new ParseStatus("gitStatus.txt", "gitCredentials.txt").getModifiedFiles();
        githubSimulator.addModifiedFileList(myModifiedFiles);
    }

    private static void printFinalOutcome() {
        System.out.println("**************************************************");
        if(githubSimulator.hasHadFileOverlap()) {
            System.out.println("files would have potential merge conflict");
        } else {
            System.out.println("no files should have merge conflicts");
        }
        System.out.println("**************************************************");
    }
    
}
