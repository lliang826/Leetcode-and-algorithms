import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P_841_KeysAndRooms {
    class Solution {
        private boolean[] seen;

        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            this.seen = new boolean[rooms.size()];

            this.dfs(rooms, 0);

            for (boolean b : seen) {
                if (!b) {
                    return false;
                }
            }
            return true;
        }

        private void dfs(List<List<Integer>> rooms, int i) {
            seen[i] = true;
            for (int key : rooms.get(i)) {
                if (!seen[key]) {
                    dfs(rooms, key);
                }
            }
        }
    }

    class Solution2 {
        private boolean[] seen;
        private int seenCount = 0;

        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            this.seen = new boolean[rooms.size()];

            this.dfs(rooms, 0);

            return seenCount == rooms.size();
        }

        private void dfs(List<List<Integer>> rooms, int i) {
            seen[i] = true;
            seenCount++;
            for (int key : rooms.get(i)) {
                if (!seen[key]) {
                    dfs(rooms, key);
                }
            }
        }
    }

    class Solution3 {
        private Set<Integer> seen = new HashSet<>();

        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            this.dfs(rooms, 0);
            return seen.size() == rooms.size();
        }

        private void dfs(List<List<Integer>> rooms, int i) {
            seen.add(i);
            for (int key : rooms.get(i)) {
                if (!seen.contains(key)) {
                    dfs(rooms, key);
                }
            }
        }
    }
}
