import java.util.Stack;

public class P_1544_MakeTheStringGreat {
    /*
    Using StringBuilder as a stack. As we iterate through the characters in the input string, if the current
    char is the same as the last char, but in a different letter case, we pop both the current char amd the
    last char. Otherwise, we add it to the stack.
    The tricky part of this problem is figuring out if the characters are the same but in different letter cases.
    
    Time: O(n), where n is the number of characters in the input string
    Space: O(n), worst case scenario all the chars are different so we push all of them to the stack
    */
    public String makeGood(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isCurrCharUpperCase = Character.isUpperCase(c);
            boolean isSameAsLastChar = sb.length() > 0
                    && Character.toLowerCase(sb.charAt(sb.length() - 1)) == Character.toLowerCase(c);

            if (sb.length() > 0) {
                boolean isLastCharUpperCase = Character.isUpperCase(sb.charAt(sb.length() - 1));
                if (isSameAsLastChar && isLastCharUpperCase != isCurrCharUpperCase) {
                    sb.deleteCharAt(sb.length() - 1);
                    continue;
                }
            }
            sb.append(c);
        }

        return sb.toString();
    }

    /*
    This is the cleanest version of the StringBuilder-as-stack approach. The check if the characters are the
    same but in different letter cases can be messy, but this solution finds their unicode value difference.
    The difference between a lowercase and uppercase unicode letter is 32.
    
    Same time and space complexities as above.
    */
    public String v2(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (sb.length() > 0 && Math.abs(sb.charAt(sb.length() - 1) - c) == 32) {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
    
    /*
    In my opinion, this solution is the easiest to understand. We use a Stack, which allows us to use push(),
    pop(), and peek() methods. After iterating through the input string, we use a StringBuilder to concatenate
    all the characters together.
    In addition, the character comparison check here is easier/more intuitive: if the 2 characters are not the
    same, but their lowercase characters are, it means that they have different letter cases.
    
    Same time and space complexities as above, except that the time complexity for this one is actually
    O(n + n) => O(n).
    */
    public String v3(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && Character.toLowerCase(stack.peek()) == Character.toLowerCase(c)
                    && stack.peek() != c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    /*
    TODO: 2 pointer approach.
    */
    public String v4(String s) {
        return "";
    }
    
    public static void main(String[] args) {
        P_1544_MakeTheStringGreat solver = new P_1544_MakeTheStringGreat();

        Object[][] tests = new Object[][] {
                { "leEeetcode", "leetcode" },
                { "abBAcC", "" },
                { "s", "s" },
                { "", "" },
                { "aA", "" },
                { "Aa", "" },
                { "abc", "abc" },
                { "ABC", "ABC" },
                { "aaBB", "aaBB" },
                { "abBa", "aa" },
                { "mC", "mC" },
                { "Pp", "" },
                { "RrTt", "" },
                { "aAbBcC", "" },
                { "kkdsFuqUfSDKK", "kkdsFuqUfSDKK" }
        };

        System.out.println("Running tests for P_1544_MakeTheStringGreat.makeGood\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = (String) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.makeGood(s);

            boolean ok = expected.equals(actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: s=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, s, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1544_MakeTheStringGreat.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = (String) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.v2(s);

            boolean ok = expected.equals(actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: s=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, s, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1544_MakeTheStringGreat.v3\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = (String) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.v3(s);

            boolean ok = expected.equals(actual);
            if (ok)
                pass3++;
            System.out.printf("Test %d: s=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, s, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("makeGood: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", pass3, tests.length);
    }
}
