import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class P_433_MinimumGeneticMutation {
    class Solution {
        public int minMutation(String startGene, String endGene, String[] bank) {
            Queue<String> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            int mutationCount = 0;
            char[] genes = new char[] { 'A', 'C', 'G', 'T' };

            Set<String> bankSet = new HashSet<>();
            for (String str : bank) {
                bankSet.add(str);
            }

            queue.offer(startGene);
            seen.add(startGene);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    String gene = queue.poll();
                    if (gene.equals(endGene)) {
                        return mutationCount;
                    }

                    for (int m = 0; m < 8; m++) {
                        char c = gene.charAt(m);
                        for (char g : genes) {
                            if (c != g) {
                                String s = gene.substring(0, m) + g + gene.substring(m + 1, 8);
                                if (bankSet.contains(s) && !seen.contains(s)) {
                                    queue.offer(s);
                                    seen.add(s);
                                }
                            }
                        }
                    }
                }

                mutationCount++;
            }

            return -1;
        }
    }
}
