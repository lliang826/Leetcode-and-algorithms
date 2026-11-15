import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_997_FindTheTownJudge {
    /*
    Initial DFS approach.
    */
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

    /*
    More efficient approach: count the in and out degrees for each node.

    Same time complexity as the original solution, but more efficient because there's no
    DFS involved.

    Space: O(n) for the trustScore integer array
    */
    public int findJudge2(int n, int[][] trust) {
        int[] trustScore = new int[n + 1];

        for (int[] pair : trust) {
            int truster = pair[0];
            int trusted = pair[1];
            trustScore[truster]--;
            trustScore[trusted]++;
        }

        for (int person = 1; person <= n; person++) {
            if (trustScore[person] == n - 1) {
                return person;
            }
        }
        return -1;
    }

    /*
    Counting indegrees and outdegrees, another solution.
    */
    public int findJudge3(int n, int[][] trust) {
        // {indegree, outdegree}
        Map<Integer, Integer[]> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            map.put(i, new Integer[]{0, 0});
        }

        for (int[] t : trust) {
            int out = t[0];
            Integer[] newOut = map.get(out);
            map.put(out, new Integer[]{newOut[0], newOut[1] + 1});

            int in = t[1];
            Integer[] newIn = map.get(in);
            map.put(in, new Integer[]{newIn[0] + 1, newIn[1]});
        }

        for (Map.Entry<Integer, Integer[]> entry : map.entrySet()) {
            Integer[] value = entry.getValue();
            if (value[0] == n - 1 && value[1] == 0) {
                return entry.getKey();
            }
        }

        return -1;
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
                        "Judge is the last person (n)" },
                { 3, new int[][] { { 2, 1 }, { 3, 1 } }, 1,
                        "Judge is the first person (1)" },
                { 3, new int[][] { { 1, 3 }, { 2, 3 }, { 3, 2 } }, -1,
                        "Trusted by all but trusts one person (score n-2, not n-1)" },
                { 3, new int[][] { { 1, 2 }, { 2, 3 }, { 3, 1 } }, -1,
                        "Everyone trusts exactly one person: no sink at all" },
                { 200, everyoneTrusts(200, 200, -1), 200,
                        "Large n: in-degree 199 > 127 (Integer cache boundary)" },
                { 200, everyoneTrusts(200, 150, 199), -1,
                        "Large n: one person (199) doesn't trust candidate 150" }
        };

        System.out.println("Running tests for P_997_FindTheTownJudge.Solution.findJudge (DFS)\n");
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
                    i + 1, desc, n, formatTrust(trust), expected, actual,
                    ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_997_FindTheTownJudge.findJudge2 (trust score)\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] trust = (int[][]) tests[i][1];
            int expected = (int) tests[i][2];
            String desc = (String) tests[i][3];

            int actual = outer.findJudge2(n, trust);

            boolean ok = actual == expected;
            if (ok)
                pass2++;
            System.out.printf("Test %d (%s): n=%d, trust=%s => expected=%d, actual=%d => %s%n",
                    i + 1, desc, n, formatTrust(trust), expected, actual,
                    ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_997_FindTheTownJudge.findJudge3 (in/out degree map)\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] trust = (int[][]) tests[i][1];
            int expected = (int) tests[i][2];
            String desc = (String) tests[i][3];

            int actual = outer.findJudge3(n, trust);

            boolean ok = actual == expected;
            if (ok)
                pass3++;
            System.out.printf("Test %d (%s): n=%d, trust=%s => expected=%d, actual=%d => %s%n",
                    i + 1, desc, n, formatTrust(trust), expected, actual,
                    ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("Solution.findJudge (DFS): %d/%d tests passed%n", pass, tests.length);
        System.out.printf("findJudge2 (trust score): %d/%d tests passed%n", pass2, tests.length);
        System.out.printf("findJudge3 (in/out degree map): %d/%d tests passed%n", pass3, tests.length);
    }

    // Builds trust pairs where every person except the judge (and except skip, if skip != -1) trusts the judge.
    private static int[][] everyoneTrusts(int n, int judge, int skip) {
        List<int[]> pairs = new ArrayList<>();
        for (int person = 1; person <= n; person++) {
            if (person != judge && person != skip) {
                pairs.add(new int[] { person, judge });
            }
        }
        return pairs.toArray(new int[0][]);
    }

    // Keeps output readable for the large generated test cases.
    private static String formatTrust(int[][] trust) {
        if (trust.length > 10) {
            return "[" + trust.length + " pairs]";
        }
        return java.util.Arrays.deepToString(trust);
    }
}
