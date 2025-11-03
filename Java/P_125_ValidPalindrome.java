public class P_125_ValidPalindrome {
    public boolean isPalindrome(String s) {
        s = s.toLowerCase().replaceAll("[^a-z0-9]", "");

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        P_125_ValidPalindrome solver = new P_125_ValidPalindrome();

        // Test cases: {input, expected}
        String[][] tests = new String[][] {
                { "A man, a plan, a canal: Panama", "true" },
                { "race a car", "false" },
                { "", "true" },
                { " ", "true" },
                { "a", "true" },
                { "Madam", "true" },
                { "racecar", "true" },
                { "hello", "false" },
                { "A Santa at NASA", "true" },
                { "Was it a car or a cat I saw?", "true" },
                { "No 'x' in Nixon", "true" },
                { "Mr. Owl ate my metal worm", "true" },
                { "12321", "true" },
                { "12345", "false" },
                { "A1B2b1a", "true" },
                { "0P", "false" }
        };

        System.out.println("Running tests for P_125_ValidPalindrome.isPalindrome\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = tests[i][0];
            boolean expected = Boolean.parseBoolean(tests[i][1]);
            boolean actual = solver.isPalindrome(input);
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
