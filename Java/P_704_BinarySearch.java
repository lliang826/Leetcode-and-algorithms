/*
 * Binary search implementation with overflow protection.
 * 
 * Key implementation details:
 * - Uses (right - left) / 2 + left instead of (left + right) / 2 to prevent integer overflow
 *   - The difference between the two pointers is the mid point; add this difference to the left 
 *     pointer to reach it
 * - The condition left <= right ensures we check all possible positions
 * 
 * Time complexity: O(log n) because we are halving the search space in each iteration
 * Space complexity: O(1) because we are using pointers, no additional data structures
 */

public class P_704_BinarySearch {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        P_704_BinarySearch solver = new P_704_BinarySearch();

        // Test cases: {nums, target, expected}
        Object[][] tests = new Object[][] {
                { new int[]{-1, 0, 3, 5, 9, 12}, 9, 4 },
                { new int[]{-1, 0, 3, 5, 9, 12}, 2, -1 },
                { new int[]{5}, 5, 0 },
                { new int[]{5}, 3, -1 },
                { new int[]{1, 3, 5, 7, 9}, 1, 0 },
                { new int[]{1, 3, 5, 7, 9}, 9, 4 },
                { new int[]{1, 3, 5, 7, 9}, 5, 2 },
                { new int[]{1, 3, 5, 7, 9}, 4, -1 },
                { new int[]{1, 3, 5, 7, 9}, 0, -1 },
                { new int[]{1, 3, 5, 7, 9}, 10, -1 },
                { new int[]{2, 4, 6, 8, 10, 12, 14, 16}, 8, 3 },
                { new int[]{2, 4, 6, 8, 10, 12, 14, 16}, 1, -1 },
                { new int[]{2, 4, 6, 8, 10, 12, 14, 16}, 17, -1 },
                { new int[]{-10, -5, 0, 5, 10}, -5, 1 },
                { new int[]{-10, -5, 0, 5, 10}, 0, 2 },
                { new int[]{1, 2}, 1, 0 },
                { new int[]{1, 2}, 2, 1 },
                { new int[]{1, 2}, 3, -1 }
        };

        System.out.println("Running tests for P_704_BinarySearch.search\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int target = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.search(nums, target);
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
