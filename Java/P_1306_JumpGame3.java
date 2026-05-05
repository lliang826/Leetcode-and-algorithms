import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

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

    public boolean canReach2(int[] arr, int start) {
        int n = arr.length;
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> seen = new HashSet<>();

        queue.offer(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();

                if (arr[index] == 0) {
                    return true;
                }

                int jumpUp = index + arr[index];
                int jumpDown = index - arr[index];
                int[] jumps = new int[] { jumpUp, jumpDown };

                for (int jump : jumps) {
                    if (jump >= 0 && jump < n && !seen.contains(jump)) {
                        queue.offer(jump);
                        seen.add(jump);
                    }
                }
            }
        }

        return false;
    }
}
