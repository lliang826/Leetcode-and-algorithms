/*
 * Sliding window technique to find the smallest subarray with a sum that is equal to or greater than the target.
 * 
 * The sliding window technique uses two pointers (left and right) to find a subarray/interval within the array.
 * When the right pointer increments, the size of the subarray increases. When the left pointer increments, the
 * size of the subarray decreases. Sliding window algorithms can be used to solve problems involving contiguous
 * subarrays/substrings:
 * - longest/shortest subarray/substring with a condition
 * - counting subarrays/substrings that meet a requirement
 * 
 * For this problem, we initialize a left and right pointer, a variable to hold the sum, and a variable to hold
 * the length of the subarray. Both pointers start at the beginning of the array, but a for loop allows the right
 * pointer to iterate through the entire array. 
 * In each iteration, we add the integer to the right pointer to the sum and check if the sum is greater than or
 * equal to the target. If it is, we can save this length and we can also start incrementing the left pointer.
 * As the left pointer increments, the window/subarray shrinks, so we deduct the value at the left pointer from
 * the sum. The left pointer continuously increments until the sum is no longer equal to or greater than the 
 * target.
 * Finally, we perform a check to return the length, or 0 if no subarray exists.
 */

public class P_209_MinimumSizeSubarraySum {
    // Initial attempt
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int length = nums.length;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > target) {
                sum -= nums[left];
                left++;
            }
            if (sum < target && left > 0) {
                left--;
                sum += nums[left];
            }

            if (sum >= target) {
                length = Math.min(length, right - left + 1);
            }
        }

        if (sum >= target) {
            return length;
        } else {
            return 0;
        }
    }

    // Better solution
    public int v2(int target, int[] nums) {
        int left = 0, right = 0;
        int sum = 0;
        int length = nums.length;

        for (right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                length = Math.min(length, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        if (left > 0) {
            left--;
            sum += nums[left];
        }
        return sum >= target ? length : 0;
    }

    // Solution from Leetcode
    public int v3(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int length = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                length = Math.min(length, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return length == Integer.MAX_VALUE ? 0 : length;
    }

    public static void main(String[] args) {
        P_209_MinimumSizeSubarraySum solver = new P_209_MinimumSizeSubarraySum();

        // Test cases: {target, input, expected}
        Object[][] tests = new Object[][] {
                { 7, new int[]{2, 3, 1, 2, 4, 3}, 2 },
                { 4, new int[]{1, 4, 4}, 1 },
                { 11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}, 0 },
                { 15, new int[]{1, 2, 3, 4, 5}, 5 },
                { 3, new int[]{1, 1, 1, 1, 1, 1, 1, 1}, 3 },
                { 213, new int[]{12, 28, 83, 4, 25, 26, 25, 2, 25, 25, 25, 12}, 8 },
                { 1, new int[]{1}, 1 },
                { 5, new int[]{2, 1, 2, 4, 3, 1}, 2 },
                { 6, new int[]{10, 2, 3}, 1 },
                { 9, new int[]{1, 2, 3, 4}, 3 },
                { 100, new int[]{1, 4, 4}, 0 },
                { 80, new int[]{10, 5, 13, 4, 8, 4, 5, 11, 14, 9, 16, 10, 20, 8}, 6 }
        };

        System.out.println("Running tests for P_209_MinimumSizeSubarraySum.minSubArrayLen\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int target = (int) tests[i][0];
            int[] input = (int[]) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.minSubArrayLen(target, input.clone()); // Clone to avoid modifying original
            
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: target=%d, input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, target, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_209_MinimumSizeSubarraySum.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int target = (int) tests[i][0];
            int[] input = (int[]) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.v2(target, input.clone()); // Clone to avoid modifying original
            
            boolean ok = (expected == actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: target=%d, input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, target, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);
        
        // Test v3 function
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_209_MinimumSizeSubarraySum.v3\n");
        int passV3 = 0;
        for (int i = 0; i < tests.length; i++) {
            int target = (int) tests[i][0];
            int[] input = (int[]) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.v3(target, input.clone()); // Clone to avoid modifying original
            
            boolean ok = (expected == actual);
            if (ok)
                passV3++;
            System.out.printf("Test %d: target=%d, input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, target, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minSubArrayLen: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", passV3, tests.length);
    }
}