import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P_841_KeysAndRooms {
    /*
     * Pretty simple and straightforward Graph problem. DFS approach with a boolean
     * array for the seen nodes.
     * Since the input is already an adjacency list, we can use it as is. First, we
     * perform a recursive DFS
     * to visit all the nodes/rooms. We use a boolean array to track the seen nodes
     * because the problem tells
     * us that there are n rooms labeled from 0 to n - 1; we could also use a set,
     * but a boolean array is
     * slightly faster since we don't need to compute the hash codes.
     * Once we've traversed the entire graph starting from room 0, we iterate
     * through the boolean array to
     * check if any nodes/rooms are unvisited.
     * 
     * Time: O(n + e), where n is the # of nodes and e is the # of edges
     * - But in the worst case scenario, if a node is connected to every other node,
     * e = O(n^2) => O(n^2)
     * Space: O(n), boolean array
     */
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

    /*
    Improved version of the solution above. Instead of iterating through the boolean
    array at the end, we can have a counter for the number of seen rooms. If this counter
    is the same as the total number of rooms, we've seen every room and can return True.
    
    Same time and space complexities, but slightly faster because we no longer need to
    iterate through the boolean array.
    */
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

    /*
    Alternate approach using a set instead of a boolean array for the seen nodes.
    
    Same time and space complexities, but slightly slower due to sets computing hash codes.
    */
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

    private static List<List<Integer>> buildRooms(int[]... keys) {
        List<List<Integer>> rooms = new ArrayList<>();
        for (int[] k : keys) {
            List<Integer> room = new ArrayList<>();
            for (int v : k) room.add(v);
            rooms.add(room);
        }
        return rooms;
    }

    public static void main(String[] args) {
        P_841_KeysAndRooms outer = new P_841_KeysAndRooms();

        // Test cases: {rooms (as List<List<Integer>>), expected boolean}
        Object[][] tests = new Object[][] {
                // Example 1: linear chain, all rooms reachable
                { buildRooms(new int[] { 1 }, new int[] { 2 }, new int[] { 3 }, new int[] {}), true },
                // Example 2: room 2 is unreachable
                { buildRooms(new int[] { 1, 3 }, new int[] { 3, 0, 1 }, new int[] { 2 }, new int[] { 0 }), false },
                // Room 0 has all keys directly
                { buildRooms(new int[] { 1, 2, 3 }, new int[] {}, new int[] {}, new int[] {}), true },
                // Minimum rooms, room 1 unreachable (room 0 has no keys)
                { buildRooms(new int[] {}, new int[] {}), false },
                // 2 rooms, bidirectional
                { buildRooms(new int[] { 1 }, new int[] { 0 }), true },
                // 3-room cycle
                { buildRooms(new int[] { 1 }, new int[] { 2 }, new int[] { 0 }), true },
                // Non-sequential access: 0 -> 2 -> 1
                { buildRooms(new int[] { 2 }, new int[] {}, new int[] { 1 }), true },
                // Branching graph
                { buildRooms(new int[] { 1, 2 }, new int[] { 3 }, new int[] { 3 }, new int[] {}), true },
                // Chain with back-edge at the end
                { buildRooms(new int[] { 1 }, new int[] { 2 }, new int[] { 3 }, new int[] { 1 }), true },
                // Disconnected: only rooms 0 and 3 reachable
                { buildRooms(new int[] { 3 }, new int[] { 1, 3 }, new int[] { 2 }, new int[] { 0 }), false },
        };

        // Note: we create a new solver instance per test because Solution2
        // and Solution3 have state that doesn't reset between calls (see analysis).

        System.out.println("Running tests for Solution.canVisitAllRooms\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            @SuppressWarnings("unchecked")
            List<List<Integer>> rooms = (List<List<Integer>>) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            Solution solver = outer.new Solution();
            boolean actual = solver.canVisitAllRooms(rooms);
            boolean ok = expected == actual;
            if (ok) pass1++;
            System.out.printf("Test %d: rooms=%s => expected=%b, actual=%b => %s\n",
                    i + 1, rooms, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for Solution2.canVisitAllRooms\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            @SuppressWarnings("unchecked")
            List<List<Integer>> rooms = (List<List<Integer>>) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            Solution2 solver = outer.new Solution2();
            boolean actual = solver.canVisitAllRooms(rooms);
            boolean ok = expected == actual;
            if (ok) pass2++;
            System.out.printf("Test %d: rooms=%s => expected=%b, actual=%b => %s\n",
                    i + 1, rooms, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for Solution3.canVisitAllRooms\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            @SuppressWarnings("unchecked")
            List<List<Integer>> rooms = (List<List<Integer>>) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            Solution3 solver = outer.new Solution3();
            boolean actual = solver.canVisitAllRooms(rooms);
            boolean ok = expected == actual;
            if (ok) pass3++;
            System.out.printf("Test %d: rooms=%s => expected=%b, actual=%b => %s\n",
                    i + 1, rooms, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution:  %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("Solution3: %d/%d tests passed\n", pass3, tests.length);
    }
}
