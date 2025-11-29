import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_2225_FindPlayersWIthZeroOrOneLosses {
    /*
    Frequency map approach. Since I decided to use a single hash map, the tricky part of this problem was figuring out
    how to track the counts for each match. Originally, I thought maybe a win could increment the count by 1 and a loss
    could decrement the count by 1, but this wasn't accurate - a player could have a score of 4 if they won 5 matches
    but lost 1.
    
    To use a single hash map, we simply have to count the number of losses for each player. Each loss increment's the
    frequency by 1, while a win does nothing (it adds the player to the map if they haven't been added yet).
    
    Once we've iterated through all the players in the input array, we can check the loss frequency map for players who
    never lost and players who lost once. We perform sorting since the question wants the two lists in the answer to be
    in increasing order. We also use ArrayLists because arrays are fixed in size in Java - ArrayLists are dynamically sized.
    
    Time: O(n + n + n log n + n log n) => O(n log n), depends on sorting time
    Space: O(n), 2 arraylists are created, but they are at most n, where n is the # of players
    */
    public List<List<Integer>> findWinners(int[][] matches) {
        Map<Integer, Integer> lossMap = new HashMap<>();

        for (int[] match : matches) {
            lossMap.put(match[0], lossMap.getOrDefault(match[0], 0) + 0);
            lossMap.put(match[1], lossMap.getOrDefault(match[1], 0) + 1);
        }

        List<Integer> noLosses = new ArrayList<>();
        List<Integer> oneLoss = new ArrayList<>();

        for (int player : lossMap.keySet()) {
            if (lossMap.get(player) == 0) {
                noLosses.add(player);
            } else if (lossMap.get(player) == 1) {
                oneLoss.add(player);
            }
        }

        Collections.sort(noLosses);
        Collections.sort(oneLoss);

        List<List<Integer>> ans = new ArrayList<>();
        ans.add(noLosses);
        ans.add(oneLoss);

        return ans;
    }
    
    public static void main(String[] args) {
        P_2225_FindPlayersWIthZeroOrOneLosses solver = new P_2225_FindPlayersWIthZeroOrOneLosses();

        Object[][] tests = new Object[][] {
                { new int[][] { { 1, 3 }, { 2, 3 }, { 3, 6 }, { 5, 6 }, { 5, 7 }, { 4, 5 }, { 4, 8 }, { 4, 9 }, { 10, 4 }, { 10, 9 } },
                        List.of(List.of(1, 2, 10), List.of(4, 5, 7, 8)) },
                { new int[][] { { 2, 3 }, { 1, 3 }, { 5, 4 }, { 6, 4 } },
                        List.of(List.of(1, 2, 5, 6), List.of()) },
                { new int[][] { { 1, 2 } },
                        List.of(List.of(1), List.of(2)) },
                { new int[][] { { 1, 2 }, { 2, 1 } },
                        List.of(List.of(), List.of(1, 2)) },
                { new int[][] { { 1, 2 }, { 1, 3 }, { 1, 4 } },
                        List.of(List.of(1), List.of(2, 3, 4)) },
                { new int[][] { { 1, 5 }, { 2, 5 }, { 3, 5 }, { 4, 5 } },
                        List.of(List.of(1, 2, 3, 4), List.of()) },
                { new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } },
                        List.of(List.of(1, 3, 5), List.of(2, 4, 6)) },
                { new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 } },
                        List.of(List.of(1), List.of(2, 3, 4)) },
                { new int[][] { { 5, 1 }, { 5, 2 }, { 5, 3 }, { 1, 4 }, { 2, 4 }, { 3, 4 } },
                        List.of(List.of(5), List.of(1, 2, 3)) },
                { new int[][] { { 1, 2 }, { 3, 2 }, { 4, 2 } },
                        List.of(List.of(1, 3, 4), List.of()) }
        };

        System.out.println("Running tests for P_2225_FindPlayersWIthZeroOrOneLosses.findWinners\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] input = (int[][]) tests[i][0];
            @SuppressWarnings("unchecked")
            List<List<Integer>> expected = (List<List<Integer>>) tests[i][1];
            List<List<Integer>> actual = solver.findWinners(input);

            boolean ok = expected.equals(actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.deepToString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("findWinners: %d/%d tests passed\n", pass, tests.length);
    }
}
