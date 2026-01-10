import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class P_933_NumberOfRecentCalls {
    class RecentCounter {
        Deque<Integer> queue;

        public RecentCounter() {
            this.queue = new ArrayDeque<>();
        }
        
        public int ping(int t) {
            this.queue.addLast(t);
            int first = this.queue.peek();
            while (first < t - 3000 || first > t) {
                this.queue.pop();
                first = this.queue.peek();
            }
            return this.queue.size();
        }
    }

    class RecentCounter2 {
        private Queue<Integer> queue;

        public RecentCounter2() {
            this.queue = new ArrayDeque<>();
        }
        
        public int ping(int t) {
            this.queue.offer(t);
            while (this.queue.peek() < t - 3000) {
                this.queue.poll();
            }
            return this.queue.size();
        }
    }
}