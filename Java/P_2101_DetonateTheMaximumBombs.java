import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class P_2101_DetonateTheMaximumBombs {
    /*
    Recursive DFS approach with boolean seen array.
    
    This question is an implicit graph problem: we have a start state (the bomb we choose to
    detonate first), a goal state (detonating the max number of bombs in the chain), and rules
    to generate neighbors (other bombs within the range of the first one will also be detonated).
    
    One tricky part of this problem is recognizing that it's a directed graph, so we need to
    build an adjacency list. In example 1, bombs[0] has a small radius, so it cannot detonate
    bombs[1]. But the latter has a larger radius, so it can detonate the former. This means that
    we have a directed edge from bombs[1] to bombs[0].
    
    Another tricky part is figuring out how to find the neighbors; how can we tell if a bomb is
    within the radius of another? For this, we have to use the Pythagorean theorem: 
    x^2 + y^2 = z^2
    If z^2 is less than or equal to radius^2, the second bomb is within the radius/range of the
    first bomb.
    Also, when we are calculating z^2 in the Pythagorean theorem, one crucial piece is to use
    longs instead of ints; if x, y, or z have large values, their squares might cause integer
    overflows, so it's better to use longs.
    
    The final tricky part is knowing to run a graph traversal starting on each node in the graph.
    The question asks for the max number of bombs that can be detonated if you detonate only one
    bomb at the start. Since we don't know which bomb chain is the longest, we have to run a graph
    traversal on each node.
    This means that on each traversal, we have to reset the boolean seen array. We also need to
    track the count for each traversal, and keep only the max count.
    
    Time: 
      - buildGraph: O(n^2) pair checks
      - DFS from each of n starts: n * O(n + e)
      - In the worst case e = n^2 (every bomb reaches every other)
      - O(n * (n + e)) => O(n * (n + n^2)) => O(n^2 + n^3) => O(n^3) overall
    
    Space: O(nodes + edges) for the adjacency list + O(n) for seen array + O(n) recursion stack
        = O(n + e) overall
    */
    class Solution {
        Map<Integer, List<Integer>> bombGraph = new HashMap<>();
        boolean[] seen;
        int count = 0;

        public int maximumDetonation(int[][] bombs) {
            this.buildGraph(bombs);
            int maxCount = 0;

            for (int i = 0; i < bombs.length; i++) {
                this.count = 0;
                this.seen = new boolean[bombs.length];
                this.dfs(i);
                maxCount = Math.max(maxCount, count);
            }

            return maxCount;
        }

        private void dfs(int bomb) {
            seen[bomb] = true;
            this.count++;

            for (int neighbor : bombGraph.get(bomb)) {
                if (!seen[neighbor]) {
                    this.dfs(neighbor);
                }
            }
        }

        private void buildGraph(int[][] bombs) {
            for (int i = 0; i < bombs.length; i++) {
                bombGraph.put(i, new ArrayList<>());
            }

            for (int i = 0; i < bombs.length; i++) {
                for (int j = i + 1; j < bombs.length; j++) {
                    int[] bombA = bombs[i];
                    int[] bombB = bombs[j];

                    if (isWithinRadius(bombA, bombB)) {
                        bombGraph.get(i).add(j);
                    }

                    if (isWithinRadius(bombB, bombA)) {
                        bombGraph.get(j).add(i);
                    }
                }
            }
        }

        private boolean isWithinRadius(int[] bombA, int[] bombB) {
            long x = bombB[0] - bombA[0];
            long y = bombB[1] - bombA[1];
            long radius = bombA[2];
            return x * x + y * y <= radius * radius;
        }
    }

    /*
    BFS approach with boolean seen array to track seen nodes/bombs.
    
    Similar to the solution above, but we use BFS instead of recursive DFS.
    Since the recursive DFS solution uses the call stack, it's not suitable if the graph is very tall
    (if bombs.length is very large). If the graph is very tall, it's better to use the heap, which
    means a BFS or iterative DFS approach is better.
    For this problem, the constraints tell us that "1 <= bombs.length <= 100", so n is pretty 
    small; we can use either DFS or BFS.
    But recursive DFS is less verbose, so it might be the better pick for this problem.
    
    We mark a node as seen and increment count when we
    ENQUEUE it, not when we dequeue it. Marking on dequeue would allow the
    same node to be enqueued multiple times (once per incoming edge from
    already-visited nodes), inflating the count and bloating the queue.
    
    Same time and space complexities as the recursive DFS solution above.
    
    Time: buildGraph + BFS on each node
        => O(n^2) + O(n * (n + e))
        => O(n^2) + O(n * (n + n^2)) 
        => O(n^2) + O(n^2 + n^3)
        => O(n^3)
        In the worst case scenario, if every node can reach every other node, the # of edges
        is n^2 in a graph
    
    Space: O(nodes + edges) for the adjacency list + O(n) for seen array + O(n) queue
        = O(n + e) overall
    */
    class Solution2 {
        Map<Integer, List<Integer>> bombGraph = new HashMap<>();
        boolean[] seen;
        int count = 0;

        public int maximumDetonation(int[][] bombs) {
            this.buildGraph(bombs);
            int maxCount = 0;

            for (int i = 0; i < bombs.length; i++) {
                this.seen = new boolean[bombs.length];
                this.count = 0;
                this.bfs(i);
                maxCount = Math.max(maxCount, count);
            }

            return maxCount;
        }

        private void bfs(int bomb) {
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(bomb);
            this.seen[bomb] = true;
            this.count++;

            while (!queue.isEmpty()) {
                int node = queue.poll();

                for (int neighbor : bombGraph.get(node)) {
                    if (!seen[neighbor]) {
                        queue.offer(neighbor);
                        this.seen[neighbor] = true;
                        this.count++;
                    }
                }
            }
        }

        private void buildGraph(int[][] bombs) {
            for (int i = 0; i < bombs.length; i++) {
                bombGraph.put(i, new ArrayList<>());
            }

            for (int i = 0; i < bombs.length; i++) {
                for (int j = i + 1; j < bombs.length; j++) {
                    int[] bombA = bombs[i];
                    int[] bombB = bombs[j];

                    if (isWithinRadius(bombA, bombB)) {
                        bombGraph.get(i).add(j);
                    }

                    if (isWithinRadius(bombB, bombA)) {
                        bombGraph.get(j).add(i);
                    }
                }
            }
        }

        private boolean isWithinRadius(int[] bombA, int[] bombB) {
            long x = bombB[0] - bombA[0];
            long y = bombB[1] - bombA[1];
            long radius = bombA[2];
            return x * x + y * y <= radius * radius;
        }
    }

    public static void main(String[] args) {
        P_2101_DetonateTheMaximumBombs outer = new P_2101_DetonateTheMaximumBombs();

        // Test cases: {bombs, expected, description}
        Object[][] tests = new Object[][] {
                { new int[][] { { 2, 1, 3 }, { 6, 1, 4 } }, 2,
                        "LC Example 1: only bomb 1 reaches bomb 0" },
                { new int[][] { { 1, 1, 5 }, { 10, 10, 5 } }, 1,
                        "LC Example 2: bombs too far apart" },
                { new int[][] { { 1, 2, 3 }, { 2, 3, 1 }, { 3, 4, 2 }, { 4, 5, 3 }, { 5, 6, 4 } }, 5,
                        "LC Example 3: full chain reaction" },
                { new int[][] { { 1, 1, 1 } }, 1,
                        "Single bomb (degenerate input)" },
                { new int[][] { { 0, 0, 2 }, { 1, 1, 2 } }, 2,
                        "Two bombs with mutual reach" },
                { new int[][] { { 0, 0, 10 }, { 5, 0, 1 } }, 2,
                        "Asymmetric edge: big bomb reaches small, not reverse" },
                { new int[][] { { 0, 0, 1 }, { 1, 0, 1 }, { 100, 0, 1 }, { 101, 0, 1 } }, 2,
                        "Two disconnected clusters of size 2 each" },
                { new int[][] { { 0, 0, 1 }, { 10, 0, 1 }, { 20, 0, 1 } }, 1,
                        "All bombs isolated (no edges)" },
                { new int[][] { { 0, 0, 1 }, { 1, 0, 1 }, { 0, 1, 1 } }, 3,
                        "Triangle with cycles - must guard against revisits" },
                { new int[][] { { 1, 1, 100000 }, { 100000, 100000, 1 } }, 1,
                        "Large coordinates - overflow probe (int*int would fail)" }
        };

        System.out.println("Running tests for P_2101_DetonateTheMaximumBombs.maximumDetonation (DFS)\n");
        int passDfs = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] input = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            String desc = (String) tests[i][2];

            // Fresh Solution per test case to avoid instance-field carryover.
            P_2101_DetonateTheMaximumBombs.Solution solver = outer.new Solution();
            int actual = solver.maximumDetonation(input);

            boolean ok = actual == expected;
            if (ok)
                passDfs++;
            System.out.printf("Test %d (%s): expected=%d, actual=%d => %s%n",
                    i + 1, desc, expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_2101_DetonateTheMaximumBombs.maximumDetonation (BFS)\n");
        int passBfs = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] input = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            String desc = (String) tests[i][2];

            // Fresh Solution2 per test case to avoid instance-field carryover.
            P_2101_DetonateTheMaximumBombs.Solution2 solver = outer.new Solution2();
            int actual = solver.maximumDetonation(input);

            boolean ok = actual == expected;
            if (ok)
                passBfs++;
            System.out.printf("Test %d (%s): expected=%d, actual=%d => %s%n",
                    i + 1, desc, expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("maximumDetonation (DFS): %d/%d tests passed%n", passDfs, tests.length);
        System.out.printf("maximumDetonation (BFS): %d/%d tests passed%n", passBfs, tests.length);
    }
}
