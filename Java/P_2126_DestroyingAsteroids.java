import java.util.Arrays;

public class P_2126_DestroyingAsteroids {
    /*
    Greedy algorithm approach.
    */
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        long total = mass;

        for (int i : asteroids) {
            if (total >= i) {
                total += i;
            } else {
                return false;
            }
        }

        return true;
    }

    private static int[] repeat(int value, int count) {
        int[] arr = new int[count];
        Arrays.fill(arr, value);
        return arr;
    }

    public static void main(String[] args) {
        P_2126_DestroyingAsteroids solver = new P_2126_DestroyingAsteroids();

        // Test cases: {mass, asteroids, expected}
        Object[][] tests = new Object[][] {
                { 10, new int[] { 3, 9, 19, 5, 21 }, true },
                { 5, new int[] { 4, 9, 23, 4 }, false },
                { 1, new int[] { 1 }, true },
                { 1, new int[] { 2 }, false },
                { 2, new int[] { 2 }, true },
                { 1, new int[] { 1, 1, 1, 1 }, true },
                { 1, new int[] { 3, 2 }, false },
                { 1, new int[] { 1, 2, 4, 8, 16 }, true },
                { 1, new int[] { 1, 2, 4, 8, 17 }, false },
                { 1000, new int[] { 1, 2, 3 }, true },
                { 100000, new int[] { 100000, 100000, 100000 }, true },
                // running mass exceeds Integer.MAX_VALUE, so the accumulator must be a long
                { 100000, repeat(100000, 30000), true },
        };

        System.out.println("Running tests for P_2126_DestroyingAsteroids.asteroidsDestroyed\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int mass = (int) tests[i][0];
            int[] asteroids = (int[]) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.asteroidsDestroyed(mass, asteroids.clone());

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            String shown = asteroids.length > 10
                    ? "[" + asteroids.length + " asteroids of " + asteroids[0] + "]"
                    : Arrays.toString(asteroids);
            System.out.printf("Test %d: mass=%d, asteroids=%s => expected=%b, actual=%b => %s\n",
                    i + 1, mass, shown, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("asteroidsDestroyed: %d/%d tests passed\n", pass1, tests.length);
    }
}
