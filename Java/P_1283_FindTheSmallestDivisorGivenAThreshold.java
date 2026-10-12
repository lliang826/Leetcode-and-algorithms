public class P_1283_FindTheSmallestDivisorGivenAThreshold {
    /*
    Binary search approach. Very similar problem to 875. Koko Eating Bananas.

    For this problem, we are asked to find the smallest divisor such that the sum of
    each division in the array is less than or equal to the threshold. Since we can't
    derive a mathematical formula to find this divisor, we can only try a range of
    values.

    We could use brute force to search through this range of values, but since the 
    check is monotonic, we can use binary search to improve the time complexity. (if
    a divisor's result exceeds the threshold, all values smaller than the divisor will
    also exceed the threshold; if a divisor's result is less than or equal to the
    threshold, there may be a value smaller than the divisor that also has a result
    less than or equal to the threshold).

    The lower bound of this range is 1 (the divisor is a positive integer, and we 
    can't divide by zero), and the upper bound is the largest element in the array
    (if we divide each element by the larget element, each division's ceiling value
    will be 1, so the sum will be the length of the array)

    Time: O(n + log r * n) -> O(n log r)
    - O(n) iterating through the entire array to find the largest element
    - O(log r) binary search on the range
    - O(n) using the divisor to divide each element in the array

    Space: O(1) no additional data structures
    */
    class Solution {
        public int smallestDivisor(int[] nums, int threshold) {
            int left = 1;
            int right = 0;
            for (int n : nums) {
                right = Math.max(right, n);
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(nums, threshold, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(int[] nums, int threshold, double divisor) {
            double sum = 0;
            for (double n : nums) {
                sum += Math.ceil(n / divisor);
            }

            return sum <= threshold;
        }
    }

    public static void main(String[] args) {
        P_1283_FindTheSmallestDivisorGivenAThreshold outer = new P_1283_FindTheSmallestDivisorGivenAThreshold();

        int[] large = new int[50000];
        java.util.Arrays.fill(large, 1000000);

        // Test cases: {nums, threshold, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 5, 9 }, 6, 5 },
                { new int[] { 44, 22, 33, 11, 1 }, 5, 44 },
                { new int[] { 21212, 10101, 12121 }, 1000000, 1 },
                { new int[] { 2, 3, 5, 7, 11 }, 11, 3 },
                { new int[] { 19 }, 5, 4 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 1000000 }, 1, 1000000 },
                { new int[] { 1, 1, 1, 1 }, 4, 1 },
                { new int[] { 5, 3, 8 }, 3, 8 },
                { new int[] { 10, 20, 30 }, 6, 10 },
                { large, 1000000, 50000 },
        };

        System.out.println("Running tests for P_1283_FindTheSmallestDivisorGivenAThreshold.smallestDivisor\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int threshold = (int) tests[i][1];
            int expected = (int) tests[i][2];
            Solution solver = outer.new Solution();
            int actual = solver.smallestDivisor(nums.clone(), threshold);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            String numsStr = nums.length > 10 ? "[" + nums.length + " elements]" : java.util.Arrays.toString(nums);
            System.out.printf("Test %d: nums=%s, threshold=%d => expected=%d, actual=%d => %s\n",
                    i + 1, numsStr, threshold, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("smallestDivisor: %d/%d tests passed\n", pass, tests.length);
    }
}
