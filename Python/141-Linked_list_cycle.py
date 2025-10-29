'''
141. Linked list cycle

DESCRIPTION:
Given head, the head of a linked list, determine if the linked list has a cycle in it.
There is a cycle in a linked list if there is some node in the list that can be reached 
again by continuously following the next pointer. Internally, pos is used to denote 
the index of the node that tail's next pointer is connected to. Note that pos is not 
passed as a parameter.
Return true if there is a cycle in the linked list. Otherwise, return false.
'''
from typing import Optional


class ListNode:
    def __init__(self, value, next = None):
        self.val = value
        self.next = next

'''
Floyd's tortoise and hare algorithm
Analogy: imagine 2 runners on a track oval (which has a cycle). One of the runners is
faster than the other. Assuming that they maintain their speeds for the entire duration,
the fast runner will eventually overlap and meet the slow runner again.
Time: O(n)
Space: O(1)
'''

class Solution:
    def hasCycle(self, head: Optional[ListNode]) -> bool:
        slow = head
        fast = head

        while fast and fast.next:
            fast = fast.next.next
            slow = slow.next
            if fast == slow:
                return True

        return False
        

'''
Test cases:

head = [3,2,0,-4]
pos = 1
Output: true

head = [1,2]
pos = 0
Output: true

head = [1]
pos = -1
Output: false
'''
        

# def linkedListCycle(head: List[ListNode]):
#     fast = head[0]
#     slow = head[0]

#     while fast and fast.next:
#         fast = fast.next.next
#         slow = slow.next
#         if fast == slow:
#             return True
        
#     return False

# node4 = ListNode(-4)
# node3 = ListNode(0, node4)
# node2 = ListNode(2, node3)
# node1 = ListNode(3, node2)
# node4.next = node1

# list1 = [node1, node2, node3, node4]
# print(linkedListCycle(list1))