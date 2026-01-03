import utils.ListNode;

public class P_92_ReverseLinkedList2 {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode start = dummy;
        for (int i = 0; i < left - 1; i++) {
            start = start.next;
        }

        ListNode curr = start.next;
        ListNode prev = null;
        while (curr != null && right - left >= 0) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            right--;
        }

        ListNode end = start.next;
        start.next = prev;
        end.next = curr;

        return dummy.next;
    }

    public ListNode v2(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode beforeLeft = dummy;
        for (int i = 0; i < left - 1; i++) {
            beforeLeft = beforeLeft.next;
        }

        ListNode curr = beforeLeft.next;
        ListNode prev = null;
        int length = right - left + 1;
        while (length > 0) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            length--;
        }

        ListNode end = beforeLeft.next;
        beforeLeft.next = prev;
        end.next = curr;

        return dummy.next;
    }

    public static void main(String[] args) {
        P_92_ReverseLinkedList2 solver = new P_92_ReverseLinkedList2();

        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4, 5 }, 2, 4, new int[] { 1, 4, 3, 2, 5 } },
                { new int[] { 5 }, 1, 1, new int[] { 5 } },
                { new int[] { 1, 2, 3 }, 1, 3, new int[] { 3, 2, 1 } },
                { new int[] { 1, 2, 3, 4, 5 }, 1, 5, new int[] { 5, 4, 3, 2, 1 } },
                { new int[] { 1, 2 }, 1, 2, new int[] { 2, 1 } },
                { new int[] { 1, 2, 3, 4, 5 }, 3, 5, new int[] { 1, 2, 5, 4, 3 } },
                { new int[] { 1, 2, 3, 4, 5 }, 1, 1, new int[] { 1, 2, 3, 4, 5 } },
                { new int[] { 1, 2, 3, 4, 5 }, 5, 5, new int[] { 1, 2, 3, 4, 5 } },
                { new int[] { 1, 2, 3, 4, 5, 6 }, 2, 5, new int[] { 1, 5, 4, 3, 2, 6 } },
                { new int[] { 3, 5 }, 1, 2, new int[] { 5, 3 } }
        };

        System.out.println("Running tests for P_92_ReverseLinkedList2.reverseBetween\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int left = (int) tests[i][1];
            int right = (int) tests[i][2];
            int[] expected = (int[]) tests[i][3];
            ListNode head = ListNode.createList(input);
            ListNode result = solver.reverseBetween(head, left, right);
            int[] actual = ListNode.listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s, left=%d, right=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), left, right,
                    java.util.Arrays.toString(expected), java.util.Arrays.toString(actual),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_92_ReverseLinkedList2.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int left = (int) tests[i][1];
            int right = (int) tests[i][2];
            int[] expected = (int[]) tests[i][3];
            ListNode head = ListNode.createList(input);
            ListNode result = solver.v2(head, left, right);
            int[] actual = ListNode.listToArray(result);

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s, left=%d, right=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), left, right,
                    java.util.Arrays.toString(expected), java.util.Arrays.toString(actual),
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("reverseBetween: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
