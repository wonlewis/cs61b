package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Utils.*;

public class RepositoryHelpers {

    protected static void writeToCommitTree(Map<String, Commit> commitTree, Map<String, String> commitTreeShortened, Commit commitHead) {
        String commitSha = Utils.sha1(Utils.serialize(commitHead));
        commitTree.put(commitSha, commitHead);
        String initialCommitShaPart1 = commitSha.substring(0,1);
        String initialCommitShaPart2 = commitSha.substring(2, commitSha.length()-1);
        commitTreeShortened.put(initialCommitShaPart1, initialCommitShaPart2);
        File commitTreeFile = join(Repository.GITLET_DIR, "commitTree");
        File commitTreeShortenedFile = join(Repository.GITLET_DIR, "commitTreeShortened");
        writeObject(commitTreeFile, (Serializable) commitTree);
        writeObject(commitTreeShortenedFile, (Serializable) commitTreeShortened);
        writeObject(Repository.COMMIT_HEAD, commitHead);
    }

    protected static Commit setCommitHead(Commit commitHead, String message) {
        String commitHeadSha = sha1(commitHead);
        commitHead.setMessage(message);
        commitHead.setParentId(commitHead.thisId);
        commitHead.setThisId(commitHeadSha);
        commitHead.setTimestamp(new Date());
        return commitHead;
    }

    protected static TreeMap<String, Commit> readCommitTree() {
        File commitTreeFile = join(Repository.GITLET_DIR, "commitTree");
        return readObject(commitTreeFile, TreeMap.class);
    }

    protected static TreeMap<String, String> readCommitTreeShortened() {
        File commitTreeShortenedFile = join(Repository.GITLET_DIR, "commitTreeShortened");
        return readObject(commitTreeShortenedFile, TreeMap.class);
    }

    protected static Commit getCommitHead() {
        TreeMap<String, Commit> commitTree = readCommitTree();
        String commitHead = commitTree.lastKey();
        return commitTree.get(commitHead);
    }

    protected static Commit getCommitHead(String id) {
        TreeMap<String, Commit> commitTree = readCommitTree();
        return commitTree.get(id);
    }

    protected static void printCommitMessage(Commit commit) {
        System.out.println("===");
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        String date = sdf.format(commit.getTimestamp());
        System.out.println("Date: " + date);
        System.out.println(commit.getMessage());
        System.out.println();
    }
}
