package utils;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    // Helper method to create a linked list from an array
    public static ListNode createList(int[] values) {
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
    public static int[] listToArray(ListNode head) {
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

    // Helper method to get the value of a node
    public static int getNodeValue(ListNode node) {
        return node != null ? node.val : -1;
    }
}