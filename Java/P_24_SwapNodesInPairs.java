import utils.ListNode;

public class P_24_SwapNodesInPairs {
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

    public static void main(String[] args) {

    }
}
