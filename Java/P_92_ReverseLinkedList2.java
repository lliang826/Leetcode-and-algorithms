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
}
