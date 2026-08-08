import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class P_1046_LastStoneWeight {
    public int lastStoneWeight(int[] stones) {
        List<Integer> list = new ArrayList<>();
        for (int i : stones) {
            list.add(i);
        }

        while (list.size() > 1) {
            Collections.sort(list, Collections.reverseOrder());
            int y = list.remove(0);
            int x = list.remove(0);
            int diff = y - x;
            if (diff > 0) {
                list.add(diff);
            }
        }

        return list.size() == 0 ? 0 : list.get(0);
    }

    public int v2(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int s : stones) {
            heap.offer(s);
        }

        while (heap.size() > 1) {
            int x = heap.poll();
            int y = heap.poll();
            int diff = x - y;
            if (diff > 0) {
                heap.offer(diff);
            }
        }

        return heap.size() == 0 ? 0 : heap.poll();
    }
}
