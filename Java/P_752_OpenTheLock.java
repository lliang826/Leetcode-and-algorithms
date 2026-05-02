import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class P_752_OpenTheLock {
    class Solution {
        public int openLock(String[] deadends, String target) {
            Queue<String> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            int count = 0;

            for (String d : deadends) {
                if (d.equals("0000")) {
                    return -1;
                }
                seen.add(d);
            }

            queue.offer("0000");
            seen.add("0000");

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    String node = queue.poll();

                    if (node.equals(target)) {
                        return count;
                    }

                    for (String turn : this.getNextTurns(node)) {
                        if (!seen.contains(turn)) {
                            queue.offer(turn);
                            seen.add(turn);
                        }
                    }
                }

                count++;
            }

            return -1;
        }

        private String[] getNextTurns(String node) {
            List<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder(node);

            for (int i = 0; i < node.length(); i++) {
                char c = node.charAt(i);

                char rollWheelUp = c == '9' ? '0' : (char) (c + 1);
                sb.setCharAt(i, rollWheelUp);
                list.add(sb.toString());

                char rollWheelDown = c == '0' ? '9' : (char) (c - 1);
                sb.setCharAt(i, rollWheelDown);
                list.add(sb.toString());

                sb.setCharAt(i, c);
            }

            return list.toArray(new String[0]);
        }
    }

    class Solution2 {
        public int openLock(String[] deadends, String target) {
            Queue<String> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            int count = 0;

            for (String d : deadends) {
                if (d.equals("0000")) {
                    return -1;
                }
                seen.add(d);
            }

            queue.offer("0000");
            seen.add("0000");

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    String node = queue.poll();

                    if (node.equals(target)) {
                        return count;
                    }

                    for (String turn : this.getNextTurns(node)) {
                        if (!seen.contains(turn)) {
                            queue.offer(turn);
                            seen.add(turn);
                        }
                    }
                }

                count++;
            }

            return -1;
        }

        private String[] getNextTurns(String node) {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < node.length(); i++) {
                char c = node.charAt(i);

                char rollUp = c == '9' ? '0' : (char) (c + 1);
                String rollUpString = node.substring(0, i) + rollUp + node.substring(i + 1);
                list.add(rollUpString);

                char rollDown = c == '0' ? '9' : (char) (c - 1);
                String rollDownString = node.substring(0, i) + rollDown + node.substring(i + 1);
                list.add(rollDownString);
            }

            return list.toArray(new String[0]);
        }
    }

    /*
     * 0000
     * 
     * 1000 9000
     * 0100 0900
     * 0010 0090
     * 0001 0009
     * 
     * 
     * 9000
     * 
     * 0000
     * 
     * 
     * add 1, mod 10
     * minus 1, mod 10
     */
}
