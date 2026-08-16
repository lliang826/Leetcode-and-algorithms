import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class P_658_FindKClosestElements {
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
}
