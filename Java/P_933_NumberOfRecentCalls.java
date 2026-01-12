import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class P_933_NumberOfRecentCalls {
    /*
    Initial implementation using Deque. We maintain a queue of timestamps for each ping.
    When a new ping arrives, we add its timestamp to the queue and remove any timestamps
    that are outside the 3000 milliseconds window [t-3000, t]. The size of the queue after
    this process gives us the number of recent calls.

    Time: O(n) for n pings, each ping operation is O(1) on average since each timestamp is
    added and removed at most once.
    Space: O(n) in the worst case, where all pings are within the 3000 milliseconds window 
    so they are all stored in the queue.
    */
    class RecentCounter {
        Deque<Integer> queue;

        public RecentCounter() {
            this.queue = new ArrayDeque<>();
        }
        
        public int ping(int t) {
            this.queue.addLast(t);
            int first = this.queue.peek();
            while (first < t - 3000 || first > t) {
                this.queue.pop();
                first = this.queue.peek();
            }
            return this.queue.size();
        }
    }

    /*
    Cleaner implementation using Queue interface. The logic remains the same as above, but
    we use the Queue interface because we only need to use offer(), poll(), and peek() methods.
    Using the Deque interface is unnecessary because it provides additional methods that we
    don't need, like addFirst() and removeLast(). We should only use the most specific interface
    that provides the methods we need.

    We also shouldn't use Queue<Integer> queue = new LinkedList<>(); because LinkedList has
    higher overhead compared to ArrayDeque. ArrayDeque is implemented as a resizable array,
    which provides better cache locality and performance for queue operations. LinkedList, on
    the other hand, is implemented as a doubly linked list, which requires more memory for
    storing pointers and has worse cache locality.

    This solution also simplifies the condition in the while loop. Since the problem states
    that "Each test case will call ping with strictly increasing values of t.", we don't need
    to check for first > t.

    Since this solution implements the Queue interface, we use the poll() method to remove
    the head of the queue instead of pop(), and offer() to add to the tail instead of addLast().
    poll() and offer() are better because they don't throw exceptions when the queue is empty 
    or full, respectively. Instead, they return null or false, which is more graceful handling.

    Time and space complexities remain the same as above.
    */
    class RecentCounter2 {
        private Queue<Integer> queue;

        public RecentCounter2() {
            this.queue = new ArrayDeque<>();
        }
        
        public int ping(int t) {
            this.queue.offer(t);
            while (this.queue.peek() < t - 3000) {
                this.queue.poll();
            }
            return this.queue.size();
        }
    }

    public static void main(String[] args) {
        P_933_NumberOfRecentCalls solver = new P_933_NumberOfRecentCalls();

        // Test cases: each test is an array of [timestamp, expected_count]
        int[][][] testSets = new int[][][] {
            // Test 1: Example from LeetCode
            {
                {1, 1},
                {100, 2},
                {3001, 3},
                {3002, 3}
            },
            // Test 2: All pings within window
            {
                {1, 1},
                {10, 2},
                {100, 3},
                {1000, 4},
                {2000, 5}
            },
            // Test 3: Pings fall out of window
            {
                {1, 1},
                {3002, 1},
                {6003, 1},
                {9004, 1}
            },
            // Test 4: Single ping
            {
                {1, 1}
            },
            // Test 5: Boundary case - exactly 3000ms apart
            {
                {1, 1},
                {3001, 2},
                {6001, 2},
                {9001, 2}
            },
            // Test 6: Multiple pings at same timestamp + 1
            {
                {1000, 1},
                {1001, 2},
                {1002, 3},
                {4002, 2},
                {4003, 2}
            },
            // Test 7: Large gaps
            {
                {1, 1},
                {10000, 1},
                {20000, 1}
            }
        };

        System.out.println("Running tests for P_933_NumberOfRecentCalls.RecentCounter\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            RecentCounter counter = solver.new RecentCounter();
            int[][] testCase = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testCase.length; j++) {
                int timestamp = testCase[j][0];
                int expected = testCase[j][1];
                int actual = counter.ping(timestamp);
                
                boolean ok = expected == actual;
                totalTests1++;
                if (ok) pass1++;
                
                System.out.printf("  ping(%d) => expected=%d, actual=%d => %s\n",
                        timestamp, expected, actual, (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_933_NumberOfRecentCalls.RecentCounter2\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            RecentCounter2 counter = solver.new RecentCounter2();
            int[][] testCase = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testCase.length; j++) {
                int timestamp = testCase[j][0];
                int expected = testCase[j][1];
                int actual = counter.ping(timestamp);
                
                boolean ok = expected == actual;
                totalTests2++;
                if (ok) pass2++;
                
                System.out.printf("  ping(%d) => expected=%d, actual=%d => %s\n",
                        timestamp, expected, actual, (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("RecentCounter: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("RecentCounter2: %d/%d tests passed\n", pass2, totalTests2);
    }
}