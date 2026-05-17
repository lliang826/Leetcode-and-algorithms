import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class P_127_WordLadder {
    class Solution {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> words = new HashSet<>();
            for (String word : wordList) {
                words.add(word);
            }

            if (!words.contains(endWord)) {
                return 0;
            }

            Queue<String> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            int count = 0;

            queue.offer(beginWord);
            seen.add(beginWord);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                count++;

                for (int i = 0; i < levelSize; i++) {
                    String s = queue.poll();
                    if (s.equals(endWord)) {
                        return count;
                    }

                    for (int m = 0; m < s.length(); m++) {
                        for (char c = 'a'; c <= 'z'; c++) {
                            String newString = s.substring(0, m) + c + s.substring(m + 1, s.length());
                            if (words.contains(newString) && !seen.contains(newString)) {
                                seen.add(newString);
                                queue.offer(newString);
                            }
                        }
                    }
                }
            }

            return 0;
        }
    }
}
