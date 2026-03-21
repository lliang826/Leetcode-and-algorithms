import java.util.List;

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
}
