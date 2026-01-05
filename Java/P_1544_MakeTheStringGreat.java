import java.util.Stack;

public class P_1544_MakeTheStringGreat {
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
    
    public String v3(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && Character.toLowerCase(stack.peek()) == Character.toLowerCase(c) && stack.peek() != c) {
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
