import data_structures.ListNode;

public class P_19_RemoveNthNodeFromEndOfList {
    /*
    2 pass approach. First, we iterate through all the nodes in the linked list to find the total
    number of nodes. Then, we iterate to the node before the nth node from the end, and we remove
    the nth node from the list.
    
    Time: O(n + n) => O(n), in the worst case scenario, the node we want to remove is the last 
    node in the list, which means we have to iterate through all nodes in the list twice
    Space: O(1), constant space, only pointers
    */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int count = 0;
        ListNode curr = head;

        while (curr != null) {
            curr = curr.next;
            count++;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        int i = count - n;
        while (i > 0) {
            prev = prev.next;
            i--;
        }

        prev.next = prev.next.next;
        return dummy.next;
    }

    /*
    Improved 1 pass approach. We initialize fast and slow pointers. The fast pointer is n + 1 nodes
    ahead of the slow pointer, so when the fast pointer reaches the end of the list, the slow pointer
    will be at the node before the nth node from the end.
    
    Time: O(n), only a single pass, but we still have to iterate through the entire linked list
    Space: O(1), constant space
    */
    public ListNode v2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        for (int i = 0; i < n + 1; i++) {
            fast = fast.next;
        }

        ListNode slow = dummy;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        P_19_RemoveNthNodeFromEndOfList solver = new P_19_RemoveNthNodeFromEndOfList();

        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4, 5 }, 2, new int[] { 1, 2, 3, 5 } },
                { new int[] { 1 }, 1, new int[] {} },
                { new int[] { 1, 2 }, 1, new int[] { 1 } },
                { new int[] { 1, 2 }, 2, new int[] { 2 } },
                { new int[] { 1, 2, 3, 4, 5 }, 1, new int[] { 1, 2, 3, 4 } },
                { new int[] { 1, 2, 3, 4, 5 }, 5, new int[] { 2, 3, 4, 5 } },
                { new int[] { 1, 2, 3 }, 3, new int[] { 2, 3 } },
                { new int[] { 1, 2, 3 }, 2, new int[] { 1, 3 } },
                { new int[] { 1, 2, 3, 4 }, 2, new int[] { 1, 2, 4 } },
                { new int[] { 1, 2, 3, 4, 5, 6 }, 3, new int[] { 1, 2, 3, 5, 6 } }
        };

        System.out.println("Running tests for P_19_RemoveNthNodeFromEndOfList.removeNthFromEnd\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int n = (int) tests[i][1];
            int[] expected = (int[]) tests[i][2];
            ListNode head = ListNode.createList(input);
            ListNode result = solver.removeNthFromEnd(head, n);
            int[] actual = ListNode.listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s, n=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), n,
                    java.util.Arrays.toString(expected), java.util.Arrays.toString(actual),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_19_RemoveNthNodeFromEndOfList.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int n = (int) tests[i][1];
            int[] expected = (int[]) tests[i][2];
            ListNode head = ListNode.createList(input);
            ListNode result = solver.v2(head, n);
            int[] actual = ListNode.listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s, n=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), n,
                    java.util.Arrays.toString(expected), java.util.Arrays.toString(actual),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("removeNthFromEnd: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
