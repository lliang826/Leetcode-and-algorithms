import utils.ListNode;

public class P_83_RemoveDuplicatesFromSortedList {
    /*
    One-pass approach with tracking: We use two pointers where 'right' traverses each node, and 'left' 
    tracks the last unique node. When 'right' finds a duplicate, we skip it by setting left.next to 
    right.next. When 'right' finds a new unique value, we advance 'left' to 'right'.

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode left = head;
        ListNode right = head;

        while (right.next != null) {
            right = right.next;
            if (right.val == left.val) {
                left.next = right.next;
            } else {
                left = right;
            }
        }

        return head;
    }

    /*
    Optimal solution: we use a single pointer to traverse the list. If the current node's value is the same
    as the next node's value, we skip the next node by adjusting the current node's next pointer. Otherwise, we
    move to the next node.

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode v2(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        P_83_RemoveDuplicatesFromSortedList solver = new P_83_RemoveDuplicatesFromSortedList();

        // Test cases: {input array, expected output array}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 1, 2 }, new int[] { 1, 2 } },
                { new int[] { 1, 1, 2, 3, 3 }, new int[] { 1, 2, 3 } },
                { new int[] { 1, 1, 1 }, new int[] { 1 } },
                { new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 } },
                { new int[] { 1 }, new int[] { 1 } },
                { new int[] { 1, 1, 1, 2, 2, 3, 3, 3, 4 }, new int[] { 1, 2, 3, 4 } },
                { new int[] { 0, 0, 0, 0 }, new int[] { 0 } },
                { new int[] { -3, -1, 0, 0, 0, 3, 3 }, new int[] { -3, -1, 0, 3 } },
                { new int[] { 1, 1, 1, 2, 3, 3, 4, 4, 4, 5 }, new int[] { 1, 2, 3, 4, 5 } },
                { new int[] { 5, 5, 5, 5, 5 }, new int[] { 5 } }
        };

        System.out.println("Running tests for P_83_RemoveDuplicatesFromSortedList.deleteDuplicates\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            ListNode head = createList(input);
            ListNode result = solver.deleteDuplicates(head);
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

        System.out.println("\nRunning tests for P_83_RemoveDuplicatesFromSortedList.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            ListNode head = createList(input);
            ListNode result = solver.v2(head);
            int[] actual = listToArray(result);

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
        System.out.printf("deleteDuplicates: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
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
}
