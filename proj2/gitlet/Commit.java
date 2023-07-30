package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Lewis Won
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private String parentId;
    public String thisId;
    private String secondParentId;
    private Date timestamp;
    private Map files;

    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        this.parentId = "0000000000000000000000000000000000000000";
        this.files = new TreeMap<String, String>();
    }

    public boolean hasFile(String fileName, String sha1) {
        if (this.files.containsKey(fileName)) {
            return sha1.equals(this.files.get(fileName));
        }
        return false;
    }
    public void setFile(String filename, String sha1) {
        this.files.put(filename, sha1);
    }

    public void setParentId(String sha1) {
        this.parentId = sha1;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setThisId(String sha1) {
        this.thisId = sha1;
    }

    /* TODO: fill in the rest of this class. */
}
