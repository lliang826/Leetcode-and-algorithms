public class P_344_ReverseString {
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        P_344_ReverseString solver = new P_344_ReverseString();

        // Test cases: {input, expected}
        String[][] tests = new String[][] {
                { "hello", "olleh" },
                { "Hannah", "hannaH" },
                { "", "" },
                { "a", "a" },
                { "ab", "ba" },
                { "abc", "cba" },
                { "abcd", "dcba" },
                { "racecar", "racecar" },
                { "12345", "54321" },
                { "A man a plan a canal Panama", "amanaP lanac a nalp a nam A" },
                { "Was it a car or a cat I saw?", "?was I tac a ro rac a ti saW" },
                { "!@#$%", "%$#@!" },
                { "aA", "Aa" },
                { "abcdef", "fedcba" },
                { "programming", "gnimmargorprogramming".substring(0, 11) } // "gnimmargorp"
        };

        System.out.println("Running tests for P_344_ReverseString.reverseString\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = tests[i][0];
            String expected = tests[i][1];
            
            // Convert input to char array for the method
            char[] inputArray = input.toCharArray();
            solver.reverseString(inputArray);
            String actual = new String(inputArray);
            
            boolean ok = expected.equals(actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
