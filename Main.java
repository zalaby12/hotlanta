package hotlanta;

public class Main {

    private static GitHub githubSimulator;

    private static String CURRENT_DIRECTORY;

    public static void main(String[] args) {
    
        System.out.println("starting analysis");

        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        CURRENT_DIRECTORY = System.getProperty("user.dir");

        githubSimulator = new GitHub();
    
        populateGithubSimulator();

    }

    private static void populateGithubSimulator(String arg) {
//        githubSimulator.add(new ModifiedFile
    }

    private static void populateGithubSimulator() {
        populateGithubSimulator(null);
    }

}
