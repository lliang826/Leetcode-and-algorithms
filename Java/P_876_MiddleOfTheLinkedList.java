public class P_876_MiddleOfTheLinkedList {
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
        
    }
}
