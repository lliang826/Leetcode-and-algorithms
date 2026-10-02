public class P_875_KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int h) {
        int maxValue = 0;
        for (int p : piles) {
            maxValue = Math.max(maxValue, p);
        }

        int left = 1;
        int right = maxValue;

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            int hours = getHoursFromK(mid, piles);
            if (hours <= h) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private int getHoursFromK(double k, int[] piles) {
        int hours = 0;
        for (double p : piles) {
            hours += Math.ceil(p / k);
        }
        return hours;
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
        P_875_KokoEatingBananas solver = new P_875_KokoEatingBananas();

        // Test cases: {piles, h, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[] { 3, 6, 7, 11 }, 8, 4 },
                { new int[] { 30, 11, 23, 4, 20 }, 5, 30 },
                { new int[] { 30, 11, 23, 4, 20 }, 6, 23 },
                // Multiple speeds take exactly h hours (7, 8, 9, 10); must return the smallest, not stop early
                { new int[] { 3, 6, 7, 11 }, 5, 7 },
                // h == piles.length: forced to finish each pile in 1 hour, so answer is max(piles)
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
