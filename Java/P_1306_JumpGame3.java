import java.util.ArrayDeque;
import java.util.Queue;

public class P_1306_JumpGame3 {
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] seen = new boolean[n];

        queue.offer(start);
        seen[start] = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                int indexNode = queue.poll();
                if (arr[indexNode] == 0) {
                    return true;
                }

                int jumpUp = indexNode + arr[indexNode];
                if (jumpUp < n && !seen[jumpUp]) {
                    queue.offer(jumpUp);
                    seen[jumpUp] = true;
                }

                int jumpDown = indexNode - arr[indexNode];
                if (jumpDown >= 0 && !seen[jumpDown]) {
                    queue.offer(jumpDown);
                    seen[jumpDown] = true;
                }
            }
        }

        return false;
    }
}
