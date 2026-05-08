import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/*
Implicit graph problem.
 
For this problem, we have a start state (the input array), a goal state (any
index with a value of 0), and rules that generate neighbors (jump up to i +
arr[i] or jump down to i - arr[i]).
 
We can use either DFS or BFS for this problem; both approaches have the same
time and space complexity.     

Time: O(nodes + edges) => O(n + n * 2) => O(3n) => O(n)
Space: O(n) for the seen hashset/boolean array and the queue/stack

I kept the queue level processing out of habit - I always include it when I use 
a BFS approach. For this problem though, it doesn't ask for the shortest path or
anything like that, so it's not needed. Can remove the levelSize variable and the for loop.

Since recursive DFS uses the stack (iterative Stack/Deque data structure uses heap), 
it's not suitable for tall trees and graphs with a large height/depth. 
For those kinds of inputs, it's better to use BFS since it uses the Queue data 
structure, which is stored on the heap.
*/
public class P_1306_JumpGame3 {
    /*
     * BFS with seen boolean array.
     */
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

    /*
     * BFS with seen hashset and for loop/array to iterate through edges.
     */
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

    /*
     * DFS with seen boolean array.
     */
    class Solution {
        boolean[] seen;
        boolean found = false;

        public boolean canReach(int[] arr, int start) {
            int n = arr.length;
            this.seen = new boolean[n];
            this.dfs(arr, start, n);
            return found;
        }

        private void dfs(int[] arr, int index, int n) {
            seen[index] = true;
            if (arr[index] == 0) {
                this.found = true;
                return;
            }

            int jumpUp = index + arr[index];
            int jumpDown = index - arr[index];
            int[] jumps = new int[] { jumpUp, jumpDown };

            for (int jump : jumps) {
                if (jump >= 0 && jump < n && !seen[jump]) {
                    dfs(arr, jump, n);
                }
            }
        }
    }

    public static void main(String[] args) {
        P_1306_JumpGame3 solver = new P_1306_JumpGame3();

        // Test cases: { arr, start, expected }
        // Why these cases:
        // 1. Standard example from the problem — typical reachable case.
        // 2. Same array, different start — different traversal path still finds 0.
        // 3. Unreachable — a 0 exists but is isolated by parity/jumps.
        // 4. Start already on a 0 — earliest possible success.
        // 5. Single element, value 0 — smallest reachable case.
        // 6. Single element, non-zero — smallest unreachable case.
        // 7. All zeros — trivially reachable from any start.
        // 8. No zeros at all — can never succeed.
        // 9. Cycle without a 0 — ensures the visited set prevents infinite loops.
        // 10. Long array, only reachable via a chain of jumps — exercises BFS
        // traversal.
        Object[][] tests = new Object[][] {
                { new int[] { 4, 2, 3, 0, 3, 1, 2 }, 5, true },
                { new int[] { 4, 2, 3, 0, 3, 1, 2 }, 0, true },
                { new int[] { 3, 0, 2, 1, 2 }, 2, false },
                { new int[] { 0, 1 }, 0, true },
                { new int[] { 0 }, 0, true },
                { new int[] { 1 }, 0, false },
                { new int[] { 0, 0, 0, 0 }, 2, true },
                { new int[] { 1, 2, 3, 4, 5 }, 0, false },
                { new int[] { 2, 1, 1, 2 }, 0, false },
                { new int[] { 1, 1, 1, 1, 1, 1, 1, 0 }, 0, true }
        };

        System.out.println("Running tests for P_1306_JumpGame3.canReach\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int start = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.canReach(arr, start);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: arr=%s, start=%d => expected=%b, actual=%b => %s%n",
                    i + 1, java.util.Arrays.toString(arr), start, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1306_JumpGame3.canReach2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int start = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.canReach2(arr, start);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: arr=%s, start=%d => expected=%b, actual=%b => %s%n",
                    i + 1, java.util.Arrays.toString(arr), start, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1306_JumpGame3.Solution.canReach (DFS)\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] arr = (int[]) tests[i][0];
            int start = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            P_1306_JumpGame3.Solution dfsSolver = solver.new Solution();
            boolean actual = dfsSolver.canReach(arr, start);

            boolean ok = expected == actual;
            if (ok)
                pass3++;
            System.out.printf("Test %d: arr=%s, start=%d => expected=%b, actual=%b => %s%n",
                    i + 1, java.util.Arrays.toString(arr), start, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("canReach:           %d/%d tests passed%n", pass1, tests.length);
        System.out.printf("canReach2:          %d/%d tests passed%n", pass2, tests.length);
        System.out.printf("Solution.canReach:  %d/%d tests passed%n", pass3, tests.length);
    }
}
