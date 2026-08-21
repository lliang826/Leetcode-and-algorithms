import java.util.PriorityQueue;

public class P_703_KthLargestElementInAStream {
    class KthLargest {
        private PriorityQueue<Integer> heap;
        int limit;

        public KthLargest(int k, int[] nums) {
            heap = new PriorityQueue<>();
            limit = k;

            for (int n : nums) {
                heap.offer(n);
                if (heap.size() > limit) {
                    heap.poll();
                }
            }
        }

        public int add(int val) {
            heap.offer(val);
            if (heap.size() > limit) {
                heap.poll();
            }
            return heap.peek();
        }
    }
}
