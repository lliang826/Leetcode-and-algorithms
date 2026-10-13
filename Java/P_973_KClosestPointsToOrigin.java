import java.util.PriorityQueue;

public class P_973_KClosestPointsToOrigin {
    /*
    Max heap approach.

    To find the k closest points to the origin, we can use a maxHeap to hold the k
    closest points and pop the points that are farther away.

    Time: O(n log k + k log k) -> O(n log k) because n >= k
    - O(n log k) to iterate through all points in the array and pop the farthest points
    - O(k log k) to pop all elements from the maxHeap and store them in the new 2D array

    Space: O(k)
    - O(k) for the maxHeap PriorityQueue
    - O(k) for the new 2D array
    */
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((int[] a, int[] b) -> {
            double distA = Math.sqrt(a[0] * a[0] + a[1] * a[1]);
            double distB = Math.sqrt(b[0] * b[0] + b[1] * b[1]);
            return Double.compare(distB, distA);
        });

        for (int[] p : points) {
            maxHeap.offer(p);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            res[i] = maxHeap.poll();
        }

        return res;
    }

    // The answer may be returned in any order, so sort points by (x, y) before comparing
    private static int[][] sortPoints(int[][] points) {
        int[][] copy = new int[points.length][];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i].clone();
        }
        java.util.Arrays.sort(copy, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        return copy;
    }

    public static void main(String[] args) {
        P_973_KClosestPointsToOrigin solver = new P_973_KClosestPointsToOrigin();

        // Test cases: {points, k, expected}
        Object[][] tests = new Object[][] {
                // LeetCode example 1
                { new int[][] { { 1, 3 }, { -2, 2 } }, 1, new int[][] { { -2, 2 } } },
                // LeetCode example 2
                { new int[][] { { 3, 3 }, { 5, -1 }, { -2, 4 } }, 2, new int[][] { { 3, 3 }, { -2, 4 } } },
                // Single point
                { new int[][] { { 7, -7 } }, 1, new int[][] { { 7, -7 } } },
                // k == n: every point is returned
                { new int[][] { { 1, 2 }, { -3, 4 }, { 0, -5 } }, 3, new int[][] { { 1, 2 }, { -3, 4 }, { 0, -5 } } },
                // Origin itself is the closest point
                { new int[][] { { 2, 2 }, { 0, 0 }, { -1, 1 } }, 1, new int[][] { { 0, 0 } } },
                // All negative coordinates
                { new int[][] { { -5, -5 }, { -1, -2 }, { -3, -3 } }, 2, new int[][] { { -1, -2 }, { -3, -3 } } },
                // Equal-distance points both included
                { new int[][] { { 1, 0 }, { 0, 1 }, { 5, 5 } }, 2, new int[][] { { 1, 0 }, { 0, 1 } } },
                // Duplicate points
                { new int[][] { { 1, 1 }, { 3, 3 }, { 1, 1 } }, 2, new int[][] { { 1, 1 }, { 1, 1 } } },
                // Boundary coordinates (-10^4 <= x, y <= 10^4)
                { new int[][] { { 10000, 10000 }, { -10000, -10000 }, { 9999, -10000 } }, 1, new int[][] { { 9999, -10000 } } },
                // Closest points appear last in the input
                { new int[][] { { 9, 9 }, { 8, 8 }, { 7, 7 }, { 1, 0 }, { 0, -1 } }, 2, new int[][] { { 1, 0 }, { 0, -1 } } }
        };

        System.out.println("Running tests for P_973_KClosestPointsToOrigin.kClosest\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] points = (int[][]) tests[i][0];
            int k = (int) tests[i][1];
            int[][] expected = (int[][]) tests[i][2];
            String input = java.util.Arrays.deepToString(points);

            int[][] actual = solver.kClosest(points, k);

            boolean ok = java.util.Arrays.deepEquals(sortPoints(expected), sortPoints(actual));
            if (ok)
                pass++;
            System.out.printf("Test %d: points=%s, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, input, k, java.util.Arrays.deepToString(expected),
                    java.util.Arrays.deepToString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("kClosest: %d/%d tests passed\n", pass, tests.length);
    }
}
