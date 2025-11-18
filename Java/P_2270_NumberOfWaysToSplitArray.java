public class P_2270_NumberOfWaysToSplitArray {

    /*
     * Uses the prefix sum approach. We begin by pre-processing a prefix/cumulative sum array; this requires us to iterate through all elements
     * in the input array O(n). It's important to make this an array of type long, in case we are adding large integers.
     * Example input array: [2, 3, 1, 0]
     * Example prefix sum array: [2, 5, 6, 6]
     * Since we are comparing the sum of the subarrays, having the prefix sum array makes it very efficient to compare sums: to find the sum at
     * an index, we simply have to access that same index in the prefix sum array, which is an O(1) operation.
     * Once the prefix sum array is built, we simply have to iterate through the input array and compare the sum of the left subarray with the
     * sum of the right subarray. We do not iterate on the last index because there is no right subarray there. The left subarray sum is simply 
     * the value in the prefix sum array as mentioned earlier, whereas the right subarray sum is the total sum of the array minus the left 
     * subarray sum. 
     * If the left subarray sum fulfills the condition of being greater than or equal to the right subarray sum, we increment the counter. After
     * all iterations, we return the counter.
     * 
     * Time complexity: O(n + (n - 1)) => O(n) 
     * Space complexity: O(n) prefix sum array
     */
    public int waysToSplitArray(int[] nums) {
        long[] prefix = new long[nums.length];
        prefix[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }

        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (prefix[i] >= prefix[nums.length - 1] - prefix[i]) {
                count++;
            }
        }
        return count;
    }

    /*
     * This is an improvement of the first solution. It still requires some pre-processing (calculating the total sum of the input array), but
     * we don't need to build an entire prefix sum array because in the second for loop when we are comparing the subarray sums, we can also
     * track the sum of the left subarray.
     * Same time complexity, but a huge improvement for space complexity: O(1) no prefix sum array
     */
    public int v2(int[] nums) {
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        int count = 0;
        long leftSum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            leftSum += nums[i];
            if (leftSum >= totalSum - leftSum) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        P_2270_NumberOfWaysToSplitArray solver = new P_2270_NumberOfWaysToSplitArray();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[]{10, 4, -8, 7}, 2 },
                { new int[]{2, 3, 1, 0}, 2 },
                { new int[]{1, 2, 3, 4, 5}, 1 },
                { new int[]{5, 4, 3, 2, 1}, 3 },
                { new int[]{1, 1}, 1 },
                { new int[]{-1, -1}, 1 },
                { new int[]{0, 0}, 1 },
                { new int[]{10, -10, 10, -10, 10}, 2 },
                { new int[]{100, 1, 1, 1, 1}, 4 },
                { new int[]{1, 1, 1, 1, 100}, 0 }
        };

        System.out.println("Running tests for P_2270_NumberOfWaysToSplitArray.waysToSplitArray\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.waysToSplitArray(input.clone());
            
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_2270_NumberOfWaysToSplitArray.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v2(input.clone());
            
            boolean ok = (expected == actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("waysToSplitArray: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}