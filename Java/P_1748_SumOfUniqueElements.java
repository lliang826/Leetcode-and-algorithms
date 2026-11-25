import java.util.HashMap;
import java.util.Map;

public class P_1748_SumOfUniqueElements {
    /*
    */
    public int sumOfUnique(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                sum += entry.getKey();
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        P_1748_SumOfUniqueElements solver = new P_1748_SumOfUniqueElements();

        // Test cases: {input array, expected sum}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 2 }, 4 },
                { new int[] { 1, 1, 1, 1, 1 }, 0 },
                { new int[] { 1, 2, 3, 4, 5 }, 15 },
                { new int[] { 1 }, 1 },
                { new int[] { 100 }, 100 },
                { new int[] { 1, 2, 1, 1 }, 2 },
                { new int[] { 1, 1, 2, 2, 3, 3 }, 0 },
                { new int[] { 5, 5, 5, 7 }, 7 },
                { new int[] { 10, 20, 10, 30, 40, 40 }, 50 },
                { new int[] { 100, 99, 100, 98 }, 197 },
                { new int[] { 3, 1, 3, 1, 3, 2 }, 2 },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 55 }
        };

        System.out.println("Running tests for P_1748_SumOfUniqueElements.sumOfUnique\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.sumOfUnique(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("sumOfUnique: %d/%d tests passed\n", pass, tests.length);
    }
}
