import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class P_127_WordLadder {
    /*
    This question is an implicit graph problem. It gives us the following:
    1. Starting state (beginWord)
    2. End/goal state (endWord)
    3. Rules to define neighbors (changing one letter/char at a time, new word must be in wordList)
    
    Since the question asks for the shortest transformation sequence, we can use a BFS approach with a
    hashset to track seen nodes/words.
    
    By using the typical BFS approach for graphs, this problem is pretty straightforward. But the tricky
    part is transforming the string char by char - I did this with substring.
    
    Putting the wordList into a hashset at the beginning is also useful so checking if the transformed
    word is in the list becomes a constant O(1) operation. We can also add a check at the beginning to
    see if endWord exists in the wordList; if not, return 0 right away.
    
    Time: O(nodes * neighbors * work per neighbor + wordList build)
          => O(n * 26L * L + n * L) => O(n * L^2 + n * L) => O(n * L^2)
    Space: O(queue + seen + wordList) => O(nL + nL + nL) => O(n * L)
    
    Let n = number of words, L = word length. Note each word has 26 * L neighbors (not just 26), since we
    try 26 letters at each of the L positions. Building each candidate substring is O(L), and each
    hashset lookup/insert also hashes O(L) chars, so every candidate costs O(L).
    */
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

    /*
    Bidirectional BFS.
    */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> seen = new HashSet<>();
        Set<String> words = new HashSet<>();
        for (String s : wordList) {
            words.add(s);
        }
        int count = 0;

        if (!words.contains(endWord)) {
            return 0;
        }

        beginSet.add(beginWord);
        endSet.add(endWord);
        seen.add(beginWord);
        seen.add(endWord);
        count++;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (endSet.size() < beginSet.size()) {
                Set<String> tempSet = beginSet;
                beginSet = endSet;
                endSet = tempSet;
            }

            Set<String> nextSet = new HashSet<>();
            for (String s : beginSet) {
                for (int i = 0; i < s.length(); i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        String newString = s.substring(0, i) + c + s.substring(i + 1, s.length());
                        if (endSet.contains(newString)) {
                            return count + 1;
                        }

                        if (words.contains(newString) && !seen.contains(newString)) {
                            nextSet.add(newString);
                            seen.add(newString);
                        }
                    }
                }
            }

            beginSet = nextSet;
            count++;
        }

        return 0;
    }

    public static void main(String[] args) {
        P_127_WordLadder outer = new P_127_WordLadder();

        // Test cases: {beginWord, endWord, wordList, expected}
        Object[][] tests = new Object[][] {
                { "hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"), 5 },
                { "hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log"), 0 },
                { "a", "c", Arrays.asList("a", "b", "c"), 2 },
                { "hot", "dog", Arrays.asList("hot", "dog"), 0 },
                { "hot", "dog", Arrays.asList("hot", "dog", "dot"), 3 },
                { "lost", "miss", Arrays.asList("most", "mist", "miss"), 4 },
                { "talk", "tail", Arrays.asList("talk", "tons", "fall", "tail"), 0 },
                { "hot", "dog", Arrays.<String>asList(), 0 },
                { "leet", "code", Arrays.asList("lest", "leet", "lose", "code", "lode", "robe", "lost"), 6 },
                { "red", "tax", Arrays.asList("ted", "tex", "red", "tax", "tad", "den", "rex", "pee"), 4 }
        };

        System.out.println("Running tests for P_127_WordLadder.Solution.ladderLength (standard BFS)\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String begin = (String) tests[i][0];
            String end = (String) tests[i][1];
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) tests[i][2];
            int expected = (int) tests[i][3];

            P_127_WordLadder.Solution sol = outer.new Solution();
            int actual = sol.ladderLength(begin, end, list);
            boolean ok = actual == expected;
            if (ok) pass1++;
            System.out.printf("Test %d: begin=%s, end=%s, words=%s => expected=%d, actual=%d => %s%n",
                    i + 1, begin, end, list, expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_127_WordLadder.ladderLength (bidirectional BFS)\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String begin = (String) tests[i][0];
            String end = (String) tests[i][1];
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) tests[i][2];
            int expected = (int) tests[i][3];

            P_127_WordLadder freshOuter = new P_127_WordLadder();
            int actual = freshOuter.ladderLength(begin, end, list);
            boolean ok = actual == expected;
            if (ok) pass2++;
            System.out.printf("Test %d: begin=%s, end=%s, words=%s => expected=%d, actual=%d => %s%n",
                    i + 1, begin, end, list, expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("Solution.ladderLength (standard BFS):     %d/%d tests passed%n", pass1, tests.length);
        System.out.printf("ladderLength (bidirectional BFS):         %d/%d tests passed%n", pass2, tests.length);
    }
}
