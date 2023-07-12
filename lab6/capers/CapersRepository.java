package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author Lewis Won
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = join(".", ".capers");
    static final File STORY = join(".", ".capers", "story");
    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        File capersFolder = CAPERS_FOLDER;
        capersFolder.mkdir();
        File dogsFolder = Dog.DOG_FOLDER;
        dogsFolder.mkdir();
        File story = STORY;
        try {
            story.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        text = text.concat("\n");
        String existingStory = readContentsAsString(STORY);
        existingStory = existingStory.concat(text);
        writeContents(STORY, existingStory);
        System.out.println(existingStory);

    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog d = new Dog(name, breed, age);
        d.saveDog();
        Dog getDog = Dog.fromFile(name);
        getDog.printString();
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog getDog = Dog.fromFile(name);
        getDog.haveBirthday();
        getDog.toString();
        getDog.saveDog();
    }
}
