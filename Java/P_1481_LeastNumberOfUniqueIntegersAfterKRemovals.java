import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class P_1481_LeastNumberOfUniqueIntegersAfterKRemovals {
    /*
    Greedy algorithm approach.

    Hashmap to count frequencies and minHeap to remove the k least frequent integers. 
    The answer is the size of the minHeap after removing the k least frequent integers.
    But there is a small catch for some edge cases: if we remove too many integers and
    k becomes a negative value, we have to add the last integer popped back to the
    minHeap.

    Time: O(n log n)
    - O(n) to count all frequencies in the input array
    - O(n log n) to put all integers into the minHeap based on frequency
    - O(k log n) to remove k elements from the minHeap

    Space: O(n)
    - O(n) for the hashmap
    - O(n) for the minHeap
    */
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : arr) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> {
            return map.get(a) - map.get(b);
        });

        for (int i : map.keySet()) {
            minHeap.offer(i);
        }

        int lastKey = -1;
        while (k > 0) {
            lastKey = minHeap.poll();
            k -= map.get(lastKey);
        }

        if (k < 0) {
            minHeap.offer(lastKey);
        }

        return minHeap.size();
    }

    public static void main(String[] args) {
        P_1481_LeastNumberOfUniqueIntegersAfterKRemovals solver = new P_1481_LeastNumberOfUniqueIntegersAfterKRemovals();

        // Test cases: {input array, k, expected count of remaining unique integers}
        Object[][] tests = new Object[][] {
                { new int[] { 5, 5, 4 }, 1, 1 },
                { new int[] { 4, 3, 1, 1, 3, 3, 2 }, 3, 2 },
                { new int[] { 1 }, 0, 1 },
                { new int[] { 1 }, 1, 0 },
                { new int[] { 1, 2, 3, 4, 5 }, 0, 5 },
                { new int[] { 1, 2, 3, 4, 5 }, 5, 0 },
                { new int[] { 1, 1, 1, 1 }, 2, 1 },
                { new int[] { 1, 1, 1, 1 }, 4, 0 },
                { new int[] { 1, 1, 2, 2 }, 1, 2 },
                { new int[] { 2, 1, 1, 3, 3, 3 }, 3, 1 },
                { new int[] { 5, 5, 4 }, 3, 0 },
                { new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5 }, 3, 3 }
        };

        System.out.println("Running tests for P_1481_LeastNumberOfUniqueIntegersAfterKRemovals.findLeastNumOfUniqueInts\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.findLeastNumOfUniqueInts(arr, k);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: arr=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(arr), k, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("findLeastNumOfUniqueInts: %d/%d tests passed\n", pass, tests.length);
    }
}
