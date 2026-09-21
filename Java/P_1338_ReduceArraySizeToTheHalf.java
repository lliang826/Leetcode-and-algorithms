import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class P_1338_ReduceArraySizeToTheHalf {
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
}
