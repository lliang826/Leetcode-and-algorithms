import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences {
    /*
    Hashmap approach. Iterate through the string and store a count of each character in a hashmap. To check if
    the counts are the same, we can use a set; if the set has a count of 1, then they're all the same.
    
    Time: O(n), iterating through all characters in the input string
    Space: O(k), where k is the number of distinct characters
    */
    public boolean areOccurrencesEqual(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        Set<Integer> set = new HashSet<>();
        for (int num : map.values()) {
            set.add(num);
        }

        return set.size() == 1;
    }

    /*
    Same as the solution above, except we don't use a set to check if the counts are the same; this improves
    the space complexity.
    
    Time: O(n) same
    Space: O(k), where k is the number of distinct characters
    */
    public boolean v2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int count = -1;
        for (int num : map.values()) {
            if (count == -1) {
                count = num;
            } else if (count != num) {
                return false;
            }
        }

        return true;
    }

    /*
    Still a hashmap approach, but we use an array to track the characters, which is constant time; most space efficient.
    
    Time: O(n) same
    Space: O(1) no additional data structure (26 lowercase characters, fixed array size)
    */
    public boolean v3(String s) {
        int[] arr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            arr[c - 'a'] += 1;
        }

        char firstLetter = s.charAt(0);
        int freq = arr[firstLetter - 'a'];
        for (int num : arr) {
            if (num != 0 && num != freq) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences solver = new P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { "abacbc", true },
                { "aaabb", false },
                { "a", true },
                { "aabbcc", true },
                { "aabbccdd", true },
                { "abcdefg", true },
                { "aabbccc", false },
                { "zzyyxx", true },
                { "abcabc", true },
                { "abcabcabc", true },
                { "aabbcccddd", false },
                { "aa", true },
                { "ab", true },
                { "aab", false },
                { "aaaabbbbcccc", true },
                { "aaaabbbbccccd", false }
        };

        System.out.println("Running tests for P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences.areOccurrencesEqual\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            boolean actual = solver.areOccurrencesEqual(input);
            
            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, input, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences.v2\n");
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
        System.out.println("Running tests for P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences.v3\n");
        int passV3 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            boolean actual = solver.v3(input);
            
            boolean ok = expected == actual;
            if (ok)
                passV3++;
            System.out.printf("Test %d: input=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, input, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("areOccurrencesEqual: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", passV3, tests.length);
    }
}
