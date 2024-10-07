from typing import List, Optional

'''
206. Reverse Linked List

DESCRIPTION:
Given the head of a singly linked list, reverse the list, and return the reversed list.
'''

# Definition for singly-linked list
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

'''
Using pen and paper makes this iterative solution easier to understand.

There are 2 pointers required: 'prev' tracks the previous node, and 'temp' tracks the next. In each
iteration, 'temp' is used to track the next node, while the pointer for head.next is moved to 'prev'.
Then, we move the prev and head pointers up by one. The important thing to remember is to return
prev, not head. Since the last node in the list is pointing to null, when the loop completes, head
will be pointing to null too. 'prev' will be pointing to the last node in the list.

Time: O(n) - need to iterate through the entire linked list
Space: O(1) - no additional data structures
'''
class Solution:
    def reverseList(self, head: Optional[ListNode]) -> Optional[ListNode]:
        prev = None
        while head:
            temp = head.next
            head.next = prev
            prev = head
            head = temp

        return prev