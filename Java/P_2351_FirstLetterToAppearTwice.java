import java.util.HashSet;
import java.util.Set;

public class P_2351_FirstLetterToAppearTwice {
    /*
     * HashSet approach. Iterate through the string and add each character to a set. If the character is already
     * in the set, return it as it's the first letter to appear twice.
     * After the for loop, we have to return a character, but this line will never be reached; the question 
     * guarantees that there is always a repeating character in the input string.
     * 
     * Time: O(n), iterating through all characters in the input string
     * Space: O(1), the set can contain at most 26 characters (lowercase English letters)
     */
    public char repeatedCharacter(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                return c;
            } else {
                set.add(c);
            }
        }
        return 'a';
    }

    public static void main(String[] args) {
        P_2351_FirstLetterToAppearTwice solver = new P_2351_FirstLetterToAppearTwice();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { "abccbaacz", 'c' },
                { "abcdd", 'd' },
                { "aa", 'a' },
                { "abcdefghijklmnopqrstuvwxyza", 'a' },
                { "aabbcc", 'a' },
                { "abba", 'b' },
                { "noonbook", 'o' },
                { "programming", 'r' },
                { "leetcode", 'e' },
                { "aabbccdd", 'a' },
                { "abcabc", 'a' },
                { "xyzzyx", 'z' },
                { "hello", 'l' },
                { "mississippi", 's' },
                { "zz", 'z' }
        };

        System.out.println("Running tests for P_2351_FirstLetterToAppearTwice.repeatedCharacter\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            char expected = (char) tests[i][1];
            char actual = solver.repeatedCharacter(input);
            
            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=\"%s\" => expected='%c', actual='%c' => %s\n",
                    i + 1, input, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("repeatedCharacter: %d/%d tests passed\n", pass, tests.length);
    }
}
