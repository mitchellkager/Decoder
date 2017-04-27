/* Mitchell Kager
   Decoder */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class Decoder
{
    /* 
     * Reads pattern source from command-line, exiting if none passed.
     * Print all strings from implied dictionary (or dictionary from second
     * argument) which match the source pattern.
     */
    public static void main(String[] args) {
        /*
         * pattern: pattern to match, read from command-line.
         * dictionary: source of dictionary file.
         */
        String pattern = null, dictionary = "/usr/share/dict/words";

        // attempt to read pattern from first argument
        try {
            pattern = args[0];
        } catch (Exception e) {
            // if a pattern was not passed, tell user and quit
            System.out.println("Please provide a pattern");
            System.exit(1);
        }

        // attempt to read dictionary from second argument
        try {
            dictionary = args[1];
        } catch (Exception e) {
            // tell user that the default dictionary will be used
            System.out.println("No dictionary provided, using default");
        }

        // calculate and report all strings from dictionary with same pattern
        System.out.println(matchesPattern(pattern, dictionary));
    }

    /*
     * Returns a Set of string containing all words from the source dictionary
     * which match 'word', the source pattern.
     */
    public static Set<String> matchesPattern(String word,
            String dictionaryFile) {
        /*
         * wordFinder: BufferedReader which scans over dictionary file.
         * foundWords: set of all strings which match the given pattern
         */
        BufferedReader wordFinder = null;
        Set<String> foundWords;

        // attempt to open the dictionary file
        try {
            wordFinder = new BufferedReader(new FileReader(
                        dictionaryFile));
        } catch (FileNotFoundException e) {
            // inform user that dictionary is nonexistent and quit
            System.out.println("Invalid dictionary- quitting");
            System.exit(2);
        }

        // initialize founds as a TreeSet to keep result strings sorted
        foundWords = new TreeSet<>();

        // attempt to read from dictionary file and add all words matching
        // pattern to resultant set
        try {
            // read the first line of the file
            String curWord = wordFinder.readLine();
            // check current word and read next word while lines exist
            while (curWord != null) {
                // convert the word to uppercase
                curWord = curWord.toUpperCase();
                // check if the source pattern and the current word share the
                // same pattern
                if (similar(word, curWord)) {
                    // same pattern, add current word to result
                    foundWords.add(curWord);
                }
                // update the current word to the next word
                curWord = wordFinder.readLine();
            }
        } catch (IOException e) {
            // inform the user of an IOException, quit
            System.out.println("IOException encountered- quitting");
            System.exit(3);
        }

        // return the set of words matching pattern from dictionary
        return foundWords;
    }

    /*
     * Returns true if str1 and str2 adopt the same character pattern - false
     * otherwise.
     */
    public static boolean similar(String str1, String str2) {
        // if length of string is inequal, immediately quit
        if (str1.length() != str2.length()) {
            return false;
        }

        // iterate over the string's character
        for (int i = 0; i < str1.length(); i++) {
            // iterate over all characters in string to the right of the
            // current character (at i)
            for (int j = i+1; j < str1.length(); j++) {
                // returns false if two characters are inequal in one string
                // where they're equal in the other
                if ((str1.charAt(i) == str1.charAt(j)) !=
                     str2.charAt(i) == str2.charAt(j)) {
                    return false;
                }
            }
        }

        // no discrepancies found - return true
        return true;
    }
}
