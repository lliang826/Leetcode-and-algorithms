import java.util.ArrayDeque;
import java.util.Deque;

public class P_739_DailyTemperatures {
    /*
     * Monotonic stack approach. We maintain a stack of indices of the temperatures
     * array. For each temperature, we pop indices from the stack while the current
     * temperature is greater than the temperature at the index stored at the top of
     * the stack. For each popped index, we calculate the number of days until a
     * warmer temperature by subtracting the popped index from the current index.
     * Finally, we push the current index onto the stack. This way, the stack always
     * contains indices of temperatures in decreasing order.
     * 
     * Time: O(n), where n is the length of the temperatures array. Each index is pushed
     * and popped at most once.
     * Space: O(n) in the worst case, where the temperatures are in decreasing order,
     * so all indices are stored in the stack.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peekLast()]) {
                int index = stack.removeLast();
                ans[index] = i - index;
            }
            stack.addLast(i);
        }

        return ans;
    }

    public static void main(String[] args) {
        P_739_DailyTemperatures solver = new P_739_DailyTemperatures();

        // Test cases: [input temperatures[], expected output[]]
        int[][][] testCases = new int[][][] {
            // Test 1: Example from LeetCode
            {{73, 74, 75, 71, 69, 72, 76, 73}, {1, 1, 4, 2, 1, 1, 0, 0}},
            // Test 2: Another LeetCode example
            {{30, 40, 50, 60}, {1, 1, 1, 0}},
            // Test 3: Another LeetCode example
            {{30, 60, 90}, {1, 1, 0}},
            // Test 4: All decreasing temperatures
            {{100, 90, 80, 70, 60}, {0, 0, 0, 0, 0}},
            // Test 5: All increasing temperatures
            {{30, 40, 50, 60, 70}, {1, 1, 1, 1, 0}},
            // Test 6: Single element
            {{75}, {0}},
            // Test 7: Two elements - warmer
            {{30, 40}, {1, 0}},
            // Test 8: Two elements - colder
            {{40, 30}, {0, 0}},
            // Test 9: Mixed pattern
            {{55, 38, 53, 81, 61, 93, 97, 32, 43, 78}, {3, 1, 1, 2, 1, 1, 0, 1, 1, 0}},
            // Test 10: Plateau pattern
            {{70, 70, 70, 75}, {3, 2, 1, 0}}
        };

        System.out.println("Running tests for P_739_DailyTemperatures.dailyTemperatures\n");
        int pass = 0;
        int totalTests = 0;

        for (int i = 0; i < testCases.length; i++) {
            int[] temperatures = testCases[i][0];
            int[] expected = testCases[i][1];
            int[] actual = solver.dailyTemperatures(temperatures);

            boolean ok = java.util.Arrays.equals(expected, actual);
            totalTests++;
            if (ok) pass++;

            System.out.printf("Test %d:\n", i + 1);
            System.out.printf("  Input:    %s\n", java.util.Arrays.toString(temperatures));
            System.out.printf("  Expected: %s\n", java.util.Arrays.toString(expected));
            System.out.printf("  Actual:   %s\n", java.util.Arrays.toString(actual));
            System.out.printf("  Result:   %s\n\n", (ok ? "PASS" : "FAIL"));
        }

        System.out.println("=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("dailyTemperatures: %d/%d tests passed\n", pass, totalTests);
    }
}
