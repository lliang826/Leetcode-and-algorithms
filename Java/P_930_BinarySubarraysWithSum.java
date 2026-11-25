import java.util.HashMap;
import java.util.Map;

public class P_930_BinarySubarraysWithSum {
    /*
    */
    public int numSubarraysWithSum(int[] nums, int goal) {
        int[] prefix = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            prefix[i] = sum;
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int count = 0;
        for (int p : prefix) {
            int complement = p - goal;
            if (map.containsKey(complement)) {
                count += map.get(complement);
            }
            map.put(p, map.getOrDefault(p, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        P_930_BinarySubarraysWithSum solver = new P_930_BinarySubarraysWithSum();

        // Test cases: {input array, goal, expected count}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 0, 1, 0, 1 }, 2, 4 },
                { new int[] { 0, 0, 0, 0, 0 }, 0, 15 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 0 }, 0, 1 },
                { new int[] { 1 }, 0, 0 },
                { new int[] { 0 }, 1, 0 },
                { new int[] { 1, 1, 1 }, 2, 2 },
                { new int[] { 1, 1, 1 }, 3, 1 },
                { new int[] { 1, 1, 1 }, 4, 0 },
                { new int[] { 1, 1 }, 0, 0 },
                { new int[] { 0, 1, 0 }, 1, 4 },
                { new int[] { 1, 0, 0, 1 }, 1, 6 },
                { new int[] { 1, 0, 1, 0, 1 }, 0, 2 },
                { new int[] { 0, 0, 1, 0, 0 }, 0, 6 },
                { new int[] { 1, 0, 1, 0, 1 }, 3, 1 }
        };

        System.out.println("Running tests for P_930_BinarySubarraysWithSum.numSubarraysWithSum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int goal = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.numSubarraysWithSum(input.clone(), goal);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, goal=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), goal, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("numSubarraysWithSum: %d/%d tests passed\n", pass, tests.length);
    }
}
