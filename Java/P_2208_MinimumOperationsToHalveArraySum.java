import java.util.Comparator;
import java.util.PriorityQueue;

public class P_2208_MinimumOperationsToHalveArraySum {
    public int halveArray(int[] nums) {
        double sum = 0;
        PriorityQueue<Double> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int n : nums) {
            sum += n;
            heap.offer((double) n);
        }

        double half = sum / 2;
        int ops = 0;
        while (sum > half) {
            double max = heap.poll();
            double maxHalf = max / 2;
            sum -= maxHalf;
            heap.offer(maxHalf);
            ops++;
        }

        return ops;
    }
}
