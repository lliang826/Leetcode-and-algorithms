import java.util.ArrayDeque;
import java.util.ArrayList;
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

    class StockSpanner2 {
        private Deque<Integer> deque;
        private ArrayList<Integer> list;
        int index;

        public StockSpanner2() {
            deque = new ArrayDeque<>();
            list = new ArrayList<>();
            index = 0;
        }

        public int next(int price) {
            list.add(price);

            while (!deque.isEmpty() && list.get(deque.peekLast()) <= price) {
                deque.removeLast();
            }

            int span = index - (deque.isEmpty() ? -1 : deque.peekLast());
            deque.addLast(index);
            this.index++;
            return span;
        }
    }

    class StockSpanner3 {
        private Deque<int[]> deque;

        public StockSpanner3() {
            deque = new ArrayDeque<>();
        }

        public int next(int price) {
            int count = 0;
            while (!deque.isEmpty() && deque.peekLast()[0] <= price) {
                count += deque.removeLast()[1];
            }

            int span = count + 1;
            deque.addLast(new int[] { price, span });
            return span;
        }
    }
}