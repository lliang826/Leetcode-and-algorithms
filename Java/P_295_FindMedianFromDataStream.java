import java.util.Comparator;
import java.util.PriorityQueue;

public class P_295_FindMedianFromDataStream {
    class MedianFinder {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;

        public MedianFinder() {
            this.maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            this.minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            this.maxHeap.offer(num);
            if (this.minHeap.size() > 0 && this.maxHeap.peek() > this.minHeap.peek()) {
                int i = this.maxHeap.poll();
                this.minHeap.offer(i);
            }
            if (this.maxHeap.size() - this.minHeap.size() > 1) {
                int i = this.maxHeap.poll();
                this.minHeap.offer(i);
            }
            if (this.maxHeap.size() - this.minHeap.size() < -1) {
                int i = this.minHeap.poll();
                this.maxHeap.offer(i);
            }
        }

        public double findMedian() {
            if (this.maxHeap.size() == this.minHeap.size()) {
                int x = maxHeap.peek();
                int y = minHeap.peek();
                return (double) (x + y) / 2;
            } else if (this.maxHeap.size() > this.minHeap.size()) {
                return this.maxHeap.peek();
            } else {
                return this.minHeap.peek();
            }
        }
    }
}
