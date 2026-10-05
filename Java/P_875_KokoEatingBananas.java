public class P_875_KokoEatingBananas {
    /*
    Binary search approach.

    For this problem, we need to find the bananas/hour eating speed (k) so Koko can 
    finish all the piles of bananas within the specified number of hours. Koko can
    only eat from one pile each hour.

    Since we can't really derive a mathematical formula to calculate k, the only
    thing that we can do is to use a brute force approach and try all possibilities
    within a certain range. For this range, the lower bound is 1 (we know that k is
    an integer value, but it can't be negative or 0 since Koko must eat something),
    and the upper bound is max(piles), the maximum value in the piles array. The 
    upper bound is the max value in the piles array because Koko can only eat from
    one pile per hour; if k is equal to max(piles), Koko can eat one entire pile 
    per hour.

    Since we have a sorted range of numbers and the check is monotonic (if a speed
    k works, all larger/faster values will work too; if a speed k is too slow, all
    smaller/slower values won't work either), we can use binary search instead of
    brute force to make the algorithm more efficient. In binary search, we
    continuously find the mid point of the array and if we don't find what we're
    looking for, we eliminate half the elements by moving one of the two pointers. 
    
    So for this problem, for each mid point, we test if that's the k value we're 
    looking for. If that midpoint works, we don't automatically return that as k
     - we eliminate the upper half of the elements and we keep searching left because
    there may be a smaller k value that also works (we want the minimum k value). If
    k causes the duration to exceed the number of hours, Koko is eating too slow and
    we need to look for a faster speed.

    For this problem, we're using a variation of binary search to find the left
    most insertion point.

    Time: O(n + n log max(piles)) -> O(n log max(piles))
    - O(n) to find the largest value in the piles array
    - O(log max(piles)) for the binary search
    - O(n) to iterate through the piles array to check if the k value works

    Space: O(1) no additional data structures
    */
    class Solution {
        public int minEatingSpeed(int[] piles, int h) {
            int left = 1;
            int right = 0;
            for (int p : piles) {
                right = Math.max(right, p);
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(mid, piles, h)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(double k, int[] piles, int h) {
            int hours = 0;
            for (double bananas : piles) {
                hours += Math.ceil(bananas / k);
            }
            return hours <= h;
        }
    }

    private static String formatPiles(int[] piles) {
        if (piles.length <= 10) {
            return java.util.Arrays.toString(piles);
        }
        return "[" + piles[0] + ", " + piles[1] + ", ... (" + piles.length + " piles)]";
    }

    private static int[] filledPiles(int length, int value) {
        int[] piles = new int[length];
        java.util.Arrays.fill(piles, value);
        return piles;
    }

    public static void main(String[] args) {
        P_875_KokoEatingBananas outer = new P_875_KokoEatingBananas();
        Solution solver = outer.new Solution();

        // Test cases: {piles, h, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[] { 3, 6, 7, 11 }, 8, 4 },
                { new int[] { 30, 11, 23, 4, 20 }, 5, 30 },
                { new int[] { 30, 11, 23, 4, 20 }, 6, 23 },
                // Multiple speeds take exactly h hours (7, 8, 9, 10); must return the smallest,
                // not stop early
                { new int[] { 3, 6, 7, 11 }, 5, 7 },
                // h == piles.length: forced to finish each pile in 1 hour, so answer is
                // max(piles)
                { new int[] { 1, 2, 3, 4, 5 }, 5, 5 },
                // Lots of extra time: answer hits the lower bound of 1
                { new int[] { 1, 1, 1, 1 }, 4, 1 },
                { new int[] { 3, 6, 7, 11 }, 100, 1 },
                // Single pile: answer is ceil(pile / h)
                { new int[] { 1 }, 1, 1 },
                { new int[] { 10 }, 3, 4 },
                { new int[] { 9 }, 3, 3 },
                // Large single pile with h one less than the pile size: k = 1 is just too slow
                { new int[] { 312884470 }, 312884469, 2 },
                { new int[] { 1000000000 }, 2, 500000000 },
                // Sum of hours at small k exceeds int range (~2.4 * 10^9 at k = 1)
                { new int[] { 805306368, 805306368, 805306368 }, 1000000000, 3 },
                // Max constraints: 10^4 piles of 10^9; hours at k = 1 is 10^13
                { filledPiles(10000, 1000000000), 1000000000, 10000 },
                { filledPiles(10000, 1000000000), 10000, 1000000000 }
        };

        System.out.println("Running tests for P_875_KokoEatingBananas.minEatingSpeed\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] piles = (int[]) tests[i][0];
            int h = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.minEatingSpeed(piles, h);
            boolean ok = (expected == actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: piles=%s, h=%d => expected=%d, actual=%d => %s\n",
                    i + 1, formatPiles(piles), h, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minEatingSpeed: %d/%d tests passed\n", pass1, tests.length);
    }
}
