import java.util.Arrays;

public class P_2300_SuccessfulPairsOfSpellsAndPotions {
    /*
    Binary search approach.

    A brute force solution would involve a nested for loop where we iterate through
    all the elements in both the spells and potions arrays, resulting in a time
    complexity of O(n * m). But we can do better: instead, after sorting the potions
    array, we can use binary search to find the insertion index, and all elements
    in the potions array from that index onwards will be successful.

    For each spell, we have to calculate the minimum strength threshold for a potion
    to be successful: threshold = success / spell. This threshold becomes our target;
    we are looking for the leftmost insertion index in the sorted potions array (must
    be leftmost in case of duplicate potion strengths).

    The leftmost insertion index variation of binary search will give us what we need.
    The difference between m (the number of potions) and this index will give us the
    number of successful potions for each spell.

    Time: O((m + n) log m)
    - O(m log m) for sorting the potions array
    - O(n log m) for performing binary search on the sorted potions array for each
    element in the spells array

    Space: O(n)
    - O(1) for sorting the potions array in place
    - O(n) for holding the results array
    */
    class Solution {
        public int[] successfulPairs(int[] spells, int[] potions, long success) {
            int n = spells.length;
            int m = potions.length;

            Arrays.sort(potions);
            int[] res = new int[n];

            for (int i = 0; i < n; i++) {
                double threshold = (double) success / (double) spells[i];
                int index = binarySearchLeftMostInsertionPoint(potions, threshold);
                res[i] = m - index;
            }

            return res;
        }

        private int binarySearchLeftMostInsertionPoint(int[] potions, double target) {
            int left = 0;
            int right = potions.length;

            while (left < right) {
                int mid = (right - left) / 2 + left;
                if (target <= potions[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }
    }

    public static void main(String[] args) {
        P_2300_SuccessfulPairsOfSpellsAndPotions outer = new P_2300_SuccessfulPairsOfSpellsAndPotions();

        // Test cases: {spells, potions, success, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[] { 5, 1, 3 }, new int[] { 1, 2, 3, 4, 5 }, 7L, new int[] { 4, 0, 3 } },
                { new int[] { 3, 1, 2 }, new int[] { 8, 5, 8 }, 16L, new int[] { 2, 0, 2 } },
                // Non-integer threshold (7 / 2 = 3.5): potion 3 must NOT count
                { new int[] { 2 }, new int[] { 3 }, 7L, new int[] { 0 } },
                { new int[] { 2 }, new int[] { 3, 4 }, 7L, new int[] { 1 } },
                // Duplicates equal to the threshold: must find the leftmost one
                { new int[] { 1 }, new int[] { 5, 5, 5 }, 5L, new int[] { 3 } },
                { new int[] { 2 }, new int[] { 1, 3, 3, 3, 5 }, 6L, new int[] { 4 } },
                // Exact product boundary (4 * 3 = 12)
                { new int[] { 4 }, new int[] { 1, 2, 3 }, 12L, new int[] { 1 } },
                // No potion succeeds
                { new int[] { 1, 2 }, new int[] { 1, 1, 1 }, 100L, new int[] { 0, 0 } },
                // Every potion succeeds
                { new int[] { 10, 20 }, new int[] { 5, 6, 7 }, 1L, new int[] { 3, 3 } },
                // Unsorted potions input
                { new int[] { 2 }, new int[] { 9, 1, 5, 3, 7 }, 10L, new int[] { 3 } },
                // Max values: products up to 10^10 exceed int range
                { new int[] { 100000 }, new int[] { 100000, 99999 }, 10000000000L, new int[] { 1 } },
                { new int[] { 100000, 1 }, new int[] { 100000 }, 10000000000L, new int[] { 1, 0 } },
                // Smallest input
                { new int[] { 1 }, new int[] { 1 }, 1L, new int[] { 1 } }
        };

        System.out.println("Running tests for P_2300_SuccessfulPairsOfSpellsAndPotions.successfulPairs\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] spells = (int[]) tests[i][0];
            int[] potions = (int[]) tests[i][1];
            long success = (long) tests[i][2];
            int[] expected = (int[]) tests[i][3];

            Solution solver = outer.new Solution();
            // Clone potions since the solution sorts it in place
            int[] actual = solver.successfulPairs(spells, potions.clone(), success);

            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: spells=%s, potions=%s, success=%d => expected=%s, actual=%s => %s\n",
                    i + 1, Arrays.toString(spells), Arrays.toString(potions), success,
                    Arrays.toString(expected), Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("successfulPairs: %d/%d tests passed\n", pass, tests.length);
    }
}
