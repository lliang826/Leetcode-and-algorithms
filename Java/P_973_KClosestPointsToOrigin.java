import java.util.PriorityQueue;

public class P_973_KClosestPointsToOrigin {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((int[] a, int[] b) -> {
            double x = Math.sqrt(a[0] * a[0] + a[1] * a[1]);
            double y = Math.sqrt(b[0] * b[0] + b[1] * b[1]);
            return Double.compare(y, x);
        });

        for (int[] p : points) {
            heap.offer(p);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            res[i] = heap.poll();
        }

        return res;
    }
}
