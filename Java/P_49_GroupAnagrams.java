import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_49_GroupAnagrams {
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
        System.out.printf("Overall Summary:\n");
        System.out.printf("groupAnagrams: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", pass3, tests.length);
    }
}
