/*
 * Two pointers solution: Use two pointers starting from opposite ends of the string and move them
 * toward each other. We preprocess the string by converting to lowercase and removing all non-
 * alphanumeric characters to handle the "ignore case and non-alphanumeric" requirement.
 * 
 * The condition "left < right" (not "left != right") is crucial because:
 * - For even-length strings: pointers will cross over (left becomes > right)
 * - For odd-length strings: pointers will meet at the middle character
 * - "left != right" would miss the crossover case and potentially cause infinite loops
 * 
 * Time complexity: O(n) - single pass through the string after O(n) preprocessing
 * Space complexity: O(n) - due to creating a new preprocessed string with replaceAll()
 * 
 * Alternative approach for O(1) space: skip non-alphanumeric characters on-the-fly during comparison
 * instead of preprocessing, but this solution prioritizes code clarity.
 */

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
