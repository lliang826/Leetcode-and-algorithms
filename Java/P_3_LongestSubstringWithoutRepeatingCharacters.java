import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P_3_LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int currLength = 0;
        int lastIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c) && map.get(c) > lastIndex) {
                currLength = i - map.get(c);
                lastIndex = map.get(c);
            } else {
                currLength++;
            }

            map.put(c, i);
            maxLength = Math.max(maxLength, currLength);
        }

        return maxLength;
    }
    
    public int v2(String s) {
        int left = 0;
        int length = 0;
        Set<Character> set = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }

            set.add(c);
            length = Math.max(length, right - left + 1);
        }

        return length;
    }

    public int v3(String s) {
        int left = 0;
        int length = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
                map.remove(c);
            }

            map.put(c, right);
            length = Math.max(length, right - left + 1);
        }

        return length;
    }
    
    public static void main(String[] args) {
        P_3_LongestSubstringWithoutRepeatingCharacters solver = new P_3_LongestSubstringWithoutRepeatingCharacters();

        Object[][] tests = new Object[][] {
                { "abcabcbb", 3 },
                { "bbbbb", 1 },
                { "pwwkew", 3 },
                { "", 0 },
                { "a", 1 },
                { "au", 2 },
                { "dvdf", 3 },
                { "abcdefghijklmnopqrstuvwxyz", 26 },
                { "aab", 2 },
                { "abba", 2 },
                { "tmmzuxt", 5 },
                { "abcabcdefabc", 6 }
        };

        System.out.println("Running tests for P_3_LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.lengthOfLongestSubstring(input);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_3_LongestSubstringWithoutRepeatingCharacters.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v2(input);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_3_LongestSubstringWithoutRepeatingCharacters.v3\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v3(input);

            boolean ok = expected == actual;
            if (ok)
                pass3++;
            System.out.printf("Test %d: input=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("lengthOfLongestSubstring: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", pass3, tests.length);
    }
}