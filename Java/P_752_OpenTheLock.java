import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class P_752_OpenTheLock {
    /*
    This is an implicit graph problem. We have a start state "0000", a goal state target, and rules that
    generate neighbors (turning 4 wheels up or down, digits range from 0-9). It asks for the minimum total
    number of turns, so we use BFS.
    
    We pre-load deadends into the seen set so BFS skips them automatically — same effect as never visiting
    them, without needing a separate check. For neighbor generation, we use a StringBuilder to mutate one
    character at a time and reset it after, avoiding temporary substring allocations.
    
    Time: O(D) where D is the number of deadends. 
      - O(nodes + edges) => O(10^4 + 10^4 * 8) => O(1) all constants
      - But we do have to process the deadends input, so time complexity is O(D)
    Space: O(1) effectively — the seen set and queue hold at most 10^4 nodes, which is a constant.
    */
    class Solution {
        public int openLock(String[] deadends, String target) {
            Queue<String> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            int count = 0;

            for (String d : deadends) {
                if (d.equals("0000")) {
                    return -1;
                }
                seen.add(d);
            }

            queue.offer("0000");
            seen.add("0000");

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    String node = queue.poll();

                    if (node.equals(target)) {
                        return count;
                    }

                    for (String turn : this.getNextTurns(node)) {
                        if (!seen.contains(turn)) {
                            queue.offer(turn);
                            seen.add(turn);
                        }
                    }
                }

                count++;
            }

            return -1;
        }

        private String[] getNextTurns(String node) {
            List<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder(node);

            for (int i = 0; i < node.length(); i++) {
                char c = node.charAt(i);

                char rollWheelUp = c == '9' ? '0' : (char) (c + 1);
                sb.setCharAt(i, rollWheelUp);
                list.add(sb.toString());

                char rollWheelDown = c == '0' ? '9' : (char) (c - 1);
                sb.setCharAt(i, rollWheelDown);
                list.add(sb.toString());

                sb.setCharAt(i, c);
            }

            return list.toArray(new String[0]);
        }
    }

    /*
    Same BFS approach but uses substring concatenation for neighbor generation instead of StringBuilder.
    More readable and portable across languages (Python slicing, JS slice, etc.), but creates more
    temporary String objects (~3-4 per neighbor vs 1 for StringBuilder).

    Same time and space complexities as above, but slightly slower since StringBuilder is more efficient.
    */
    class Solution2 {
        public int openLock(String[] deadends, String target) {
            Queue<String> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            int count = 0;

            for (String d : deadends) {
                if (d.equals("0000")) {
                    return -1;
                }
                seen.add(d);
            }

            queue.offer("0000");
            seen.add("0000");

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    String node = queue.poll();

                    if (node.equals(target)) {
                        return count;
                    }

                    for (String turn : this.getNextTurns(node)) {
                        if (!seen.contains(turn)) {
                            queue.offer(turn);
                            seen.add(turn);
                        }
                    }
                }

                count++;
            }

            return -1;
        }

        private String[] getNextTurns(String node) {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < node.length(); i++) {
                char c = node.charAt(i);

                char rollUp = c == '9' ? '0' : (char) (c + 1);
                String rollUpString = node.substring(0, i) + rollUp + node.substring(i + 1);
                list.add(rollUpString);

                char rollDown = c == '0' ? '9' : (char) (c - 1);
                String rollDownString = node.substring(0, i) + rollDown + node.substring(i + 1);
                list.add(rollDownString);
            }

            return list.toArray(new String[0]);
        }
    }

    /*
     * 0000
     * 
     * 1000 9000
     * 0100 0900
     * 0010 0090
     * 0001 0009
     * 
     * 
     * 9000
     * 
     * 0000
     * 
     * 
     * add 1, mod 10
     * minus 1, mod 10
     */

    public static void main(String[] args) {
        P_752_OpenTheLock outer = new P_752_OpenTheLock();
        Solution solver1 = outer.new Solution();
        Solution2 solver2 = outer.new Solution2();

        // Test cases: {deadends, target, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new String[] { "0201", "0101", "0102", "1212", "2002" }, "0202", 6 },
                { new String[] { "8888" }, "0009", 1 },
                { new String[] { "8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888" }, "8888", -1 },
                // Start is deadend
                { new String[] { "0000" }, "8888", -1 },
                // Target is start
                { new String[] { "1234" }, "0000", 0 },
                // No deadends
                { new String[] {}, "5555", 20 },
                // Wrap around: 0000 -> 9000 (one turn down on first wheel)
                { new String[] {}, "9999", 4 },
                // Single wheel turn
                { new String[] {}, "0001", 1 },
                // Deadends block direct path, BFS finds alternate route (rolling down instead of up)
                { new String[] { "1000", "0100", "0010", "0001" }, "1111", 6 },
                // All 8 neighbors of "0000" are deadends, target unreachable
                { new String[] { "0001", "0009", "0010", "0090", "0100", "0900", "1000", "9000" }, "5555", -1 },
        };

        System.out.println("Running tests for P_752_OpenTheLock Solution (StringBuilder)\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] deadends = (String[]) tests[i][0];
            String target = (String) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver1.openLock(deadends, target);

            boolean ok = expected == actual;
            if (ok) pass1++;
            System.out.printf("Test %d: target=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_752_OpenTheLock Solution2 (substring)\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String[] deadends = (String[]) tests[i][0];
            String target = (String) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver2.openLock(deadends, target);

            boolean ok = expected == actual;
            if (ok) pass2++;
            System.out.printf("Test %d: target=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution (StringBuilder): %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2 (substring): %d/%d tests passed\n", pass2, tests.length);
    }
}
