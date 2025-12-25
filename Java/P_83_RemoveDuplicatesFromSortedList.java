import utils.ListNode;

public class P_83_RemoveDuplicatesFromSortedList {
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

    }
}
