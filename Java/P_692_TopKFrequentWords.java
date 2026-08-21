import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class P_692_TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> heap = new PriorityQueue<>((a, b) -> {
            if (map.get(a).equals(map.get(b))) {
                return b.compareTo(a);
            }
            return map.get(a) - map.get(b);
        });

        for (String s : map.keySet()) {
            heap.offer(s);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.addFirst(heap.poll());
        }

        return res;
    }
}
