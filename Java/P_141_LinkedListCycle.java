import java.util.Arrays;

public class P_141_LinkedListCycle {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

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

    public static void main(String[] args) {
        P_141_LinkedListCycle solver = new P_141_LinkedListCycle();

        Object[][] tests = new Object[][] {
                { new int[] {3,2,0,-4}, 1, true },
                { new int[] {1,2}, 0, true },
                { new int[] {1}, -1, false },
                { new int[] {}, -1, false },
                { new int[] {1,2,3,4,5}, -1, false },
                { new int[] {1,2,3,4,5}, 2, true }
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
            if (ok) pass++;
            System.out.printf("Test %d: vals=%s, pos=%d => expected=%b, actual=%b => %s\n",
                    i + 1, Arrays.toString(vals), pos, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("hasCycle: %d/%d tests passed\n", pass, tests.length);
    }

    private static ListNode buildList(int[] vals, int pos) {
        if (vals.length == 0) return null;

        ListNode head = new P_141_LinkedListCycle().new ListNode(vals[0]);
        ListNode curr = head;
        ListNode cycleEntry = null;

        if (pos == 0) cycleEntry = head;

        for (int i = 1; i < vals.length; i++) {
            ListNode node = new P_141_LinkedListCycle().new ListNode(vals[i]);
            curr.next = node;
            curr = node;
            if (i == pos) cycleEntry = node;
        }

        if (pos >= 0) curr.next = cycleEntry;

        return head;
    }
}
