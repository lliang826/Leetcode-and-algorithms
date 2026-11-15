import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P_146_LRUCache {
    /*
    LRU Cache: evicting the least recently used key when capacity is reached

    To implement both the get() and put() methods in O(1) constant time, we need
    both a hashmap and a linked list. For the get() method, the hashmap allows 
    O(1) constant time lookup. For put(), we need a data structure that allows us
    to evict the least recently used key, and there are many different ways to do
    this:
    - each key:value pair in the hashmap can also include a timestamp, but iterating
    through each key:value pair to find the LRU key would be O(n) time
    - we could use a Queue data structure, where new and recently used keys are 
    always inserted at the end of the queue, and the LRU can be found at the front, 
    but we can't access keys in the middle of the queue for scenarios where a used
    key gets used again (if a key in the cache is used again, it needs to move to
    the end of the queue)
    - an array (or ArrayList) could solve the Queue's problem; with an array, we 
    can access keys in the middle. But moving a key from the middle to the end
    requires us to shift the rest of the elements, which would be O(n) time
    - a LinkedList is the best fit for this problem. By having head and tail dummy
    nodes, we can easily add new and recently used keys at the head, and we can 
    evict the LRU key from the tail. We can also access keys in the middle by having
    the hashmap point to each node; moving a node from the middle to the head of 
    the list only requires reassigning pointers, no shifting involved

    But even though the concept of using a hashmap with a linked list is easy,
    there are a lot of things that can go wrong in this problem, which makes it
    tricky to implement. 

    Some things to note: 
    - must use a doubly linked list: when we move a node in the middle to the head,
    we need to set the previous node's next pointer to the next node. Without the
    prev pointer in a doubly linked list, we have to iterate through the entire list
    to find prev, which is O(n)
    - having the head and tail dummy nodes means we don't have to check for null, 
    which makes things easier and cleaner
    - when first thinking about this problem, it's easy to conceptualize that the 
    hashmap holds the keys, and the linked list holds the values; each hashmap key
    points to a linked list node. But actually, each linked list node must hold the
    key value too: when we evict the LRU from the linked list's tail, we have the
    value, but we also need to remove the entry from the hashmap; where do we get 
    the key? So each node holds both the key and the value

    Time:
    - O(1) for get()
    - O(1) for put()

    Space: O(capacity)
    - O(capacity) for the hashmap
    - O(capacity) for the linked list
    */
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
