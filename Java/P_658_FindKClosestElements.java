import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class P_658_FindKClosestElements {
    /*
    Max heap approaches for both solutions.

    To return the k closest integers to x in the array, we can use a heap. We need a max heap
    because that allows us to pop the farthest integers and only keep the k closest integers.
    The problem asks for the result to be sorted in ascending order, and the return type is
    defined to be a list object of type integer, so after we have the k closest integers in
    the maxHeap, we can pol all k integers from the maxHeap into an ArrayList and then sort it.

    The tricky part of this problem is implementing the custom comaprator. The first solution
    was my first attempt at it, but the second solution is much cleaner.

    Time: O(n log k + k log k + k log k) -> O(n log k)
    - O(n log k) iterating through all elements in the array and then popping if the size exceeds k
    - O(k log k) popping all elements from the maxHeap and storing them in the ArrayList
    - O(k log k) sorting the k elements in ascending order

    Space: O(k)
    - O(k) for the maxHeap
    - O(k) for the ArrayList
    */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> {
            if (Math.abs(x - a) < Math.abs(x - b)) {
                return 1;
            } else if (Math.abs(x - a) > Math.abs(x - b)) {
                return -1;
            } else {
                if (a < b) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (int a : arr) {
            heap.offer(a);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.add(heap.poll());
        }

        Collections.sort(res);
        return res;
    }

    public List<Integer> v2(int[] arr, int k, int x) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> {
            if (Math.abs(b - x) == Math.abs(a - x)) {
                return b - a;
            }
            return Math.abs(b - x) - Math.abs(a - x);
        });

        for (int i : arr) {
            maxHeap.offer(i);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        List<Integer> res = new ArrayList<>();
        while (maxHeap.size() > 0) {
            res.add(maxHeap.poll());
        }
        
        Collections.sort(res);
        return res;
    }

    public static void main(String[] args) {
        P_658_FindKClosestElements solver = new P_658_FindKClosestElements();

        // Test cases: {arr, k, x, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4, 5 }, 4, 3, List.of(1, 2, 3, 4) },
                { new int[] { 1, 2, 3, 4, 5 }, 4, -1, List.of(1, 2, 3, 4) },
                { new int[] { 1, 1, 2, 3, 4, 5 }, 4, -1, List.of(1, 1, 2, 3) },
                { new int[] { 1, 2, 3, 4, 5 }, 4, 10, List.of(2, 3, 4, 5) },
                { new int[] { 1 }, 1, 1, List.of(1) },
                { new int[] { 1, 3 }, 1, 2, List.of(1) },
                { new int[] { 1, 1, 1, 10, 10, 10 }, 1, 9, List.of(10) },
                { new int[] { 0, 0, 1, 2, 3, 3, 4, 7, 7, 8 }, 3, 5, List.of(3, 3, 4) },
                { new int[] { 1, 2, 3, 4, 5 }, 5, 3, List.of(1, 2, 3, 4, 5) },
                { new int[] { 1, 5, 10 }, 1, 4, List.of(5) },
                { new int[] { -5, -3, 0, 2, 4 }, 2, -4, List.of(-5, -3) },
                { new int[] { 1, 1, 1, 1 }, 2, 1, List.of(1, 1) },
                { new int[] { 1, 10, 15, 25, 35, 45, 50, 59 }, 1, 30, List.of(25) },
                { new int[] { 1, 2, 3, 4, 5 }, 2, 6, List.of(4, 5) },
        };

        System.out.println("Running tests for P_658_FindKClosestElements.findClosestElements\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int x = (int) tests[i][2];
            @SuppressWarnings("unchecked")
            List<Integer> expected = (List<Integer>) tests[i][3];
            List<Integer> actual = solver.findClosestElements(arr, k, x);

            boolean ok = expected.equals(actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: arr=%s, k=%d, x=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(arr), k, x, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_658_FindKClosestElements.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int x = (int) tests[i][2];
            @SuppressWarnings("unchecked")
            List<Integer> expected = (List<Integer>) tests[i][3];
            List<Integer> actual = solver.v2(arr, k, x);

            boolean ok = expected.equals(actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: arr=%s, k=%d, x=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(arr), k, x, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("findClosestElements: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
