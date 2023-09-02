package gitlet;

import java.util.Arrays;
import java.util.List;

import static gitlet.Utils.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Lewis Won
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            errorWithExit("Please enter a command.");
        }
        String firstArg = args[0];
        Utils.checkForValidCommand(firstArg);
        Repository.checkForInitializedGitletWorkingDirectory(firstArg);
        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1);
                Repository.initCommand();
                break;
            case "add":
                validateNumArgs(args, 2);
                String fileName = args[1];
                Repository.add(fileName);
                break;
            case "commit":
                validateNumArgsForCommit(args);
                String message = args[1];
                Repository.commit(message);
                break;
            case "rm":
                // TODO: handle the `rm` command
                break;
            case "log":
                validateNumArgs(args, 1);
                Repository.log();
                break;
            case "global-log":
                // TODO: handle the `global-log` command
                break;
            case "find":
                // TODO: handle the `find` command
                break;
            case "status":
                // TODO: handle the `status` command
                break;
            case "checkout":
                // TODO: handle the `checkout` command
                break;
            case "branch":
                // TODO: handle the `branch` command
                break;
            case "rm-branch":
                // TODO: handle the `rm-branch` command
                break;
            case "reset":
                // TODO: handle the `reset` command
                break;
            case "merge":
                // TODO: handle the `merge` command
                break;
        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Incorrect operands."));
        }
    }

    public static void validateNumArgsForCommit(String[] args) {
        if (args.length == 1) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        if (args.length > 2) {
            throw new RuntimeException(
                    String.format("Incorrect operands."));
        }
    }
}
