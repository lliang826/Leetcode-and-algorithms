import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class P_1338_ReduceArraySizeToTheHalf {
    /*
    Greedy approach.

    Since we are asked to find the MINIMUM size of a set of integers, where removing all 
    occurences of the integers removes at least half of the integers in the array, we 
    shoud use a greedy approach: remove the most frequent integers first.

    First, we use a hashmap to count the frequency of each integer in the array. Then, we
    can use a maxHeap to find the integers with the highest frequencies. While the number
    of integers removed are still less than half the size of the array, we keep popping
    from the maxHeap and counting.

    Time: O(n log n)
    - O(n) to get the frequency counter of all integers
    - O(n log n) to push all distinct integers into the maxHeap
    - O(n) to find the final count result

    Space: O(n)
    - O(n) for hashmap frequency counter
    - O(n) for maxHeap
    */
    public int minSetSize(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a, b) -> {
            return b.getValue() - a.getValue();
        });

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            heap.offer(entry);
        }

        int size = 0;
        int count = 0;
        while (size < arr.length / 2) {
            Map.Entry<Integer, Integer> entry = heap.poll();
            size += entry.getValue();
            count++;
        }

        return count;
    }

    /*
    Greedy approach, similar to the solution above.

    Same time and space complexities, but simpler and easier to implement; less chance
    of bugs.

    But instead of using a maxHeap, which is more complicated, verbose, and has a higher
    chance of bugs, we can just sort the frequencies themselves. We only care about the
    frequencies, the keys themselves are not used.

    Time: O(n log n)
    - O(n) to get the frequency counter of all integers
    - O(n log n) to sort the frequencies
    - O(n) to find the final count result

    Space: O(n)
    - O(n) for hashmap frequency counter
    - O(n) for creating a new ArrayList for the frequencies
    - O(n) for sorting
    */
    public int v2(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        List<Integer> values = new ArrayList<>(map.values());
        Collections.sort(values, (a, b) -> b - a);

        int size = 0;
        int count = 0;
        for (int i : values) {
            size += i;
            count++;
            if (size >= arr.length / 2) {
                return count;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        P_1338_ReduceArraySizeToTheHalf solver = new P_1338_ReduceArraySizeToTheHalf();

        // Test cases: {arr, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 3, 3, 3, 3, 5, 5, 5, 2, 2, 7 }, 2 },
                { new int[] { 7, 7, 7, 7, 7, 7 }, 1 },
                { new int[] { 1, 9 }, 1 },
                { new int[] { 1000, 1000, 3, 7 }, 1 },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 5 },
                { new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 1 },
                { new int[] { 1, 1, 2, 2, 3, 3, 4, 4 }, 2 },
                { new int[] { 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 6 }, 2 },
                { new int[] { 5, 5, 5, 5, 4, 4, 4, 3, 3, 2 }, 2 },
                { new int[] { 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3 }, 1 },
                { new int[] { 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2 }, 4 },
                { new int[] { 100000, 100000, 100000, 1, 2, 3 }, 1 }
        };

        System.out.println("Running tests for P_1338_ReduceArraySizeToTheHalf.minSetSize\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.minSetSize(arr);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: arr=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(arr), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1338_ReduceArraySizeToTheHalf.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v2(arr);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: arr=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(arr), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minSetSize: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
