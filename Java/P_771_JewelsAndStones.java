import java.util.HashSet;
import java.util.Set;

public class P_771_JewelsAndStones {
    /*
    Very easy hash set approach. Iterate through jewels and store each character into a hash
    set. Then, iterate through stones and check if the hash set contains the character. If 
    it does, increment the counter.
    
    Time: O(n + m), where n is the number of characters in jewels, and m is the number of 
    characters in stones
    Space: O(n), storing all the characters in jewels into a hash set
    */
    public int numJewelsInStones(String jewels, String stones) {
        int count = 0;
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < jewels.length(); i++) {
            set.add(jewels.charAt(i));
        }

        for (int i = 0; i < stones.length(); i++) {
            char c = stones.charAt(i);
            if (set.contains(c)) {
                count++;
            }
        }

        return count;
    }

    /*
    Same approach as above, but we use frequency arrays to store the characters. Since jewels
    contains both uppercase and lowercase characters, we need 2 arrays.
    
    Same time complexity as above, but slightly faster since arrays are faster than maps (maps
    have a hashcode to calculate).
    Space complexity: arrays are fixed at size 26, so O(1)
    */
    public int v2(String jewels, String stones) {
        int[] lowercase = new int[26];
        int[] uppercase = new int[26];

        for (int i = 0; i < jewels.length(); i++) {
            char c = jewels.charAt(i);
            if (Character.isUpperCase(c)) {
                uppercase[c - 'A']++;
            } else {
                lowercase[c - 'a']++;
            }
        }

        int count = 0;
        for (int i = 0; i < stones.length(); i++) {
            char c = stones.charAt(i);
            if (Character.isUpperCase(c) && uppercase[c - 'A'] == 1) {
                count++;
            } else if (Character.isLowerCase(c) && lowercase[c - 'a'] == 1) {
                count++;
            }
        }

        return count;
    }
    
    public static void main(String[] args) {
        P_771_JewelsAndStones solver = new P_771_JewelsAndStones();

        Object[][] tests = new Object[][] {
                { "aA", "aAAbbbb", 3 },
                { "z", "ZZ", 0 },
                { "abc", "aabbcc", 6 },
                { "ABC", "aabbcc", 0 },
                { "a", "a", 1 },
                { "a", "b", 0 },
                { "aB", "aABbBa", 4 },
                { "zZ", "zzZZzZzZ", 8 },
                { "abcdefghijklmnopqrstuvwxyz", "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz", 52 },
                { "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz", 0 }
        };

        System.out.println("Running tests for P_771_JewelsAndStones.numJewelsInStones\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String jewels = (String) tests[i][0];
            String stones = (String) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.numJewelsInStones(jewels, stones);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: jewels=\"%s\", stones=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, jewels, stones, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_771_JewelsAndStones.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String jewels = (String) tests[i][0];
            String stones = (String) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.v2(jewels, stones);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: jewels=\"%s\", stones=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, jewels, stones, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("numJewelsInStones: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
