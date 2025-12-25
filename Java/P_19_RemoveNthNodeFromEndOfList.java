public class P_19_RemoveNthNodeFromEndOfList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode right = head;
        ListNode left = head;
        ListNode prev = head;
        int count = 0;

        while (count < n) {
            if (right == null) {
                return head;
            }
            right = right.next;
            count++;
        }

        while (right != null) {
            right = right.next;
            prev = left;
            left = left.next;
        }

        if (prev == left) {
            return head.next;
        }

        prev.next = left.next;
        return head;
    }

    public static void main(String[] args) {

    }
}
