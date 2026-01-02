import utils.ListNode;

public class P_876_MiddleOfTheLinkedList {
    /*
    Pretty straightforward solution: we iterate through the entire linked list to find its length,
    then we iterate halfway to length/2 to find the middle node.

    Time: O(n + n/2) => O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode middleNode(ListNode head) {
        int length = 0;
        ListNode curr = head;

        while (curr != null) {
            curr = curr.next;
            length++;
        }

        int mid = length / 2;
        while (mid > 0) {
            head = head.next;
            mid--;
        }

        return head;
    }

    /*
    Optimal solution: we use two pointers, one moving twice as fast as the other.
    When the fast pointer reaches the end of the list, the slow pointer will be at the middle node.
    fast = slow * 2 => slow = fast/2 => therefore, when fast = n (length of the list), slow = n/2

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode v2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        P_876_MiddleOfTheLinkedList solver = new P_876_MiddleOfTheLinkedList();

        // Test cases: {input array, expected middle value}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 3, 4, 5 }, 3 },
                { new int[] { 1, 2, 3, 4, 5, 6 }, 4 },
                { new int[] { 1 }, 1 },
                { new int[] { 1, 2 }, 2 },
                { new int[] { 1, 2, 3 }, 2 },
                { new int[] { 1, 2, 3, 4 }, 3 },
                { new int[] { 5, 10, 15, 20, 25, 30, 35 }, 20 },
                { new int[] { 10, 20 }, 20 },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 5 },
                { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 6 }
        };

        System.out.println("Running tests for P_876_MiddleOfTheLinkedList.middleNode\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            ListNode head = ListNode.createList(input);
            ListNode actual = solver.middleNode(head);
            int actualValue = ListNode.getNodeValue(actual);

            boolean ok = expected == actualValue;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actualValue,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_876_MiddleOfTheLinkedList.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            ListNode head = ListNode.createList(input);
            ListNode actual = solver.v2(head);
            int actualValue = ListNode.getNodeValue(actual);

            boolean ok = expected == actualValue;
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actualValue,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("middleNode: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
