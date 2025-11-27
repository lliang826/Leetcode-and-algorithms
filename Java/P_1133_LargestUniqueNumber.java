import java.util.HashMap;
import java.util.Map;

public class P_1133_LargestUniqueNumber {
    /*
    Hashmap approach. Iterate through all the integers in the input array and create a frequency map.
    Then, iterate through the keys of the map (all the unique integers) and find the largest key that
    only appears once (freq == 1).
    
    Time: O(n + k) => O(n), where n is the number of elements in the input array and k is the number 
        of unique elements, k <= n. We iterate through all elements to create the frequency map, then
        we iterate through the keys to find the largest key that appears once.
    Space: O(n), all elements could be added to the frequency map 
    */
    public int largestUniqueNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int max = -1;
        for (int num : map.keySet()) {
            if (map.get(num) == 1) {
                max = Math.max(max, num);
            }
        }

        return max;
    }
    
    public static void main(String[] args) {
        P_1133_LargestUniqueNumber solver = new P_1133_LargestUniqueNumber();

        Object[][] tests = new Object[][] {
                { new int[] { 5, 7, 3, 9, 4, 9, 8, 3, 1 }, 8 },
                { new int[] { 9, 9, 8, 8 }, -1 },
                { new int[] { 1 }, 1 },
                { new int[] { 1, 1 }, -1 },
                { new int[] { 1, 2, 3, 4, 5 }, 5 },
                { new int[] { 5, 4, 3, 2, 1 }, 5 },
                { new int[] { 1, 1, 2, 2, 3, 3 }, -1 },
                { new int[] { 10, 10, 10, 5 }, 5 },
                { new int[] { 1, 2, 2, 3, 3, 3 }, 1 },
                { new int[] { 100, 200, 300, 100, 200 }, 300 },
                { new int[] { 0 }, 0 },
                { new int[] { 0, 0, 1 }, 1 },
                { new int[] { 1, 2, 3, 1, 2, 3 }, -1 },
                { new int[] { 7, 7, 7, 8, 8, 9 }, 9 },
                { new int[] { 1000, 999, 1000 }, 999 }
        };

        System.out.println("Running tests for P_1133_LargestUniqueNumber.largestUniqueNumber\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.largestUniqueNumber(input.clone());

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
        System.out.printf("largestUniqueNumber: %d/%d tests passed\n", pass, tests.length);
    }
}
