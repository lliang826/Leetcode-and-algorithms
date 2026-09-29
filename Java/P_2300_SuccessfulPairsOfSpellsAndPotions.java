import java.util.Arrays;

public class P_2300_SuccessfulPairsOfSpellsAndPotions {
    class Solution {
        public int[] successfulPairs(int[] spells, int[] potions, long success) {
            int n = spells.length;
            int m = potions.length;

            Arrays.sort(potions);
            int[] res = new int[n];

            for (int i = 0; i < n; i++) {
                double target = (double) success / (double) spells[i];
                int val = binarySearch(potions, target);
                res[i] = m - val;
            }

            return res;
        }

        private int binarySearch(int[] potions, double target) {
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
}
