public class P_2187_MinimumTimeToCompleteTrips {
    class Solution {
        public long minimumTime(int[] time, int totalTrips) {
            long left = Integer.MAX_VALUE;
            for (int t : time) {
                left = Math.min(left, (long) t);
            }
            long right = left * totalTrips;

            while (left <= right) {
                long mid = (right - left) / 2 + left;
                if (check(time, totalTrips, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(int[] time, int totalTrips, long minTime) {
            long sum = 0;
            for (int t : time) {
                sum += minTime / (long) t;
            }
            return sum >= totalTrips;
        }
    }

    private static String formatTime(int[] time) {
        if (time.length <= 10) {
            return java.util.Arrays.toString(time);
        }
        return "[" + time[0] + ", " + time[1] + ", ... (" + time.length + " buses)]";
    }

    private static int[] filledTime(int length, int value) {
        int[] time = new int[length];
        java.util.Arrays.fill(time, value);
        return time;
    }

    public static void main(String[] args) {
        P_2187_MinimumTimeToCompleteTrips outer = new P_2187_MinimumTimeToCompleteTrips();
        Solution solver = outer.new Solution();

        // Test cases: {time, totalTrips, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[] { 1, 2, 3 }, 5, 3L },
                { new int[] { 2 }, 1, 2L },
                // totalTrips = 1: answer hits the lower bound, min(time)
                { new int[] { 1, 2, 3 }, 1, 1L },
                // Single bus: answer is time[0] * totalTrips
                { new int[] { 5 }, 4, 20L },
                // All buses identical: each does totalTrips / n trips
                { new int[] { 3, 3, 3 }, 6, 6L },
                // Fastest bus is not first in the array
                { new int[] { 10, 3, 7 }, 4, 9L },
                // Slow buses barely help: t = 24 gives 8 trips, t = 25 gives exactly 9
                { new int[] { 5, 10, 10 }, 9, 25L },
                // Answer equals the right bound: the slow bus never finishes a trip in time
                { new int[] { 2, 1000 }, 10, 20L },
                // Slow bus contributes exactly one trip right at the answer
                { new int[] { 1, 10000000 }, 10000000, 10000000L },
                // Single slow bus at max constraints: answer 10^14 needs long
                { new int[] { 10000000 }, 10000000, 100000000000000L },
                // Max constraints: old bound sum * totalTrips = 10^19 would overflow long
                { filledTime(100000, 10000000), 10000000, 1000000000L },
                // Many fast buses: 10^5 buses each doing t trips
                { filledTime(100000, 1), 10000000, 100L }
        };

        System.out.println("Running tests for P_2187_MinimumTimeToCompleteTrips.minimumTime\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] time = (int[]) tests[i][0];
            int totalTrips = (int) tests[i][1];
            long expected = (long) tests[i][2];
            long actual = solver.minimumTime(time, totalTrips);
            boolean ok = (expected == actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: time=%s, totalTrips=%d => expected=%d, actual=%d => %s\n",
                    i + 1, formatTime(time), totalTrips, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minimumTime: %d/%d tests passed\n", pass1, tests.length);
    }
}
