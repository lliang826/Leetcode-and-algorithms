import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class P_433_MinimumGeneticMutation {
    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>();
        for (String s : bank) {
            bankSet.add(s);
        }

        if (!bankSet.contains(endGene)) {
            return -1;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> seen = new HashSet<>();
        int count = 0;
        char[] choices = new char[] { 'A', 'C', 'G', 'T' };

        queue.offer(startGene);
        seen.add(startGene);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                String gene = queue.poll();
                if (gene.equals(endGene)) {
                    return count;
                }

                for (int j = 0; j < gene.length(); j++) {
                    char c = gene.charAt(j);
                    for (char choice : choices) {
                        if (choice != c) {
                            String newGene = gene.substring(0, j) + choice + gene.substring(j + 1);
                            if (bankSet.contains(newGene) && !seen.contains(newGene)) {
                                queue.offer(newGene);
                                seen.add(newGene);
                            }
                        }
                    }
                }
            }

            count++;
        }

        return -1;
    }

    public static void main(String[] args) {
        P_433_MinimumGeneticMutation solver = new P_433_MinimumGeneticMutation();

        // Test cases: {startGene, endGene, bank, expected}
        Object[][] tests = new Object[][] {
                // Single mutation away.
                { "AACCGGTT", "AACCGGTA", new String[] { "AACCGGTA" }, 1 },
                // Two-step chain through the bank.
                { "AACCGGTT", "AAACGGTA", new String[] { "AACCGGTA", "AACCGCTA", "AAACGGTA" }, 2 },
                // Three-step chain, mutating one position at a time.
                { "AAAAACCC", "AACCCCCC", new String[] { "AAAACCCC", "AAACCCCC", "AACCCCCC" }, 3 },
                // endGene is not in the bank -> impossible.
                { "AACCGGTT", "AACCGGTA", new String[] {}, -1 },
                // endGene is in the bank but unreachable (no valid single-step intermediate).
                { "AAAAAAAA", "AAAAAAGG", new String[] { "AAAAAAGG" }, -1 },
                // start already equals end (and is in the bank) -> 0 mutations.
                { "AACCGGTT", "AACCGGTT", new String[] { "AACCGGTT" }, 0 }
        };

        System.out.println("Running tests for P_433_MinimumGeneticMutation.minMutation\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String start = (String) tests[i][0];
            String end = (String) tests[i][1];
            String[] bank = (String[]) tests[i][2];
            int expected = (int) tests[i][3];
            int actual = solver.minMutation(start, end, bank);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: start=%s, end=%s, bank=%s => expected=%d, actual=%d => %s\n",
                    i + 1, start, end, java.util.Arrays.toString(bank), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minMutation: %d/%d tests passed\n", pass, tests.length);
    }
}
