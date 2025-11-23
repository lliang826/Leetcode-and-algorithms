import java.util.HashMap;
import java.util.Map;

public class P_1426_CountingElements {
    public int countElements(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int count = 0;
        for (int num : map.keySet()) {
            if (map.containsKey(num + 1)) {
                count += map.get(num);
            }
        }

        return count;
    }

    public static void main(String[] args) {
        P_1426_CountingElements solver = new P_1426_CountingElements();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 2, 3}, 2 },
                { new int[]{1, 1, 3, 3, 5, 5, 7, 7}, 0 },
                { new int[]{1, 3, 2, 3, 5, 0}, 3 },
                { new int[]{1, 1, 2, 2}, 2 },
                { new int[]{1, 2, 3, 4, 5}, 4 },
                { new int[]{5, 4, 3, 2, 1}, 4 },
                { new int[]{1}, 0 },
                { new int[]{1, 2}, 1 },
                { new int[]{2, 1}, 1 },
                { new int[]{1, 1, 1, 2}, 3 },
                { new int[]{1, 2, 2, 2, 3}, 4 },
                { new int[]{10, 20, 30}, 0 },
                { new int[]{0, 1, 2, 3}, 3 },
                { new int[]{-1, 0, 1, 2}, 3 },
                { new int[]{1, 1, 2, 3, 3, 4}, 5 }
        };

        System.out.println("Running tests for P_1426_CountingElements.countElements\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.countElements(input.clone());
            
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
        System.out.printf("countElements: %d/%d tests passed\n", pass, tests.length);
    }
}
