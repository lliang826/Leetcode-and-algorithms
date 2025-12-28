import utils.ListNode;

public class P_24_SwapNodesInPairs {
    /*
    My initial solution for this problem: we maintain a current pointer to traverse the list and a previous pointer
    to keep track of the last node of the previous swapped pair. For each pair of nodes, we swap their next pointers 
    and adjust the previous node's next pointer accordingly. If the previous node is not null, we link it to the 
    second node of the current pair after swapping. If the current node is the head, we update the head to point to 
    the new first node of the swapped pair. We continue this process until we reach the end of the list. 

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
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

    /*
    Cleaner version using a dummy node to simplify edge cases; having the dummy node allows us to avoid
    checking if we are at the head of the list during each swap, and also makes linking the previous node
    to the swapped pair more straightforward.

    Time: O(n), where n is the number of nodes in the linked list
    Space: O(1), we only use a constant amount of extra space
    */
    public ListNode v2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = first.next;

            first.next = second.next;
            second.next = first;
            prev.next = second;

            prev = first;
        }

        return dummy.next;
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

    public static void main(String[] args) {
        
    }
}
