public class P_410_SplitArrayLargestSum {
    /*
    Minimize the maximum subarray sum

    If the selected subarray sum gives us too many subarrays (more than k), we need to look for a larger value

    If the selected subarray sum gives us not enough subarrays, we need to look for a smaller value

    If selected subarray sum gives us enough subarrays, there still could be a smaller value
    */
    class Solution {
        public int splitArray(int[] nums, int k) {
            int left = 0;
            int right = 0;
            for (int n : nums) {
                left = Math.max(left, n);
                right += n;
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(nums, k, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(int[] nums, int k, int subarraySum) {
            int count = 1;
            int sum = 0;
            for (int n : nums) {
                if (sum + n > subarraySum) {
                    count++;
                    sum = n;
                } else {
                    sum += n;
                }
            }
            return count <= k;
        }
    }

    public static void main(String[] args) {
        P_410_SplitArrayLargestSum outer = new P_410_SplitArrayLargestSum();

        int[] large = new int[1000];
        java.util.Arrays.fill(large, 1000000);

        // Test cases: {nums, k, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 7, 2, 5, 10, 8 }, 2, 18 },
                { new int[] { 1, 2, 3, 4, 5 }, 2, 9 },
                { new int[] { 1, 4, 4 }, 3, 4 },
                { new int[] { 5 }, 1, 5 },
                { new int[] { 2, 3 }, 1, 5 },
                { new int[] { 1, 2, 3, 4, 5 }, 5, 5 },
                { new int[] { 0, 0, 0, 0 }, 2, 0 },
                { new int[] { 0, 5, 0, 3 }, 2, 5 },
                { new int[] { 10, 1, 1, 1, 1 }, 2, 10 },
                { new int[] { 1, 1, 1, 1, 10 }, 2, 10 },
                { new int[] { 2, 2, 2, 2, 2, 2 }, 3, 4 },
                { large, 1, 1000000000 },
                { large, 50, 20000000 },
        };

        System.out.println("Running tests for P_410_SplitArrayLargestSum.splitArray\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            Solution solver = outer.new Solution();
            int actual = solver.splitArray(nums.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            String numsStr = nums.length > 10 ? "[" + nums.length + " elements]" : java.util.Arrays.toString(nums);
            System.out.printf("Test %d: nums=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, numsStr, k, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("splitArray: %d/%d tests passed\n", pass, tests.length);
    }
}
