import java.util.Arrays;

public class P_2389_LongestSubsequenceWithLimitedSum {
    /*
    Greedy + prefix sum + binary search.

    A tricky problem because it combines 3 different topics. The first step is to recognize the
    greedy approach: since we want the maximum size of the subsequence, we should sort nums in
    ascending order and take the smallest elements first.

    The next step is the trickiest; instead of iterating through nums for every element in 
    queries to find the sum, we can use prefix sum. Then, binary search allows us to find the
    cutoff for the number of elements in nums whose sum is less than or equal to queries[i]; the
    cutoff index is the maximum size. Binary search only works because nums and prefix are sorted.

    Time: O(n log n) + O(m log n) => O((n + m) log n)
    - O(n log n) to sort nums
    - O(n) to build the prefix sum
    - O(m log n) to perform binary search for each element in queries

    Space: O(n) + O(m) => O(n + m)
    - O(1) to sort nums in place
    - O(n) to hold the prefix sum
    - O(m) to hold the results array
    */
    public int[] answerQueries(int[] nums, int[] queries) {
        int n = nums.length;
        int m = queries.length;
        Arrays.sort(nums);

        int[] prefix = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            prefix[i] = sum;
        }

        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int index = binarySearchRightMostInsertionPoint(prefix, queries[i]);
            res[i] = index;
        }

        return res;
    }

    private int binarySearchRightMostInsertionPoint(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (target < arr[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        P_2389_LongestSubsequenceWithLimitedSum solver = new P_2389_LongestSubsequenceWithLimitedSum();

        // Test cases: {nums, queries, expected}
        int[][][] tests = new int[][][] {
                // LeetCode example 1: unsorted input, requires sorting first
                { { 4, 5, 2, 1 }, { 3, 10, 21 }, { 2, 3, 4 } },
                // LeetCode example 2: query smaller than every element
                { { 2, 3, 4, 5 }, { 1 }, { 0 } },
                // Single element
                { { 1 }, { 1, 2 }, { 1, 1 } },
                // Duplicates in nums
                { { 1, 1, 1, 1 }, { 1, 2, 3, 4, 5 }, { 1, 2, 3, 4, 4 } },
                // Queries exactly equal to prefix sums (boundary: <= should count)
                { { 3, 1, 2 }, { 1, 3, 6 }, { 1, 2, 3 } },
                // Queries just below prefix sums
                { { 1, 2, 3 }, { 2, 5 }, { 1, 2 } },
                // Every element larger than every query
                { { 10, 20 }, { 5, 9 }, { 0, 0 } },
                // Query large enough to take everything
                { { 5, 5, 5 }, { 1000000 }, { 3 } },
                // Unsorted queries
                { { 7, 1, 3 }, { 11, 4, 1, 3 }, { 3, 2, 1, 1 } },
                // Large values near constraint limits
                { { 1000000, 1000000, 1000000 }, { 1999999, 2000000, 3000000 }, { 1, 2, 3 } }
        };

        System.out.println("Running tests for P_2389_LongestSubsequenceWithLimitedSum.answerQueries\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = tests[i][0];
            int[] queries = tests[i][1];
            int[] expected = tests[i][2];
            // Clone because answerQueries sorts nums in place
            int[] actual = solver.answerQueries(nums.clone(), queries);

            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, queries=%s => expected=%s, actual=%s => %s\n",
                    i + 1, Arrays.toString(nums), Arrays.toString(queries),
                    Arrays.toString(expected), Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("answerQueries: %d/%d tests passed\n", pass, tests.length);
    }
}
