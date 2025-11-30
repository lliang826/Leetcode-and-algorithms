import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_49_GroupAnagrams {
    /*
    Hash map approach. To determine if strings are anagrams, we need to determine the frequencies of each character.
    We can do this using the fixed array of size 26 to save memory, but the tricky part is figuring out how to group
    the anagrams together. 
    
    We can achieve this by transforming that array of size 26 into a string; if two strings are anagrams of each other,
    they have the same size 26 array. Strings are also immutable, so this is our hash map key. The hash map value is
    simply an ArrayList containing the original strings.
    
    Another tricky part is when we transform the size 26 array to a string, we also need to need to add a delimiter.
    Otherwise, double-digit frequencies can produce incorrect strings.
    E.g. a: 10, b: 2 => 102 (does this mean 1 'a', 0 'b', 2 'c' or does it mean something else?)
    
    For each string in the input array, there are 3 steps:
    1. Build the size 26 frequency array
    2. Convert it to a string with delimiters (use StringBuilder and toString() for Java)
    3. Add/update the <key, value> pair in the hash map as <transformedString, listOfOriginalStrings>
    
    Time: O(n * m), where n is the size of the input array and m is the number of characters in the longest string
    Space: O(n), no anagrams so the hash map holds all strings as keys
    */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            int[] arr = new int[26];
            for (char c : s.toCharArray()) {
                arr[c - 'a']++;
            }

            StringBuilder sb = new StringBuilder();
            for (int i : arr) {
                sb.append(i).append('#');
            }
            String key = sb.toString();

            if (map.containsKey(key)) {
                map.get(key).add(s);
            } else {
                List<String> list = new ArrayList<>();
                list.add(s);
                map.put(key, list);
            }
        }

        return new ArrayList<>(map.values());
    }

    /*
    Same as the solution above, but uses computeIfAbsent(). This is the most concise and optimal solution.

    Same time/space complexities.
    */
    public List<List<String>> v2(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            int[] arr = new int[26];
            for (int i = 0; i < s.length(); i++) {
                arr[s.charAt(i) - 'a']++;
            }

            StringBuilder sb = new StringBuilder();
            for (int i : arr) {
                sb.append(i).append('#');
            }
            String key = sb.toString();

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    /*
    Same as the other two solutions above, but rewrites computeIfAbsent() in a way that's easier 
    to understand. 
    
    Same time/space complexities.
    */
    public List<List<String>> v3(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            int[] arr = new int[26];
            for (int i = 0; i < s.length(); i++) {
                arr[s.charAt(i) - 'a']++;
            }

            StringBuilder sb = new StringBuilder();
            for (int i : arr) {
                sb.append(i).append('#');
            }
            String key = sb.toString();

            List<String> list = map.get(key);
            if (list == null) {
                list = new ArrayList<>();
                map.put(key, list);
            }
            list.add(s);
        }

        return new ArrayList<>(map.values());
    }

    /*
    Another possible solution: using the character frequency map as the key for the parent map. But this is bad practice
    since maps are mutable - if we updated one of the inner maps, it would break the hash function. 
    It works in our solution (hash function doesn't break) because we don't update inner hash maps.
    
    Same time and space complexities, but this is slower than the frequency array approach because comparing maps entry
    by entry is slow.
    */
    public List<List<String>> v4(String[] strs) {
        Map<Map<Character, Integer>, List<String>> map = new HashMap<>();

        for (String s : strs) {
            Map<Character, Integer> freq = new HashMap<>();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int count = freq.getOrDefault(c, 0);
                freq.put(c, count + 1);
            }

            map.computeIfAbsent(freq, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }
    
    /*
    Same solution as above, but we fix the mutable inner map issue by making it an unmodifiable map first.
    
    Same time and space complexities, but slower than the frequency array approach.
    */
    public List<List<String>> v5(String[] strs) {
        Map<Map<Character, Integer>, List<String>> map = new HashMap<>();

        for (String s : strs) {
            Map<Character, Integer> freq = new HashMap<>();
            
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int count = freq.getOrDefault(c, 0);
                freq.put(c, count + 1);
            }

            map.computeIfAbsent(Collections.unmodifiableMap(freq), k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        P_49_GroupAnagrams solver = new P_49_GroupAnagrams();

        // Test cases: {input, expected number of groups, expected group sizes sorted}
        Object[][] tests = new Object[][] {
                { new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }, 3, new int[] { 1, 2, 3 } },
                { new String[] { "" }, 1, new int[] { 1 } },
                { new String[] { "a" }, 1, new int[] { 1 } },
                { new String[] { "abc", "bca", "cab", "xyz", "zyx" }, 2, new int[] { 2, 3 } },
                { new String[] { "abc", "def", "ghi" }, 3, new int[] { 1, 1, 1 } },
                { new String[] { "abc", "abc", "abc" }, 1, new int[] { 3 } },
                { new String[] { "", "" }, 1, new int[] { 2 } },
                { new String[] { "ab", "ba", "abc", "cba", "bac" }, 2, new int[] { 2, 3 } },
                { new String[] { "listen", "silent", "enlist" }, 1, new int[] { 3 } },
                { new String[] { "a", "b", "c", "d", "e" }, 5, new int[] { 1, 1, 1, 1, 1 } },
                { new String[] { "aaa", "aaa", "aaa" }, 1, new int[] { 3 } },
                { new String[] { "ab", "cd", "ba", "dc", "ef" }, 3, new int[] { 1, 2, 2 } },
                { new String[] { "eat", "tea", "ate" }, 1, new int[] { 3 } },
                { new String[] { "rat", "tar", "art", "car" }, 2, new int[] { 1, 3 } },
                { new String[] { "aaab", "abaa", "baaa", "aaab" }, 1, new int[] { 4 } }
        };

        System.out.println("Running tests for P_49_GroupAnagrams.groupAnagrams\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            int expectedGroups = (int) tests[i][1];
            int[] expectedSizes = (int[]) tests[i][2];
            List<List<String>> actual = solver.groupAnagrams(input.clone());

            int[] actualSizes = actual.stream().mapToInt(List::size).sorted().toArray();
            boolean ok = expectedGroups == actual.size() && java.util.Arrays.equals(expectedSizes, actualSizes);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected groups=%d, actual groups=%d, expected sizes=%s, actual sizes=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), expectedGroups, actual.size(),
                    java.util.Arrays.toString(expectedSizes), java.util.Arrays.toString(actualSizes),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_49_GroupAnagrams.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            int expectedGroups = (int) tests[i][1];
            int[] expectedSizes = (int[]) tests[i][2];
            List<List<String>> actual = solver.v2(input.clone());

            int[] actualSizes = actual.stream().mapToInt(List::size).sorted().toArray();
            boolean ok = expectedGroups == actual.size() && java.util.Arrays.equals(expectedSizes, actualSizes);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected groups=%d, actual groups=%d, expected sizes=%s, actual sizes=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), expectedGroups, actual.size(),
                    java.util.Arrays.toString(expectedSizes), java.util.Arrays.toString(actualSizes),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_49_GroupAnagrams.v3\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            int expectedGroups = (int) tests[i][1];
            int[] expectedSizes = (int[]) tests[i][2];
            List<List<String>> actual = solver.v3(input.clone());

            int[] actualSizes = actual.stream().mapToInt(List::size).sorted().toArray();
            boolean ok = expectedGroups == actual.size() && java.util.Arrays.equals(expectedSizes, actualSizes);
            if (ok)
                pass3++;
            System.out.printf("Test %d: input=%s => expected groups=%d, actual groups=%d, expected sizes=%s, actual sizes=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), expectedGroups, actual.size(),
                    java.util.Arrays.toString(expectedSizes), java.util.Arrays.toString(actualSizes),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass3, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_49_GroupAnagrams.v4\n");
        int pass4 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            int expectedGroups = (int) tests[i][1];
            int[] expectedSizes = (int[]) tests[i][2];
            List<List<String>> actual = solver.v4(input.clone());

            int[] actualSizes = actual.stream().mapToInt(List::size).sorted().toArray();
            boolean ok = expectedGroups == actual.size() && java.util.Arrays.equals(expectedSizes, actualSizes);
            if (ok)
                pass4++;
            System.out.printf("Test %d: input=%s => expected groups=%d, actual groups=%d, expected sizes=%s, actual sizes=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), expectedGroups, actual.size(),
                    java.util.Arrays.toString(expectedSizes), java.util.Arrays.toString(actualSizes),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass4, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_49_GroupAnagrams.v5\n");
        int pass5 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            int expectedGroups = (int) tests[i][1];
            int[] expectedSizes = (int[]) tests[i][2];
            List<List<String>> actual = solver.v5(input.clone());

            int[] actualSizes = actual.stream().mapToInt(List::size).sorted().toArray();
            boolean ok = expectedGroups == actual.size() && java.util.Arrays.equals(expectedSizes, actualSizes);
            if (ok)
                pass5++;
            System.out.printf("Test %d: input=%s => expected groups=%d, actual groups=%d, expected sizes=%s, actual sizes=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), expectedGroups, actual.size(),
                    java.util.Arrays.toString(expectedSizes), java.util.Arrays.toString(actualSizes),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass5, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("groupAnagrams: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", pass3, tests.length);
        System.out.printf("v4: %d/%d tests passed\n", pass4, tests.length);
        System.out.printf("v5: %d/%d tests passed\n", pass5, tests.length);
    }
}
