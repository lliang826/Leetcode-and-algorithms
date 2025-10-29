'''
2. Add two numbers

DESCRIPTION:
You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse 
order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example 1:
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.

Example 2:
Input: l1 = [0], l2 = [0]
Output: [0]

Example 3:
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]
'''

from math import floor
from typing import Callable, List, Optional

# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

'''
My solution for this problem (definitely not the best). Instead of dealing with the carry overs and the 
modulus that would come with adding 2 numbers, I iterate through the linked lists and convert the values 
into integers before performing the addition. To do so, I convert each value to a string and concatenate them. 
Since the digits are stored in reverse order, I using slicing to reverse the strings before converting them 
to integers. The sum is finally returned as a new linked list.

The problem with this solution is that it doesn't work for very large integers: adding 2 large integers
could cause an integer overflow.

Time: O(n) - we need to iterate through all the digits of l1, l2 and the sum (whichever is longest)
Space: O(n) - a new linked list is created for the sum
'''
class Solution:
    def addTwoNumbers1(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        firstString = ""
        while l1:
            firstString += str(l1.val)
            l1 = l1.next

        secondString = ""
        while l2:
            secondString += str(l2.val)
            l2 = l2.next
        
        firstString = firstString[::-1]
        secondString = secondString[::-1]

        firstInt = int(firstString)
        secondInt = int(secondString)

        sum = firstInt + secondInt
        stringSum = str(sum)

        head = ListNode(0)
        curr = head
        for i in range(len(stringSum) - 1, -1, -1):
            node = ListNode(int(stringSum[i]))
            curr.next = node
            curr = curr.next

        return head.next
    

    '''
    This is the ideal solution that involves carrying over any 1s. In this solution, we are adding 1 digit from each
    linked list in each iteration, plus the carry over. To get the carry over, we have to find the floor of the sum
    divided by 10. The digit inserted into the new node is simply the remainder of this division operation (given by
    the modulus operator).

    Some tricky parts (edge cases):
        - for cases where one linked list has more digits than the other, we have to set the digit to 0 for the current
            node in the shorter linked list (lines 98 + 99)
        - the 'while' condition must check if the carryOver has a value: if the sum of the last digits is > 9, this
            carryOver should be included in the answer

    Time: O(n) - iterating through the length of the linked lists, where n is the length of the longest list
    Space: O(n) - creating a new linked list for the sum
    '''
    def addTwoNumbers2(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        head = ListNode(0)
        curr = head
        carryOver = 0

        while l1 or l2 or carryOver:
            num1 = 0 if l1 is None else l1.val
            num2 = 0 if l2 is None else l2.val

            sum = num1 + num2 + carryOver
            carryOver = floor(sum / 10)
            curr.next = ListNode(sum % 10)
            curr = curr.next

            l1 = l1.next if l1 else None
            l2 = l2.next if l2 else None
        
        return head.next
    
    
    def listToLinkedList(self, arrayList: List[int]) -> List[ListNode]:
        head = ListNode(0)
        curr = head
        for i in arrayList:
            node = ListNode(i)
            curr.next = node
            curr = curr.next

        return head.next
    

    def printSum(self, l1: List[int], l2: List[int], func: Callable) -> None:
        linkedList1 = self.listToLinkedList(l1)
        linkedList2 = self.listToLinkedList(l2)
        head = func(linkedList1, linkedList2)
        array = []
        while head:
            array.append(head.val)
            head = head.next
        print(array)
    

    
solution = Solution()

solution.printSum([2,4,3], [5,6,4], solution.addTwoNumbers1)
solution.printSum([2,4,3], [5,6,4], solution.addTwoNumbers2)

solution.printSum([0], [0], solution.addTwoNumbers1)
solution.printSum([0], [0], solution.addTwoNumbers2)

solution.printSum([9,9,9,9,9,9,9], [9,9,9,9], solution.addTwoNumbers1)
solution.printSum([9,9,9,9,9,9,9], [9,9,9,9], solution.addTwoNumbers2)