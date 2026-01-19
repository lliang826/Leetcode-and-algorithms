import java.util.ArrayDeque;
import java.util.Deque;

public class P_1438_LongestContinuousSubarray {
    /*
    First attempt using monotonic deques. This approach uses an if-else to check the limit,
    but only moves the left pointer by 1 position when the constraint is violated.
    This is incorrect for two reasons:
    1. Moving left by only 1 may not be enough to satisfy the constraint
    2. Only updates max when constraint is satisfied, missing valid windows after shrinking
    
    Note: Math.abs() on line 29 is unnecessary since the "dec" deque always has the max at the front
    and the "inc" deque always has the min at the front. Therefore, we can find the non-negative
    difference through dec.peekFirst() - inc.peekFirst() without needing Math.abs().
    
    Time: O(n) where n is the length of nums, as each element is added and removed at most once
    Space: O(n) for the two deques in the worst case
    */
    public int longestSubarrayV1(int[] nums, int limit) {
        Deque<Integer> inc = new ArrayDeque<>();
        Deque<Integer> dec = new ArrayDeque<>();
        int max = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            while (!inc.isEmpty() && inc.peekLast() > nums[right]) {
                inc.removeLast();
            }
            while (!dec.isEmpty() && dec.peekLast() < nums[right]) {
                dec.removeLast();
            }
            inc.addLast(nums[right]);
            dec.addLast(nums[right]);

            if (Math.abs(inc.peekFirst() - dec.peekFirst()) <= limit) {
                max = Math.max(max, right - left + 1);
            } else {
                if (!inc.isEmpty() && inc.peekFirst() == nums[left]) {
                    inc.removeFirst();
                }
                if (!dec.isEmpty() && dec.peekFirst() == nums[left]) {
                    dec.removeFirst();
                }
                left++;
            }
        }

        return max;
    }

    /*
    Correct solution using monotonic deques. We maintain two deques:
    - inc: monotonic increasing deque tracking minimum values
    - dec: monotonic decreasing deque tracking maximum values
    
    The front of 'dec' always has the maximum in the current window,
    and the front of 'inc' always has the minimum. We use a while loop
    to shrink the window from the left until the constraint is satisfied.
    
    Time: O(n) where n is the length of nums, as each element is added and removed at most once
    Space: O(n) for the two deques in the worst case
    */
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> inc = new ArrayDeque<>();
        Deque<Integer> dec = new ArrayDeque<>();
        int max = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            while (!inc.isEmpty() && inc.peekLast() > nums[right]) {
                inc.removeLast();
            }
            while (!dec.isEmpty() && dec.peekLast() < nums[right]) {
                dec.removeLast();
            }
            inc.addLast(nums[right]);
            dec.addLast(nums[right]);

            while (dec.peekFirst() - inc.peekFirst() > limit) {
                if (!inc.isEmpty() && inc.peekFirst() == nums[left]) {
                    inc.removeFirst();
                }
                if (!dec.isEmpty() && dec.peekFirst() == nums[left]) {
                    dec.removeFirst();
                }
                left++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    public static void main(String[] args) {
        P_1438_LongestContinuousSubarray solver = new P_1438_LongestContinuousSubarray();

        // Test cases: [nums[], limit, expected_result]
        Object[][] testCases = new Object[][] {
            // Test 1: Example 1 from LeetCode
            {new int[]{8, 2, 4, 7}, 4, 2},
            // Test 2: Example 2 from LeetCode
            {new int[]{10, 1, 2, 4, 7, 2}, 5, 4},
            // Test 3: Example 3 from LeetCode
            {new int[]{4, 2, 2, 2, 4, 4, 2, 2}, 0, 3},
            // Test 4: Single element
            {new int[]{5}, 0, 1},
            // Test 5: All elements same
            {new int[]{3, 3, 3, 3, 3}, 0, 5},
            // Test 6: Strictly increasing
            {new int[]{1, 2, 3, 4, 5}, 2, 3},
            // Test 7: Strictly decreasing
            {new int[]{5, 4, 3, 2, 1}, 2, 3},
            // Test 8: Large limit (entire array valid)
            {new int[]{1, 5, 10, 15, 20}, 100, 5},
            // Test 9: Limit 0 with duplicates at start
            {new int[]{1, 1, 1, 2, 2, 2}, 0, 3},
            // Test 10: Two elements within limit
            {new int[]{1, 3}, 2, 2},
            // Test 11: Two elements outside limit
            {new int[]{1, 10}, 5, 1},
            // Test 12: Complex case with multiple valid windows
            {new int[]{1, 5, 6, 7, 8, 10, 6, 5, 6}, 4, 4}
        };

        System.out.println("Running tests for P_1438_LongestContinuousSubarray.longestSubarrayV1\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = (int[]) testCases[i][0];
            int limit = (int) testCases[i][1];
            int expected = (int) testCases[i][2];
            int actual = solver.longestSubarrayV1(nums, limit);
            
            boolean ok = expected == actual;
            totalTests1++;
            if (ok) pass1++;
            
            System.out.printf("Test %d: nums=%s, limit=%d\n", i + 1, arrayToString(nums), limit);
            System.out.printf("  Expected=%d, Actual=%d => %s\n", expected, actual, (ok ? "PASS" : "FAIL"));
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1438_LongestContinuousSubarray.longestSubarray\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = (int[]) testCases[i][0];
            int limit = (int) testCases[i][1];
            int expected = (int) testCases[i][2];
            int actual = solver.longestSubarray(nums, limit);
            
            boolean ok = expected == actual;
            totalTests2++;
            if (ok) pass2++;
            
            System.out.printf("Test %d: nums=%s, limit=%d\n", i + 1, arrayToString(nums), limit);
            System.out.printf("  Expected=%d, Actual=%d => %s\n", expected, actual, (ok ? "PASS" : "FAIL"));
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("longestSubarrayV1: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("longestSubarray: %d/%d tests passed\n", pass2, totalTests2);
    }

    private static String arrayToString(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}