import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class Decoder
{
    public static void main(String[] args)
    {
        String pattern = null, dictionary = "/usr/share/dict/words";

        // get pattern from command-line arguments
        try {
            pattern = args[0];
        } catch (Exception e) {
            System.out.println("Please provide a pattern");
            System.exit(1);
        }

        // attempt to find a dictionary, using default if not provided
        try {
            dictionary = args[1];
        } catch (Exception e) {
            System.out.println("No dictionary provided, using default");
        }

        System.out.println(matchesPattern(pattern, dictionary));
    }

    public static Set<String> matchesPattern(String word, String dictionaryFile)
    {
        BufferedReader wordFinder = null;
        try {
             wordFinder = new BufferedReader(new FileReader(
                                                dictionaryFile));
        } catch (FileNotFoundException e) {
                System.out.println("Invalid dictionary- quitting");
                System.exit(2);
        }

        Set<String> foundWords = new TreeSet<>();

        try {
            String curWord = wordFinder.readLine();
            while (curWord != null)
            {
                curWord = curWord.toUpperCase();
                if (similar(word, curWord))
                {
                    foundWords.add(curWord);
                }
                curWord = wordFinder.readLine();
            }
        } catch (IOException e) {
            System.out.println("IOException encountered- quitting");
            System.exit(3);
        }

        return foundWords;
    }

    public static boolean similar(String str1, String str2)
    {
        if (str1.length() != str2.length())
        {
            return false;
        }

        for (int i = 0; i < str1.length(); i++)
        {
            for (int j = i+1; j < str1.length(); j++)
            {
                if (str1.charAt(i) == str1.charAt(j) && str2.charAt(i) != str2.charAt(j) ||
                    str2.charAt(i) == str2.charAt(j) && str1.charAt(i) != str1.charAt(j))
                {
                    return false;
                }
            }
        }

        return true;
    }
}
