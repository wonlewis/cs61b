package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static gitlet.Utils.*;

public class RepositoryHelpers {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The current commit. */
    public static final File COMMIT_HEAD = join(GITLET_DIR, "commit_head");
    /** Staging folder. */
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    /** Staging folder commit files */
    public static final File STAGING_DIR_MAP = join(GITLET_DIR, "staging", "staging_map");
    /** Folder of committed files */
    public static final File COMMITTED_FILES_DIR = join(GITLET_DIR, "objects");

    protected static void writeToCommitTree(Map<String, Commit> commitTree, Map<String, String> commitTreeShortened, Commit commitHead) {
        String commitSha = Utils.sha1(Utils.serialize(commitHead));
        commitTree.put(commitSha, commitHead);
        String initialCommitShaPart1 = commitSha.substring(0,1);
        String initialCommitShaPart2 = commitSha.substring(2, commitSha.length()-1);
        commitTreeShortened.put(initialCommitShaPart1, initialCommitShaPart2);
        File commitTreeFile = join(GITLET_DIR, "commitTree");
        File commitTreeShortenedFile = join(GITLET_DIR, "commitTreeShortened");
        writeObject(commitTreeFile, (Serializable) commitTree);
        writeObject(commitTreeShortenedFile, (Serializable) commitTreeShortened);
        writeObject(COMMIT_HEAD, commitHead);
    }

    protected static Commit setCommitHead(Commit commitHead, String message) {
        String commitHeadSha = sha1(commitHead);
        commitHead.setMessage(message);
        commitHead.setParentId(commitHead.thisId);
        commitHead.setThisId(commitHeadSha);
        commitHead.setTimestamp(new Date());
        return commitHead;
    }
}
