import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences {
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

    public boolean v2(String s) {
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
        System.out.printf("Overall Summary:\n");
        System.out.printf("areOccurrencesEqual: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
