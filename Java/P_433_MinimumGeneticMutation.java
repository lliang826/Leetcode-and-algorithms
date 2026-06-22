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
}
