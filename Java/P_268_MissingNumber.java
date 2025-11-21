/*
* Two other approaches:
* 
* - Sorting
* - Bit manipulation (XOR)
*/

public class P_268_MissingNumber {
    /*
     * Counting array approach. Create an array of size n+1 to count occurrences of
     * each number. Increment the count for each number in the input array. Then
     * iterate through the count array to find the index with 0 occurrences, which
     * is the missing number.
     * 
     * Time: O(n), iterating through the input array and count array
     * Space: O(n), creating a count array of size n+1
     */
    public int missingNumber(int[] nums) {
        int[] count = new int[nums.length + 1];

        for (int num : nums) {
            count[num]++;
        }

        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Math approach. Calculate the sum of all numbers in the input array. Then
     * subtract all numbers from 0 to n from this sum. If there wasn't a missing
     * number, the sum would return to 0. Since there is a missing number, the
     * difference between zero and the sum is the missing number. Alternatively, we
     * can calculate the expected sum using the formula n * (n + 1) / 2 and subtract
     * the actual sum (Gauss' formula).
     * 
     * Time: O(n), iterating through the input array and then from 0 to n
     * Space: O(1), only using a single variable to store the sum
     */
    public int v2(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        for (int i = 0; i < nums.length + 1; i++) {
            sum -= i;
        }

        return 0 - sum;
    }

    public static void main(String[] args) {
        P_268_MissingNumber solver = new P_268_MissingNumber();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 3, 0, 1 }, 2 },
                { new int[] { 0, 1 }, 2 },
                { new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 }, 8 },
                { new int[] { 0 }, 1 },
                { new int[] { 1 }, 0 },
                { new int[] { 1, 2 }, 0 },
                { new int[] { 0, 2 }, 1 },
                { new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 10 },
                { new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 }, 0 },
                { new int[] { 0, 1, 2, 3, 5 }, 4 },
                { new int[] { 2, 0 }, 1 },
                { new int[] { 1, 2, 3, 4 }, 0 },
                { new int[] { 0, 1, 2, 3, 4 }, 5 },
                { new int[] { 5, 4, 3, 2, 0 }, 1 },
                { new int[] { 0, 1, 3, 4, 5, 6, 7 }, 2 }
        };

        System.out.println("Running tests for P_268_MissingNumber.missingNumber\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.missingNumber(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_268_MissingNumber.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v2(input.clone());

            boolean ok = expected == actual;
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("missingNumber: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
