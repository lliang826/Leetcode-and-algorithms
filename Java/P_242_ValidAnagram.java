import java.util.HashMap;
import java.util.Map;

public class P_242_ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                int value = map.get(c);
                map.replace(c, value + 1);
            } else {
                map.put(c, 1);
            }
        }

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (map.containsKey(c)) {
                int value = map.get(c);
                map.replace(c, value - 1);
            } else {
                return false;
            }
        }

        for (int value : map.values()) {
            if (value != 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isAnagramImproved(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                int value = map.get(c);
                map.replace(c, value + 1);
            } else {
                map.put(c, 1);
            }
        }

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (map.containsKey(c)) {
                int value = map.get(c);
                if (value == 1) {               // Improvement here: instead of leaving counts of 0 in the map,
                    map.remove(c);              // remove from from the hashmap so we can avoid the third for
                }                               // loop and use a simple isEmpty() check instead
                map.replace(c, value - 1);
            } else {
                return false;
            }
        }

        if (!map.isEmpty()) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        P_242_ValidAnagram solver = new P_242_ValidAnagram();

        // Test cases: {s, t, expected}
        String[][] tests = new String[][] {
                { "anagram", "nagaram", "true" },
                { "rat", "car", "false" },
                { "", "", "true" },
                { "a", "ab", "false" },
                { "listen", "silent", "true" },
                { "école", "école", "true" },
                { "aaa", "aa", "false" },
                { "abc", "cba", "true" },
                { "aabbcc", "abcabc", "true" },
        };

        System.out.println("Running tests for P_242_ValidAnagram.isAnagram\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = tests[i][0];
            String t = tests[i][1];
            boolean expected = Boolean.parseBoolean(tests[i][2]);
            boolean actual = solver.isAnagram(s, t);
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: s=\"%s\", t=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, s, t, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("Running tests for P_242_ValidAnagram.isAnagramImproved\n");
        pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = tests[i][0];
            String t = tests[i][1];
            boolean expected = Boolean.parseBoolean(tests[i][2]);
            boolean actual = solver.isAnagramImproved(s, t);
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: s=\"%s\", t=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, s, t, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
