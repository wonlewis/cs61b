package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;

public class RepositoryHelpers {

    protected static void writeToCommitTree(Map<String, Commit> commitTree, Map<String, List<String>> commitTreeShortened, Commit commitHead) {
        String commitSha = Utils.sha1(Utils.serialize(commitHead));
        commitTree.put(commitSha, commitHead);
        String commitShaPart1 = commitSha.substring(0, 1);
        String commitShaPart2 = commitSha.substring(2, commitSha.length() - 1);
        List<String> arrayCommitShaPart2;
        if (commitTreeShortened.get(commitShaPart1) != null) {
            arrayCommitShaPart2 = commitTreeShortened.get(commitShaPart1);
        }
        else { arrayCommitShaPart2 = new ArrayList<String>(); }
        arrayCommitShaPart2.add(commitShaPart2);
        commitTreeShortened.put(commitShaPart1, arrayCommitShaPart2);
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

    protected static TreeMap<String, List<String>> readCommitTreeShortened() {
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
        System.out.println("commit " + commit.getThisId());
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        String date = sdf.format(commit.getTimestamp());
        System.out.println("Date: " + date);
        System.out.println(commit.getMessage());
        System.out.println();
    }

    protected static void writeToFile(File fileToRead, File fileToWrite) {
        byte[] fileContent = readContents(fileToRead);
        writeContents(fileToWrite, fileContent);
    }

    protected static void writeToFileInWorkingDirectory(File fileToRead, String fileName) {
        File fileToWrite = join(Repository.CWD, fileName);
        byte[] fileContent = readContents(fileToRead);
        writeContents(fileToWrite, fileContent);
    }

    protected static List<String> matchSubstring(List<String> array, String shaShort) {
        Integer length = shaShort.length();
        List<String> matchedArray = new ArrayList<>();
        for (String element: array) {
            String elementShortened = element.substring(0, length - 1);
            if (elementShortened.equals(shaShort)) {
                matchedArray.add(element);
            }
        }
        return matchedArray;
    }

    protected static String getFullCommitId(String shaShort) {
        String commitId;
        if (shaShort.length() < 40) {
            String commitShaPart1 = shaShort.substring(0, 1);
            String commitShaPart2 = shaShort.substring(0, shaShort.length() - 1);
            TreeMap<String, List<String>> commitTreeShortened = RepositoryHelpers.readCommitTreeShortened();
            if (commitTreeShortened.get(commitShaPart1) == null) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }
            List<String> arrayCommitShaPart2 = commitTreeShortened.get(commitShaPart1);
            List<String> matchedArray = RepositoryHelpers.matchSubstring(arrayCommitShaPart2, commitShaPart2);
            if (matchedArray.size() > 1) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }
            commitId = matchedArray.get(0);
        }
        else {
            commitId = shaShort;
        }
        return commitId;
    }

    protected static void checkOutCommand(Commit commitHead, String filename) {
        TreeMap<String, String> commitHeadFiles = commitHead.getFiles();
        if (commitHeadFiles.get(filename) == null) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        String fileSha = commitHeadFiles.get(filename);
        File checkoutFile = join(Repository.COMMITTED_FILES_DIR, fileSha, filename);
        if (!checkoutFile.exists()) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        RepositoryHelpers.writeToFileInWorkingDirectory(checkoutFile, filename);
    }
}
