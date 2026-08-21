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

        // Min-heap ordered "worst first": lowest count, then lexicographically largest.
        PriorityQueue<String> heap = new PriorityQueue<>((a, b) -> {
            int countA = map.get(a);
            int countB = map.get(b);
            if (countA != countB) {
                return countA - countB;
            }
            return b.compareTo(a);
        });

        for (String word : map.keySet()) {
            heap.offer(word);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.add(heap.poll());
        }
        return res;
    }
}
