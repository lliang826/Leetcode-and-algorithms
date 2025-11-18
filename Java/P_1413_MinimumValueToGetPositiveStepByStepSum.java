public class P_1413_MinimumValueToGetPositiveStepByStepSum {

    /*
     * Prefix sum approach. We begin by creating a prefix sum array (we can do this in-place to save memory). The goal is to find the
     * smallest integer in the prefix sums; the difference between this integer and the number 1 is our minimum value answer.
     * If the smallest integer in the prefix sums is greater than 1, we simply return 1 because it's the smallest positive integer.
     * 
     * Time: O(n), iterating through all integers in the input array
     * Space: O(1), creating the prefix sum array in-place, no additional data structures
     * 
     * Example 1: nums = [-3,2,-3,4,2]
     * prefixSum = [-3,-1,-4,0,2]
     * Output: 1 - (-4) = 5
     * 
     * Example 2: nums = [1,2]
     * prefixSum = [1,3]
     * Output: 1
     * 
     * Example 3: nums = [1,-2,-3]
     * prefixSum = [1,-1,-4]
     * Output: 1 - (-4) = 5
     * 
     */
    public int minStartValue(int[] nums) {
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i] + nums[i - 1];
            min = Math.min(min, nums[i]);
        }
        
        return min >= 0 ? 1 : 1 - min;
    }

    public static void main(String[] args) {
        P_1413_MinimumValueToGetPositiveStepByStepSum solver = new P_1413_MinimumValueToGetPositiveStepByStepSum();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[]{-3, 2, -3, 4, 2}, 5 },
                { new int[]{1, 2}, 1 },
                { new int[]{1, -2, -3}, 5 },
                { new int[]{-5, -10, -3}, 19 },
                { new int[]{1, 1, 1, 1}, 1 },
                { new int[]{5}, 1 },
                { new int[]{-1}, 2 },
                { new int[]{0, 0, 0}, 1 },
                { new int[]{2, 3, 5, -5, -1}, 1 },
                { new int[]{-10, 5, -3, 2, -8, 1}, 15 }
        };

        System.out.println("Running tests for P_1413_MinimumValueToGetPositiveStepByStepSum.minStartValue\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.minStartValue(input.clone());
            
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minStartValue: %d/%d tests passed\n", pass, tests.length);
    }
}
