import java.util.HashSet;
import java.util.Set;

public class P_1832_CheckIfSentenceIsPangram {
    /*
     * Array approach. Use an array of size 26 to track occurrences of each letter. For each character in the sentence,
     * increment the count at the corresponding index (c - 'a'). Then iterate through the array to check if any letter
     * has a count of 0, which means it's missing from the sentence.
     * 
     * Time: O(n), iterating through all characters in the sentence
     * Space: O(1), fixed array of size 26
     */
    public boolean checkIfPangram(String sentence) {
        int[] arr = new int[26];
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            arr[c - 'a']++;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * HashSet approach. Add each unique character to a set. If the set size equals 26, then all letters of the 
     * alphabet are present in the sentence.
     * 
     * Time: O(n), iterating through all characters in the sentence
     * Space: O(1), the set can contain at most 26 characters
     */
    public boolean v2(String sentence) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < sentence.length(); i++) {
            set.add(sentence.charAt(i));
        }
        return set.size() == 26;
    }

    public static void main(String[] args) {
        P_1832_CheckIfSentenceIsPangram solver = new P_1832_CheckIfSentenceIsPangram();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { "thequickbrownfoxjumpsoverthelazydog", true },
                { "leetcode", false },
                { "abcdefghijklmnopqrstuvwxyz", true },
                { "thequickbrownfoxjumpsoverthelazydo", false },
                { "a", false },
                { "abcdefghijklmnopqrstuvwxy", false },
                { "zyxwvutsrqponmlkjihgfedcba", true },
                { "thequickbrownfoxjumpsoverthelazydogandthensome", true },
                { "packmyboxwithfivedozenliquorjugs", true },
                { "thefiveboxingwizardsjumpquickly", true },
                { "howvexinglyquickdaftzebrasjump", true },
                { "sphinx", false },
                { "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz", true },
                { "bcdefghijklmnopqrstuvwxyz", false },
                { "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", true }
        };

        System.out.println("Running tests for P_1832_CheckIfSentenceIsPangram.checkIfPangram\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            boolean actual = solver.checkIfPangram(input);
            
            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, input, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1832_CheckIfSentenceIsPangram.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            boolean actual = solver.v2(input);
            
            boolean ok = expected == actual;
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, input, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("checkIfPangram: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}