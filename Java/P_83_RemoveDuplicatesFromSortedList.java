public class P_83_RemoveDuplicatesFromSortedList {
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
    

}
