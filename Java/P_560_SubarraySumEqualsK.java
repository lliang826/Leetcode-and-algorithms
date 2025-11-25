import java.util.HashMap;
import java.util.Map;

public class P_560_SubarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int total = 0;
        int sum = 0;
        for (int num : nums) {
            sum += num;
            if (map.containsKey(sum - k)) {
                total += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return total;
    }

    public static void main(String[] args) {
        P_560_SubarraySumEqualsK solver = new P_560_SubarraySumEqualsK();

        // Test cases: {input array, k, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 1, 1}, 2, 2 },
                { new int[]{1, 2, 3}, 3, 2 },
                { new int[]{1}, 0, 0 },
                { new int[]{-1, -1, 1}, 0, 1 },
                { new int[]{1, -1, 0}, 0, 3 },
                { new int[]{1, 2, 1, 2, 1}, 3, 4 },
                { new int[]{1}, 1, 1 },
                { new int[]{1, 2, 3}, 6, 1 },
                { new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7, 4 },
                { new int[]{0, 0, 0, 0, 0}, 0, 15 },
                { new int[]{-1, 1, -1, 1}, 0, 4 },
                { new int[]{1, -1, 1, -1, 1}, 0, 6 },
                { new int[]{5, -5, 5, -5}, 0, 4 },
                { new int[]{10}, 10, 1 },
                { new int[]{1, 2, 3, 4, 5}, 15, 1 }
        };

        System.out.println("Running tests for P_560_SubarraySumEqualsK.subarraySum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.subarraySum(input.clone(), k);
            
            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("subarraySum: %d/%d tests passed\n", pass, tests.length);
    }
}
