package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Lewis Won
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */
    static void checkForInitializedGitletWorkingDirectory(String msg) {
        if (GITLET_DIR.exists() && msg != "init") {
            System.out.println("Not in an initialized Gitlet directory.");
        }
    }

    public static void initCommand() {
        if (GITLET_DIR.exists()) {
            errorWithoutExit("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        GITLET_DIR.mkdir();
        Commit initialCommit = new Commit();
        File initialCommitFile = join(GITLET_DIR, "initialCommit");
        writeObject(initialCommitFile, initialCommit);
    }
}
