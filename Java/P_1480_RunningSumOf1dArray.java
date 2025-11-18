public class P_1480_RunningSumOf1dArray {

    /*
     * Calculating prefix sum.
     * 
     * Time: O(n), must iterate through all elements in the array
     * Space: O(n), creating a separate array to hold the prefix sums
     */
    public int[] runningSum(int[] nums) {
        int[] runningSum = new int[nums.length];
        runningSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            runningSum[i] = runningSum[i - 1] + nums[i];
        }
        return runningSum;
    }

    /*
     * We can improve upon the first solution by creating the prefix sum in-place and returning the original array.
     * 
     * Time: O(n), same, no change
     * Space: O(1), no additional array
     */
    public int[] v2(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i] + nums[i - 1];
        }
        return nums;
    }

    public static void main(String[] args) {
        P_1480_RunningSumOf1dArray solver = new P_1480_RunningSumOf1dArray();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 2, 3, 4}, new int[]{1, 3, 6, 10} },
                { new int[]{1, 1, 1, 1, 1}, new int[]{1, 2, 3, 4, 5} },
                { new int[]{3, 1, 2, 10, 1}, new int[]{3, 4, 6, 16, 17} },
                { new int[]{5}, new int[]{5} },
                { new int[]{0, 0, 0}, new int[]{0, 0, 0} },
                { new int[]{-1, -2, -3}, new int[]{-1, -3, -6} },
                { new int[]{10, -5, 3, -2, 8}, new int[]{10, 5, 8, 6, 14} }
        };

        System.out.println("Running tests for P_1480_RunningSumOf1dArray.runningSum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            int[] actual = solver.runningSum(input.clone());
            
            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), 
                    java.util.Arrays.toString(expected), 
                    java.util.Arrays.toString(actual), 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1480_RunningSumOf1dArray.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            int[] actual = solver.v2(input.clone());
            
            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), 
                    java.util.Arrays.toString(expected), 
                    java.util.Arrays.toString(actual), 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("runningSum: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
