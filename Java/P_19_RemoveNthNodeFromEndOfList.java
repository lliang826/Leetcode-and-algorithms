import utils.ListNode;

public class P_19_RemoveNthNodeFromEndOfList {
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

    public static void main(String[] args) {

    }
}
