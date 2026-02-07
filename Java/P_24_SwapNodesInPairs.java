import data_structures.ListNode;

public class P_24_SwapNodesInPairs {
    /*
    My initial solution for this problem: we maintain a current pointer to traverse the list and a previous pointer
    to keep track of the last node of the previous swapped pair. For each pair of nodes, we swap their next pointers 
    and adjust the previous node's next pointer accordingly. If the previous node is not null, we link it to the 
    second node of the current pair after swapping. If the current node is the head, we update the head to point to 
    the new first node of the swapped pair. We continue this process until we reach the end of the list. 

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode swapPairs(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;

        while (curr != null && curr.next != null) {
            ListNode next = curr.next.next;
            curr.next.next = curr;

            if (prev != null) {
                prev.next = curr.next;
            }

            if (curr == head) {
                head = curr.next;
            }

            prev = curr;
            curr.next = next;
            curr = next;
        }

        return head;
    }

    /*
    Cleaner version using a dummy node to simplify edge cases; having the dummy node allows us to avoid
    checking if we are at the head of the list during each swap, and also makes linking the previous node
    to the swapped pair more straightforward.

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode v2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = first.next;

            first.next = second.next;
            second.next = first;
            prev.next = second;

            prev = first;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        P_24_SwapNodesInPairs solver = new P_24_SwapNodesInPairs();

        // Test cases: {input array, expected output array}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4 }, new int[] { 2, 1, 4, 3 } },
                { new int[] { 1, 2, 3 }, new int[] { 2, 1, 3 } },
                { new int[] { 1 }, new int[] { 1 } },
                { new int[] {}, new int[] {} },
                { new int[] { 1, 2 }, new int[] { 2, 1 } },
                { new int[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 1, 4, 3, 6, 5 } },
                { new int[] { 1, 2, 3, 4, 5 }, new int[] { 2, 1, 4, 3, 5 } },
                { new int[] { 10, 20, 30, 40, 50, 60, 70, 80 }, new int[] { 20, 10, 40, 30, 60, 50, 80, 70 } },
                { new int[] { 5, 10 }, new int[] { 10, 5 } },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, new int[] { 2, 1, 4, 3, 6, 5, 8, 7, 10, 9 } }
        };

        System.out.println("Running tests for P_24_SwapNodesInPairs.swapPairs\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            ListNode head = ListNode.createList(input);
            ListNode result = solver.swapPairs(head);
            int[] actual = ListNode.listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_24_SwapNodesInPairs.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            ListNode head = ListNode.createList(input);
            ListNode result = solver.v2(head);
            int[] actual = ListNode.listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("swapPairs: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
