public class P_933_NumberOfRecentCalls {
    class MovingAverage {
        private Queue<Integer> queue;
        private int windowSize;

        public MovingAverage(int size) {
            this.queue = new ArrayDeque<>();
            this.windowSize = size;
        }
        
        public double next(int val) {
            this.queue.offer(val);
            while (this.queue.size() > this.windowSize) {
                this.queue.poll();
            }
            
            int start = this.queue.poll();
            this.queue.offer(start);
            double sum = start;
            while (this.queue.peek() != start) {
                int num = this.queue.poll();
                sum += num;
                this.queue.offer(num);
            }
                
            return sum / this.queue.size();
        }
    }
}