import java.util.HashMap;
import java.util.Map;

public class P_2260_MinimumConsecutiveCardsToPickUp {
    public int minimumCardPickup(int[] cards) {
        Map<Integer, Integer> map = new HashMap<>();

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < cards.length; i++) {
            int curr = cards[i];
            if (map.containsKey(curr)) {
                min = Math.min(min, i - map.get(curr) + 1);
            }
            map.put(curr, i);
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static void main(String[] args) {
        P_2260_MinimumConsecutiveCardsToPickUp solver = new P_2260_MinimumConsecutiveCardsToPickUp();

        Object[][] tests = new Object[][] {
                { new int[] { 3, 4, 2, 3, 4, 7 }, 4 },
                { new int[] { 1, 0, 5, 3 }, -1 },
                { new int[] { 95, 11, 8, 65, 5, 86, 30, 27, 30, 73, 15, 91, 30, 7, 37, 26, 55, 76, 60, 43, 36, 85, 47, 96, 6 }, 3 },
                { new int[] { 1, 1 }, 2 },
                { new int[] { 1, 2, 3, 4, 5 }, -1 },
                { new int[] { 1, 2, 1, 2, 1 }, 3 },
                { new int[] { 5, 5, 5, 5, 5 }, 2 },
                { new int[] { 1, 2, 3, 1, 2, 3 }, 4 },
                { new int[] { 1, 2, 3, 4, 1 }, 5 },
                { new int[] { 1 }, -1 },
                { new int[] { 1, 2, 3, 2, 1 }, 3 },
                { new int[] { 7, 3, 7 }, 3 },
                { new int[] { 1, 2, 1, 3, 1 }, 3 },
                { new int[] { 10, 20, 30, 10, 20, 30 }, 4 },
                { new int[] { 1, 1, 1, 1 }, 2 }
        };

        System.out.println("Running tests for P_2260_MinimumConsecutiveCardsToPickUp.minimumCardPickup\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.minimumCardPickup(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minimumCardPickup: %d/%d tests passed\n", pass, tests.length);
    }
}
