import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class P_1481_LeastNumberOfUniqueIntegersAfterKRemovals {
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
}
