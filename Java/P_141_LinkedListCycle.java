import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import data_structures.ListNode;

public class P_141_LinkedListCycle {
    /*
     * Floyd's tortoise and hare algorithm.
     * By implementing a fast pointer (which increments by 2 nodes per iteration)
     * and a slow pointer (which increments by 1 node per iteration), we can detect
     * if a linked list contains a cycle.
     * Analogy: a fast runner and a slow runner on a racetrack. If the fast runner
     * overlaps the slow runner there is a cycle/loop.
     * 
     * Time: O(n), where n is the number of nodes in the linked list
     * Space: O(1), constant because there is no additional data structure, we only
     * use 2 pointers
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /*
     * Alternate solution that uses a hashset instead. The set tracks the nodes that
     * we've previously visited.
     * 
     * Time: O(n), same as above
     * Space: O(n), we place all nodes into the hash set
     */
    public boolean v2(ListNode head) {
        Set<ListNode> set = new HashSet<>();

        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }

        return false;
    }

    public static void main(String[] args) {
        P_141_LinkedListCycle solver = new P_141_LinkedListCycle();

        Object[][] tests = new Object[][] {
                { new int[] { 3, 2, 0, -4 }, 1, true },
                { new int[] { 1, 2 }, 0, true },
                { new int[] { 1 }, -1, false },
                { new int[] {}, -1, false },
                { new int[] { 1, 2, 3, 4, 5 }, -1, false },
                { new int[] { 1, 2, 3, 4, 5 }, 2, true }
        };

        System.out.println("Running tests for P_141_LinkedListCycle.hasCycle\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] vals = (int[]) tests[i][0];
            int pos = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];

            ListNode head = buildList(vals, pos);
            boolean actual = solver.hasCycle(head);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: vals=%s, pos=%d => expected=%b, actual=%b => %s\n",
                    i + 1, Arrays.toString(vals), pos, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_141_LinkedListCycle.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] vals = (int[]) tests[i][0];
            int pos = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];

            ListNode head = buildList(vals, pos);
            boolean actual = solver.v2(head);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: vals=%s, pos=%d => expected=%b, actual=%b => %s\n",
                    i + 1, Arrays.toString(vals), pos, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("hasCycle: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }

    private static ListNode buildList(int[] vals, int pos) {
        if (vals.length == 0)
            return null;

        ListNode head = new ListNode(vals[0]);
        ListNode curr = head;
        ListNode cycleEntry = null;

        if (pos == 0)
            cycleEntry = head;

        for (int i = 1; i < vals.length; i++) {
            ListNode node = new ListNode(vals[i]);
            curr.next = node;
            curr = node;
            if (i == pos)
                cycleEntry = node;
        }

        if (pos >= 0)
            curr.next = cycleEntry;

        return head;
    }
}
