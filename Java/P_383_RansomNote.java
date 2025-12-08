public class P_383_RansomNote {
    /*
    Very easy hash map approach using a character frequency array. All we have to do is
    check if the characters in ransomNote are also in magazine. If both strings only 
    contain lowercase alphabetical characters, we can use a frequency array of size 26
    to represent the 26 letters in the alphabet.
    
    First we iterate through all the characters in magazine, then we iterate through the
    characters in ransomNote. If there are any negative values in the frequency array,
    it means ransomNote has additional characters so we return false.
    
    Time: O(n + m), where n is the number of characters in magazine, and m is the number
    of characters in ransomNote
    Space: O(1), frequency array size is fixed at 26
    */
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] arr = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            arr[c - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            arr[c - 'a']--;
        }

        for (int i : arr) {
            if (i < 0) {
                return false;
            }
        }

        return true;
    }
    
    /*
    Improved version of the method above. Instead of a third loop where we iterate through
    the character frequency array and check for negative values, we can instead perform the
    check inside the second loop, right after we decrement the values for the ransomNote
    characters.
    
    Same time and space complexities.
    */
    public boolean v2(String ransomNote, String magazine) {
        int[] arr = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            arr[c - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            arr[c - 'a']--;
            if (arr[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args) {
        P_383_RansomNote solver = new P_383_RansomNote();

        Object[][] tests = new Object[][] {
                { "a", "b", false },
                { "aa", "ab", false },
                { "aa", "aab", true },
                { "abc", "aabbcc", true },
                { "aab", "baa", true },
                { "fihjjjjei", "hjibagacbhadfaefdjaeaebgi", false },
                { "bg", "efjbdfbdgfjhhaiigfhbaejahgfbbgbjagbddfgdiaigdadhcfcj", true },
                { "a", "a", true },
                { "ab", "a", false },
                { "aabbcc", "abc", false },
                { "", "abc", true },
                { "abc", "cba", true }
        };

        System.out.println("Running tests for P_383_RansomNote.canConstruct\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String ransomNote = (String) tests[i][0];
            String magazine = (String) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.canConstruct(ransomNote, magazine);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: ransomNote=\"%s\", magazine=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, ransomNote, magazine, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_383_RansomNote.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String ransomNote = (String) tests[i][0];
            String magazine = (String) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.v2(ransomNote, magazine);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: ransomNote=\"%s\", magazine=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, ransomNote, magazine, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("canConstruct: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
