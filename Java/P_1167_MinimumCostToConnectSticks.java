import java.util.PriorityQueue;

public class P_1167_MinimumCostToConnectSticks {
    public int connectSticks(int[] sticks) {
        if (sticks.length == 1) {
            return 0;
        }

        int total = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int s : sticks) {
            heap.offer(s);
        }

        while (heap.size() > 1) {
            int x = heap.poll();
            int y = heap.poll();
            int sum = x + y;
            total += sum;
            heap.offer(sum);
        }

        return total;
    }
}
