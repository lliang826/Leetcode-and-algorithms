public class P_1231_Divide_Chocolate {
    /*
    Binary search with greedy approach.

    This question is trickier and less intuitive than 875 Koko. For this question, we're trying
    to maximize the minimum: we're taking the chocolate piece with the smallest total sweetness,
    so we try to maximize this value.

    Like 875, there's no mathematical formula to derive the answer, so we try a range of values.
    The lower bound is 1 (it's not 0 because we're guaranteed to get something at least) and the
    upper bound is the sum of the sweetness array (we can take the entire chocolate bar). Since
    we're trying a range of values and the condition for the check is monotonic, we can use
    binary search instead of brute force to improve the algorithm's efficiency.

    In each iteration of binary search, we check if the selected total sweetness value (ts) allows 
    us to divide the chocolate bar into k + 1 pieces: we can do this by counting the number of
    subarrays with a sum greater than or equal to the selected total sweetness. If we don't have
    enough pieces, we need a smaller sweetness total. If we do have enough pieces, we can look
    for a larger sweetness total (we're trying to maximize the sweetness).
    
    The greedy approach happens in the check: once the subarray sum satisfies the selected total
    sweetness, we cut the piece there and start another piece. This allows us to maximize the number
    of pieces.

    Since we're trying to find the maximum value, we return the right pointer because it will
    give us the rightmost and largest value (the range of values is sorted).

    Time: O(n log sum)
    - O(log sum) binary search on the range of possible values, which ranges from 1 to the array sum
    - O(n) iterating through the sweetness array, where n is the number of integers in the array

    Space: O(1) no additional data structures
    */
    class Solution {
        public int maximizeSweetness(int[] sweetness, int k) {
            int left = 1;
            int right = 0;
            for (int i : sweetness) {
                right += i;
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(sweetness, k, mid)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return right;
        }

        private boolean check(int[] sweetness, int k, int ts) {
            int count = 0;
            int sum = 0;
            for (int i = 0; i < sweetness.length; i++) {
                sum += sweetness[i];
                if (sum >= ts) {
                    count++;
                    sum = 0;
                }
            }

            int pieces = k + 1;
            return count >= pieces;
        }
    }

    /*
    Similar to the original solution: same approach, but slightly improved.

    The range of possible values has shrunk: 
    - The lower bound is the smallest integer in the array; every piece contains at least one
    chunk, so every piece is at least the min
    - The upper bound is the average total, sum / (k + 1). The smallest of k + 1 pieces can't
    exceed the average, sum / (k + 1). If every piece were larger than the average, the pieces
    would add up to more than the whole bar.

    Added an early return in the check: if the number of subarrays has already met the number
    of pieces criteria, k + 1, then we can immediately return true, no need to continue iterating.

    Time: O(n log (sum / (k + 1)))
    Space: O(1)
    */
    class v2 {
        public int maximizeSweetness(int[] sweetness, int k) {
            int left = Integer.MAX_VALUE;
            int sum = 0;
            for (int i : sweetness) {
                left = Math.min(i, left);
                sum += i;
            }
            int right = sum / (k + 1);

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(sweetness, k, mid)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return right;
        }

        private boolean check(int[] sweetness, int k, int ts) {
            int count = 0;
            int sum = 0;
            for (int i = 0; i < sweetness.length; i++) {
                sum += sweetness[i];
                if (sum >= ts) {
                    count++;
                    sum = 0;
                    if (count == k + 1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public static void main(String[] args) {
        P_1231_Divide_Chocolate outer = new P_1231_Divide_Chocolate();

        int[] large = new int[10000];
        java.util.Arrays.fill(large, 100000);

        // Test cases: {sweetness, k, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 5, 6 },
                { new int[] { 5, 6, 7, 8, 9, 1, 2, 3, 4 }, 8, 1 },
                { new int[] { 1, 2, 2, 1, 2, 2, 1, 2, 2 }, 2, 5 },
                { new int[] { 5 }, 0, 5 },
                { new int[] { 1, 2, 3 }, 0, 6 },
                { new int[] { 1, 1, 1, 1 }, 3, 1 },
                { new int[] { 10, 1, 1, 10 }, 1, 11 },
                { new int[] { 1, 2, 3, 4, 5 }, 1, 6 },
                { new int[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 2, 8 },
                { large, 0, 1000000000 },
                { large, 9999, 100000 },
        };

        System.out.println("Running tests for P_1231_Divide_Chocolate.maximizeSweetness\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] sweetness = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            Solution solver = outer.new Solution();
            int actual = solver.maximizeSweetness(sweetness.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            String sweetStr = sweetness.length > 10 ? "[" + sweetness.length + " elements]"
                    : java.util.Arrays.toString(sweetness);
            System.out.printf("Test %d: sweetness=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, sweetStr, k, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1231_Divide_Chocolate.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] sweetness = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            v2 solver = outer.new v2();
            int actual = solver.maximizeSweetness(sweetness.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            String sweetStr = sweetness.length > 10 ? "[" + sweetness.length + " elements]"
                    : java.util.Arrays.toString(sweetness);
            System.out.printf("Test %d: sweetness=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, sweetStr, k, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("maximizeSweetness: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
