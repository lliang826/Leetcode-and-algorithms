import java.util.Stack;

public class P_1047_RemoveAllAdjacentDuplicatesInString {
    /*
    Stack approach. We pop any adjacent duplicate characters and push other characters to the stack.
    Finally, we have to use StringBuilder to return the remaining characters in the stack as a string.
    
    Time: O(n), where n is the number of characters in the input string
    Space: O(n), in the worst case scenario, there are no adjacent duplicate characters, which means
    that all characters are added to the stack
    */
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stack.size(); i++) {
            sb.append(stack.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        P_1047_RemoveAllAdjacentDuplicatesInString solver = new P_1047_RemoveAllAdjacentDuplicatesInString();

        Object[][] tests = new Object[][] {
                { "abbaca", "ca" },
                { "azxxzy", "ay" },
                { "a", "a" },
                { "aa", "" },
                { "aabbcc", "" },
                { "abccba", "" },
                { "abcd", "abcd" },
                { "aabbccdd", "" },
                { "aabbccdde", "e" },
                { "aaaaa", "a" },
                { "aaaa", "" },
                { "abba", "" },
                { "abbbba", "" },
                { "mississippi", "m" },
                { "aabbaabb", "" },
                { "xyzxyz", "xyzxyz" }
        };

        System.out.println("Running tests for P_1047_RemoveAllAdjacentDuplicatesInString.removeDuplicates\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.removeDuplicates(input);

            boolean ok = expected.equals(actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("removeDuplicates: %d/%d tests passed\n", pass1, tests.length);
    }
}
