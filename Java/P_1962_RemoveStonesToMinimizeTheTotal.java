import java.util.Comparator;
import java.util.PriorityQueue;

public class P_1962_RemoveStonesToMinimizeTheTotal {
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        int sum = 0;
        for (int p : piles) {
            heap.offer(p);
            sum += p;
        }

        while (k > 0) {
            int i = heap.poll();
            int diff = i / 2;
            sum -= diff;
            heap.offer(i - diff);
            k--;
        }

        return sum;
    }
}
