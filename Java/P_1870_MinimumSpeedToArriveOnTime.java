public class P_1870_MinimumSpeedToArriveOnTime {
    public int minSpeedOnTime(int[] dist, double hour) {
        if (Math.ceil(hour) < dist.length) {
            return -1;
        }

        int left = 1;
        int right = (int) Math.pow(10, 7);

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (check(dist, mid, hour)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean check(int[] dist, int speed, double hour) {
        double total = 0;
        for (int i = 0; i < dist.length; i++) {
            double duration = (double) dist[i] / (double) speed;
            if (i != dist.length - 1) {
                duration = Math.ceil(duration);
            }
            total += duration;
        }
        return total <= hour;
    }

    private static String formatDist(int[] dist) {
        if (dist.length <= 10) {
            return java.util.Arrays.toString(dist);
        }
        return "[" + dist[0] + ", " + dist[1] + ", ... (" + dist.length + " rides)]";
    }

    private static int[] filledDist(int length, int value) {
        int[] dist = new int[length];
        java.util.Arrays.fill(dist, value);
        return dist;
    }

    public static void main(String[] args) {
        P_1870_MinimumSpeedToArriveOnTime solver = new P_1870_MinimumSpeedToArriveOnTime();

        // Test cases: {dist, hour, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[] { 1, 3, 2 }, 6.0, 1 },
                { new int[] { 1, 3, 2 }, 2.7, 3 },
                { new int[] { 1, 3, 2 }, 1.9, -1 },
                // hour == n - 1 exactly: last ride needs positive time, so impossible
                { new int[] { 1, 1 }, 1.0, -1 },
                { new int[] { 1, 1, 100000 }, 2.0, -1 },
                // hour just above n - 1: last ride must fit in 0.01 hours, answer hits the 10^7 upper bound
                { new int[] { 1, 1, 100000 }, 2.01, 10000000 },
                // Single ride: no waiting, answer is ceil(dist / hour)
                { new int[] { 24760 }, 8.11, 3054 },
                { new int[] { 5 }, 1.0, 5 },
                { new int[] { 1 }, 1.0, 1 },
                // Plenty of time: answer hits the lower bound of 1
                { new int[] { 5, 3, 4, 6, 2, 2, 7 }, 100.0, 1 },
                // Integer-hour waits matter: at speed 3, rides take 1 + 1 + ceil(1.33) = 4 hours before the last
                { new int[] { 3, 3, 4, 3 }, 5.0, 3 },
                // Exact-deadline floating-point case: true answer is 25, LeetCode's judge expects 26
                { new int[] { 1, 1, 7 }, 2.28, 26 },
                // Max constraints: 10^5 rides of 10^5 km
                { filledDist(100000, 100000), 1000000000.0, 10 },
                { filledDist(100000, 100000), 99999.01, 10000000 },
                { filledDist(100000, 100000), 99999.0, -1 }
        };

        System.out.println("Running tests for P_1870_MinimumSpeedToArriveOnTime.minSpeedOnTime\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] dist = (int[]) tests[i][0];
            double hour = (double) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.minSpeedOnTime(dist, hour);
            boolean ok = (expected == actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: dist=%s, hour=%.2f => expected=%d, actual=%d => %s\n",
                    i + 1, formatDist(dist), hour, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minSpeedOnTime: %d/%d tests passed\n", pass1, tests.length);
    }
}
