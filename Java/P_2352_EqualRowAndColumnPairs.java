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
        
    }
}
