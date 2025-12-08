import java.util.HashMap;
import java.util.Map;

public class P_2352_EqualRowAndColumnPairs {
    public int equalPairs(int[][] grid) {
        Map<String, Integer> rows = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]).append('#');
            }
            rows.put(sb.toString(), rows.getOrDefault(sb.toString(), 0) + 1);
        }

        Map<String, Integer> columns = new HashMap<>();
        for (int i = 0; i < grid[0].length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < grid.length; j++) {
                sb.append(grid[j][i]).append('#');
            }
            columns.put(sb.toString(), columns.getOrDefault(sb.toString(), 0) + 1);
        }

        int output = 0;
        for (String key : rows.keySet()) {
            if (columns.containsKey(key)) {
                output += rows.get(key) * columns.get(key);
            }
        }

        return output;
    }

    public static void main(String[] args) {
        P_2352_EqualRowAndColumnPairs solver = new P_2352_EqualRowAndColumnPairs();

        Object[][] tests = new Object[][] {
                { new int[][] { { 3, 2, 1 }, { 1, 7, 6 }, { 2, 7, 7 } }, 1 },
                { new int[][] { { 3, 1, 2, 2 }, { 1, 4, 4, 5 }, { 2, 4, 2, 2 }, { 2, 4, 2, 2 } }, 3 },
                { new int[][] { { 1 } }, 1 },
                { new int[][] { { 1, 2 }, { 3, 4 } }, 0 },
                { new int[][] { { 1, 2 }, { 2, 1 } }, 2 },
                { new int[][] { { 11, 1 }, { 1, 11 } }, 2 },
                { new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 9 },
                { new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }, 0 },
                { new int[][] { { 13, 13 }, { 13, 13 } }, 4 }
        };

        System.out.println("Running tests for P_2352_EqualRowAndColumnPairs.equalPairs\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] input = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.equalPairs(input);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: expected=%d, actual=%d => %s\n",
                    i + 1, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
