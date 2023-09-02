package gitlet;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
    /** The current commit. */
    public static final File COMMIT_HEAD = join(GITLET_DIR, "commit_head");
    /** Staging folder. */
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    /** Staging folder commit files */
    public static final File STAGING_DIR_MAP = join(GITLET_DIR, "staging", "staging_map");
    /** Folder of committed files */
    public static final File COMMITTED_FILES_DIR = join(GITLET_DIR, "objects");

    /* TODO: fill in the rest of this class. */
    static void checkForInitializedGitletWorkingDirectory(String msg) {
        if (!GITLET_DIR.exists() && !msg.equals("init")) {
            System.out.println(msg);
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

        //Sha for initial commit
        //Includes both the full and shortened sha
        String initialCommitSha = Utils.sha1(Utils.serialize(initialCommit));
        initialCommit.setThisId(initialCommitSha);
        Map<String, Commit> commitTree = new TreeMap<>();
        Map<String, String> commitTreeShortened = new TreeMap<>();
        Repository.writeToCommitTree(commitTree, commitTreeShortened, initialCommit);
    }

    private static void writeToCommitTree(Map<String, Commit> commitTree, Map<String, String> commitTreeShortened, Commit commitHead) {
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

    public static void add(String fileName) {
        File toAddFile = join(GITLET_DIR, fileName);
        if (!toAddFile.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        byte[] toAddFileByteArray = readContents(toAddFile);
        String toAddFileSha = sha1(toAddFileByteArray);
        Commit commitHead = readObject(COMMIT_HEAD, Commit.class);
        File fileInStaging = join(STAGING_DIR, fileName, toAddFileSha, fileName);
        Map<String, String> toAddFileMap;
        if (STAGING_DIR_MAP.exists()) {
            toAddFileMap = readObject(STAGING_DIR_MAP, TreeMap.class);
        }
        else {
            toAddFileMap = new TreeMap<>();
        }
        if (commitHead.hasFile(fileName, toAddFileSha)) {
            Utils.restrictedDelete(fileInStaging);
        } else if (!toAddFileMap.containsKey(fileName) || toAddFileMap.get(fileName) == null) {
            byte[] toAddFileRead = readContents(toAddFile);
            writeContents(fileInStaging, toAddFileRead);
            toAddFileMap.put(fileName, toAddFileSha);
            writeObject(STAGING_DIR_MAP, (Serializable) toAddFileMap);
        }
    }

    public static void commit(String message) {
        /** read toAddFileMap */
        /** if toAddFileMap is empty, print "No changes added to the commit."  */
        Map<String, String> toAddFileMap = readObject(STAGING_DIR_MAP, TreeMap.class);
        if (toAddFileMap.size() == 0 || !STAGING_DIR_MAP.exists()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        /** read commitHead */
        Commit commitHead = readObject(COMMIT_HEAD, Commit.class);
        /** update commitHead */
        for (Map.Entry<String, String> set :
        toAddFileMap.entrySet()) {
            commitHead.setFile(set.getKey(), set.getValue());
            /** copy staged file to gitlet object folder  */
            File toCopyFile = join(STAGING_DIR, set.getKey(), set.getKey(), set.getKey());
            byte[] toCopyContent = readContents(toCopyFile);
            File toCommitFile = join(COMMITTED_FILES_DIR, set.getKey(), set.getValue(), set.getKey());
            writeContents(toCommitFile, toCopyContent);
        }
        commitHead = Repository.setCommitHead(commitHead, message);
        File commitTreeFile = join(GITLET_DIR, "commitTree");
        File commitTreeShortenedFile = join(GITLET_DIR, "commitTreeShortened");
        Map<String, Commit> commitTree = readObject(commitTreeFile, TreeMap.class);
        Map<String, String> commitTreeShortened = readObject(commitTreeShortenedFile, TreeMap.class);
        Repository.writeToCommitTree(commitTree, commitTreeShortened, commitHead);

        /** clear staging folder */
        Utils.restrictedDelete(STAGING_DIR_MAP);
        try {
            Files.walk(STAGING_DIR.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Commit setCommitHead(Commit commitHead, String message) {
        String commitHeadSha = sha1(commitHead);
        commitHead.setMessage(message);
        commitHead.setParentId(commitHead.thisId);
        commitHead.setThisId(commitHeadSha);
        commitHead.setTimestamp(new Date());
        return commitHead;
    }


}
