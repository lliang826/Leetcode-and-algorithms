import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class P_901_OnlineStockSpan {
    class StockSpanner {
        private Deque<Integer> deque;
        private Map<Integer, Integer> map;
        int index;

        public StockSpanner() {
            deque = new ArrayDeque<>();
            map = new HashMap<>();
            index = 0;
        }

        public int next(int price) {
            int count = 0;
            while (!deque.isEmpty() && deque.peekLast() <= price) {
                deque.removeLast();
                count++;
            }

            int span = index - map.getOrDefault(deque.peekLast(), -1) + count;
            map.put(price, index);
            deque.addLast(price);
            this.index++;
            return span;
        }
    }
}