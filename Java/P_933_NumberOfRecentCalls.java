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
}