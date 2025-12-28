import utils.ListNode;

public class P_206_ReverseLinkedList {
    /*
    Iterative approach: we maintain three pointers: prev, curr, and next.
    We iterate through the linked list, reversing the next pointer of each node to point to the previous node.

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }

    // Helper method to create a linked list from an array
    private static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode head = new ListNode(values[0]);
        ListNode curr = head;
        for (int i = 1; i < values.length; i++) {
            curr.next = new ListNode(values[i]);
            curr = curr.next;
        }
        return head;
    }

    // Helper method to convert a linked list to an array
    private static int[] listToArray(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        java.util.List<Integer> list = new java.util.ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        P_206_ReverseLinkedList solver = new P_206_ReverseLinkedList();

        // Test cases: {input array, expected output array}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4, 5 }, new int[] { 5, 4, 3, 2, 1 } },
                { new int[] { 1, 2 }, new int[] { 2, 1 } },
                { new int[] { 1 }, new int[] { 1 } },
                { new int[] {}, new int[] {} },
                { new int[] { 1, 2, 3 }, new int[] { 3, 2, 1 } },
                { new int[] { 5, 10, 15, 20 }, new int[] { 20, 15, 10, 5 } },
                { new int[] { 10, 20, 30, 40, 50, 60 }, new int[] { 60, 50, 40, 30, 20, 10 } },
                { new int[] { -1, 0, 1 }, new int[] { 1, 0, -1 } },
                { new int[] { 7, 7, 7, 7 }, new int[] { 7, 7, 7, 7 } },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 } }
        };

        System.out.println("Running tests for P_206_ReverseLinkedList.reverseList\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            ListNode head = createList(input);
            ListNode result = solver.reverseList(head);
            int[] actual = listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("reverseList: %d/%d tests passed\n", pass1, tests.length);
    }
}