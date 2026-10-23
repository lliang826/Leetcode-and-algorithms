import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_997_FindTheTownJudge {
    class Solution {
        private Map<Integer, List<Integer>> in = new HashMap<>();
        private Map<Integer, List<Integer>> out = new HashMap<>();
        boolean[] seen;
        int judge = -1;

        public int findJudge(int n, int[][] trust) {
            if (trust.length == 0 && n == 1) {
                return n;
            }

            buildGraph(n, trust);
            seen = new boolean[n + 1];

            for (int[] t : trust) {
                dfs(t[0], n);
            }

            return judge;
        }

        private void dfs(int person, int n) {
            seen[person] = true;
            if (out.get(person).size() == 0 && in.get(person).size() == n - 1) {
                judge = person;
            }

            for (int i : out.get(person)) {
                if (!seen[i]) {
                    dfs(i, n);
                }
            }
        }

        private void buildGraph(int n, int[][] trust) {
            for (int i = 1; i <= n; i++) {
                in.put(i, new ArrayList<>());
                out.put(i, new ArrayList<>());
            }

            for (int[] t : trust) {
                in.get(t[1]).add(t[0]);
                out.get(t[0]).add(t[1]);
            }
        }
    }

    public static void main(String[] args) {
        P_997_FindTheTownJudge outer = new P_997_FindTheTownJudge();

        // Test cases: {n, trust, expected, description}
        Object[][] tests = new Object[][] {
                { 2, new int[][] { { 1, 2 } }, 2,
                        "LC Example 1: simple judge" },
                { 3, new int[][] { { 1, 3 }, { 2, 3 } }, 3,
                        "LC Example 2: everyone trusts 3" },
                { 3, new int[][] { { 1, 3 }, { 2, 3 }, { 3, 1 } }, -1,
                        "LC Example 3: candidate trusts someone" },
                { 1, new int[][] {}, 1,
                        "Single person, no trust: they are the judge" },
                { 2, new int[][] {}, -1,
                        "Two people, no trust: nobody is trusted by everyone" },
                { 3, new int[][] { { 1, 2 } }, -1,
                        "Sink exists but person 3 doesn't trust them" },
                { 3, new int[][] { { 1, 2 }, { 2, 3 } }, -1,
                        "Chain 1->2->3: trust is not transitive" },
                { 4, new int[][] { { 1, 3 }, { 2, 3 } }, -1,
                        "Person 4 doesn't trust candidate 3" },
                { 3, new int[][] { { 1, 2 }, { 2, 1 } }, -1,
                        "Cycle: must not recurse forever" },
                { 4, new int[][] { { 1, 3 }, { 1, 4 }, { 2, 3 }, { 2, 4 }, { 4, 3 } }, 3,
                        "LC-style: extra trust edges, judge still 3" },
                { 4, new int[][] { { 1, 2 }, { 1, 4 }, { 3, 4 }, { 2, 4 }, { 1, 3 } }, 4,
                        "Judge is the last person (n)" }
        };

        System.out.println("Running tests for P_997_FindTheTownJudge.findJudge\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] trust = (int[][]) tests[i][1];
            int expected = (int) tests[i][2];
            String desc = (String) tests[i][3];

            // Fresh Solution per test case to avoid instance-field carryover.
            P_997_FindTheTownJudge.Solution solver = outer.new Solution();
            int actual = solver.findJudge(n, trust);

            boolean ok = actual == expected;
            if (ok)
                pass++;
            System.out.printf("Test %d (%s): n=%d, trust=%s => expected=%d, actual=%d => %s%n",
                    i + 1, desc, n, java.util.Arrays.deepToString(trust), expected, actual,
                    ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("findJudge: %d/%d tests passed%n", pass, tests.length);
    }
}
