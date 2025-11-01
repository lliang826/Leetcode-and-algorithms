import java.util.HashMap;
import java.util.Map;

/*
 * HashMap solution below: this solution requires us to iterate through each character in both strings to check if
 * they are anagrams of each other. When we iterate through the first string, we can use a hashmap (fast lookup, O(1) time)
 * to store the key: value pair as char: # of times the char appears in the string. If the char already exists in the 
 * map, increment the counter. Otherwise, add it to the map. 
 * Then, when we iterate through the second string, every time we visit a character, we decrement the value from the
 * map instead of incrementing it. If the character doesn't exist in the map, we can return false because that means
 * it doesn't exist in the first string. We can also add a small if statement to check the 2 string lengths (edge case).
 * Finally, we have a third for loop to iterate through the values in the hashmap. If there are non-zero values, we return
 * false. A positive value means an extra character in string s and a negative value means an extra character in string t.
 * If the third loop terminates, we can conclude that the two strings are anagrams of each other.
 * Time complexity: O(n), where n is the number of characters in the strings
 * Space complexity: O(n), storing all chars into the map
 * 
 * Sorting solution: sort both strings in either ascending or descending order. Use a third for loop to iterate through
 * the indices of both strings at the same time; if any characters don't match, return false.
 * Time complexity: O(n log n), constrained by the sorting
 * Space complexity: O(1) if sorting in place with heapsort (can't be merge sort because that creates arrays)
 * 
 * Array solution: similar to the hasmap solution, but if the question specifies only lowercase or only uppercase letters,
 * we can use an array to store the counters instead of a map. The index of the array represents the ASCII value of the 
 * letter (if the letter is 'c', then 'c' - 'a' = index 2). A second for loop is required to check for non-zero values
 * in the array. This solution is slightly faster than the hashmap solution because arrays are a bit faster and more
 * memory-efficient.
 * Time complexity: O(n), iterating through all chars in the strings
 * Space: O(n), storing all chars into an array
 */

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
