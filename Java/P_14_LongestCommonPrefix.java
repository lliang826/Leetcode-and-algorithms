public class P_14_LongestCommonPrefix {
    /*
    Horizontal scanning approach: copy the first string into a StringBuilder as the initial prefix,
    then compare it character by character against each remaining string. On a mismatch, truncate
    the prefix to the mismatch index. After the inner loop, if the prefix is still longer than the
    current string, trim the excess characters.

    Time: O(S), where S is the sum of all characters across all strings
    Space: O(m), where m is the length of the first string (for the StringBuilder)
    */
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            sb.append(c);
        }

        for (int i = 1; i < strs.length; i++) {
            for (int j = 0; j < strs[i].length(); j++) {
                char c = strs[i].charAt(j);
                if (j < sb.length() && sb.charAt(j) != c) {
                    sb.setLength(j);
                    break;
                }
            }

            int extraLength = sb.length() - strs[i].length();
            while (extraLength > 0) {
                sb.deleteCharAt(sb.length() - extraLength);
                extraLength--;
            }
        }

        return sb.toString();
    }

    /*
    Cleaner horizontal scanning: initialize the StringBuilder directly with the first string.
    For each subsequent string, first truncate the prefix if the current string is shorter,
    then scan for the first mismatch and truncate there. This avoids the separate
    "trim excess" step at the end by handling length upfront.

    Time: O(S), where S is the sum of all characters across all strings
    Space: O(m), where m is the length of the first string (for the StringBuilder)
    */
    public String longestCommonPrefix2(String[] strs) {
        StringBuilder sb = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < sb.length()) {
                sb.setLength(strs[i].length());
            }
            for (int j = 0; j < strs[i].length(); j++) {
                char c = strs[i].charAt(j);
                if (j < sb.length() && sb.charAt(j) != c) {
                    sb.setLength(j);
                    break;
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        P_14_LongestCommonPrefix solver = new P_14_LongestCommonPrefix();

        // Test cases: {input string array, expected prefix}
        Object[][] tests = new Object[][] {
                { new String[] { "flower", "flow", "flight" }, "fl" },           // standard example
                { new String[] { "dog", "racecar", "car" }, "" },                // no common prefix
                { new String[] { "abc", "abc", "abc" }, "abc" },                 // all identical
                { new String[] { "alone" }, "alone" },                           // single string
                { new String[] { "", "b" }, "" },                                // empty string in array
                { new String[] { "a", "ab", "abc" }, "a" },                      // prefix is single char
                { new String[] { "ab", "abc", "abcd" }, "ab" },                  // prefix is shortest string
                { new String[] { "interview", "interrupt" }, "inter" },           // two strings, longer prefix
                { new String[] { "a", "a", "a" }, "a" },                         // all single identical chars
                { new String[] { "a", "b", "c" }, "" },                          // all single different chars
        };

        System.out.println("Running tests for P_14_LongestCommonPrefix.longestCommonPrefix\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.longestCommonPrefix(input);

            boolean ok = expected.equals(actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_14_LongestCommonPrefix.longestCommonPrefix2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] input = (String[]) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.longestCommonPrefix2(input);

            boolean ok = expected.equals(actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("longestCommonPrefix: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("longestCommonPrefix2: %d/%d tests passed\n", pass2, tests.length);
    }
}
