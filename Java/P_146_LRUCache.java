import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P_146_LRUCache {
    class LRUCache {
        class Node {
            int key;
            int val;
            Node next;
            Node prev;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        Map<Integer, Node> map;
        int capacity;
        Node head;
        Node tail;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            this.capacity = capacity;

            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }

            Node node = map.get(key);
            removeNode(node);
            addAfterHead(node);

            return node.val;
        }

        public void put(int key, int value) {
            Node node = new Node(key, value);

            if (map.containsKey(key)) {
                Node old = map.get(key);
                removeNode(old);
            }

            map.put(key, node);
            addAfterHead(node);

            if (map.size() > capacity) {
                Node toRemove = tail.prev;
                removeNode(toRemove);
                map.remove(toRemove.key);
            }
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addAfterHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    public static void main(String[] args) {
        P_146_LRUCache outer = new P_146_LRUCache();

        // Test cases: {capacity, operations, arguments, expected outputs (null for put), description}
        Object[][] tests = new Object[][] {
                { 2,
                        new String[] { "put", "put", "get", "put", "get", "put", "get", "get", "get" },
                        new int[][] { { 1, 1 }, { 2, 2 }, { 1 }, { 3, 3 }, { 2 }, { 4, 4 }, { 1 }, { 3 }, { 4 } },
                        new Integer[] { null, null, 1, null, -1, null, -1, 3, 4 },
                        "LC Example 1" },
                { 1,
                        new String[] { "put", "put", "get", "get" },
                        new int[][] { { 1, 1 }, { 2, 2 }, { 1 }, { 2 } },
                        new Integer[] { null, null, -1, 2 },
                        "Capacity 1: every new key evicts the previous one" },
                { 2,
                        new String[] { "put", "put", "put", "get", "get", "get" },
                        new int[][] { { 1, 10 }, { 2, 20 }, { 3, 30 }, { 1 }, { 2 }, { 3 } },
                        new Integer[] { null, null, null, -1, 20, 30 },
                        "Keys != values: eviction must remove by key, not value" },
                { 2,
                        new String[] { "put", "put", "put", "put", "get", "get", "get" },
                        new int[][] { { 1, 1 }, { 2, 2 }, { 1, 10 }, { 3, 3 }, { 1 }, { 2 }, { 3 } },
                        new Integer[] { null, null, null, null, 10, -1, 3 },
                        "Updating a key makes it most recent, so the other key is evicted" },
                { 2,
                        new String[] { "put", "put", "get", "put", "put", "get" },
                        new int[][] { { 2, 1 }, { 2, 2 }, { 2 }, { 1, 1 }, { 4, 1 }, { 2 } },
                        new Integer[] { null, null, 2, null, null, -1 },
                        "Updating an existing key does not trigger eviction" },
                { 2,
                        new String[] { "get", "put", "get" },
                        new int[][] { { 1 }, { 1, 1 }, { 1 } },
                        new Integer[] { -1, null, 1 },
                        "Get on empty cache returns -1" },
                { 2,
                        new String[] { "put", "put", "get", "put", "get", "get", "get" },
                        new int[][] { { 1, 1 }, { 2, 2 }, { 5 }, { 3, 3 }, { 1 }, { 2 }, { 3 } },
                        new Integer[] { null, null, -1, null, -1, 2, 3 },
                        "Get on a missing key does not change recency order" },
                { 3,
                        new String[] { "put", "put", "put", "put", "put", "get", "get", "get", "get", "get" },
                        new int[][] { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 1 }, { 2 }, { 3 }, { 4 },
                                { 5 } },
                        new Integer[] { null, null, null, null, null, -1, -1, 3, 4, 5 },
                        "Repeated evictions keep only the 3 most recent keys" },
                { 2,
                        new String[] { "put", "put", "get", "get", "put", "get", "get" },
                        new int[][] { { 1, 1 }, { 2, 2 }, { 1 }, { 2 }, { 3, 3 }, { 1 }, { 2 } },
                        new Integer[] { null, null, 1, 2, null, -1, 2 },
                        "Last get decides recency: key 1 is LRU after get(1), get(2)" }
        };

        System.out.println("Running tests for P_146_LRUCache.LRUCache\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int capacity = (int) tests[i][0];
            String[] ops = (String[]) tests[i][1];
            int[][] opArgs = (int[][]) tests[i][2];
            Integer[] expected = (Integer[]) tests[i][3];
            String desc = (String) tests[i][4];

            // Fresh cache per test case.
            P_146_LRUCache.LRUCache cache = outer.new LRUCache(capacity);
            Integer[] actual = new Integer[ops.length];
            for (int j = 0; j < ops.length; j++) {
                if (ops[j].equals("put")) {
                    cache.put(opArgs[j][0], opArgs[j][1]);
                    actual[j] = null;
                } else {
                    actual[j] = cache.get(opArgs[j][0]);
                }
            }

            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d (%s): capacity=%d%n  ops=%s%n  args=%s%n  expected=%s%n  actual=  %s%n  => %s%n%n",
                    i + 1, desc, capacity, Arrays.toString(ops), Arrays.deepToString(opArgs),
                    Arrays.toString(expected), Arrays.toString(actual), ok ? "PASS" : "FAIL");
        }

        System.out.println("=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("LRUCache: %d/%d tests passed%n", pass, tests.length);
    }
}
