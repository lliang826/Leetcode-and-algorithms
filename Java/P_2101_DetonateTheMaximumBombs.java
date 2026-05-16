import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class P_2101_DetonateTheMaximumBombs {
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
}
