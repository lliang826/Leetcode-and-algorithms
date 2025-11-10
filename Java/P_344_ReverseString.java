/*
 * Very simple algorithm involving 2 pointers. The left pointer is at the first index in the array,
 * whereas the right pointer is at the last index. In each iteration, we swap the values at both
 * indices. 
 */
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

    public void v2(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            int left = i;
            int right = s.length - 1 - i;

            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
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

        // Test the v2() method
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_344_ReverseString.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = tests[i][0];
            String expected = tests[i][1];
            
            // Convert input to char array for the method
            char[] inputArray = input.toCharArray();
            solver.v2(inputArray);
            String actual = new String(inputArray);
            
            boolean ok = expected.equals(actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("reverseString: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
