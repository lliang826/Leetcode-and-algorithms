import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class P_502_IPO {
    /*
    Greedy algorithm approach.

    [0,1]
    [1,2]
    [1,3]

    [1,1]
    [1,2]
    [2,3]
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int[][] projects = new int[profits.length][2];
        for (int i = 0; i < profits.length; i++) {
            projects[i] = new int[] { capital[i], profits[i] };
        }

        Arrays.sort(projects, (a, b) -> {
            return a[0] - b[0];
        });

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        int j = 0;
        for (int i = 0; i < k; i++) {
            while (j < profits.length && projects[j][0] <= w) {
                maxHeap.offer(projects[j][1]);
                j++;
            }

            if (maxHeap.isEmpty()) {
                return w;
            }

            w += maxHeap.poll();
        }

        return w;
    }
}
