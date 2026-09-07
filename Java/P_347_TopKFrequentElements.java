import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class P_347_TopKFrequentElements {
    /*
    Hashmap with heap approach.

    For this question, the obvious approach is to use a hashmap to get each integer's frequency,
    and then sort the key-value pairs in descending order based on the value/frequency. But this
    gives a time complexity of O(n log n), which can be improved.

    Instead, after getting each integer's frequency by using a hashmap, we can use a min heap to
    get the time complexity down to O(n log k). This is guaranteed to be faster than O(n log n),
    or at least the just as efficient, because k <= n. Pushing and popping elements to and from
    a heap are O(log n) operations. We must use a min heap because if pushing a new element to 
    the heap causes the heap's size to exceed k, we need to pop the least frequent element -
    this allows us to retain the k most frequent elements.

    Time: O(n log k)
    - O(n) to iterate through the input array to get all frequencies 
    - O(log k) for pushing and popping elements to and from the min heap
    Space: O(n)
    - O(n) for the hashmap
    - O(k) for the min heap
    */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
        for (int key : map.keySet()) {
            heap.offer(key);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = heap.poll();
        }
        return res;
    }

    /*
    Hashmap with bucket sort approach.

    This is the most efficient approach: bucket sort solves this problem in O(n) linear time.

    After iterating through the input array and storing each integer's frequency in a hashmap,
    we create a 2D array. The outer array represents the frequencies, which must be between 1 
    and n (the size of the input array). The inner array at each frequency is the "bucket" 
    which holds all the integers with that frequency. So once we have each integer's frequency,
    we put each integer into the appropriate bucket.

    At the final step, we iterate backwards from the end of the 2D array (we want the k most
    frequent elements, and the highest frequencies are at the end).

    Time: O(n)
    Space: O(n)
    */
    public int[] v2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < nums.length + 1; i++) {
            buckets.add(new ArrayList<>());
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            buckets.get(entry.getValue()).add(entry.getKey());
        }

        int[] res = new int[k];
        int counter = 0;
        for (int i = nums.length; i > 0 && counter < k; i--) {
            for (int b : buckets.get(i)) {
                res[counter] = b;
                counter++;
                if (counter == k) {
                    break;
                }
            }
        }

        return res;
    }

    // The problem accepts any ordering, so results are compared as sorted copies.
    private static int[] sorted(int[] arr) {
        int[] copy = arr.clone();
        java.util.Arrays.sort(copy);
        return copy;
    }

    public static void main(String[] args) {
        P_347_TopKFrequentElements solver = new P_347_TopKFrequentElements();

        // Test cases: {nums, k, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 1, 1, 2, 2, 3 }, 2, new int[] { 1, 2 } },
                { new int[] { 1 }, 1, new int[] { 1 } },
                { new int[] { 1, 2 }, 2, new int[] { 1, 2 } },
                { new int[] { 5, 5, 5, 5, 5 }, 1, new int[] { 5 } },
                { new int[] { 4, 1, -1, 2, -1, 2, 3 }, 2, new int[] { -1, 2 } },
                { new int[] { -1, -1, -2, -2, -2, -3 }, 1, new int[] { -2 } },
                { new int[] { 3, 0, 1, 0 }, 1, new int[] { 0 } },
                { new int[] { 1, 1, 2, 2, 3, 3, 4 }, 3, new int[] { 1, 2, 3 } },
                { new int[] { 7, 7, 7, 8, 8, 9, 9, 9, 9, 10 }, 2, new int[] { 9, 7 } },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 10,
                        new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 } },
                { new int[] { 6, 6, 6, 6, 5, 5, 5, 4, 4, 3 }, 3, new int[] { 6, 5, 4 } },
                { new int[] { 100000, -100000, 100000 }, 1, new int[] { 100000 } }
        };

        System.out.println("Running tests for P_347_TopKFrequentElements.topKFrequent\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int[] expected = (int[]) tests[i][2];
            int[] actual = solver.topKFrequent(nums, k);

            boolean ok = java.util.Arrays.equals(sorted(expected), sorted(actual));
            if (ok)
                pass1++;
            System.out.printf("Test %d: nums=%s, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(nums), k, java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_347_TopKFrequentElements.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int[] expected = (int[]) tests[i][2];
            int[] actual = solver.v2(nums, k);

            boolean ok = java.util.Arrays.equals(sorted(expected), sorted(actual));
            if (ok)
                pass2++;
            System.out.printf("Test %d: nums=%s, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(nums), k, java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("topKFrequent: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
