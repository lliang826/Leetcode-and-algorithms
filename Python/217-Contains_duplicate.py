'''
217. Contains duplicate

DESCRIPTION:
Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.

Example 1:
Input: nums = [1,2,3,1]
Output: true
Explanation: The element 1 occurs at the indices 0 and 3.

Example 2:
Input: nums = [1,2,3,4]
Output: false
Explanation: All elements are distinct.

Example 3:
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true
'''

from typing import List

'''
Very easy problem. Use a hashset so lookup time is constant O(1). 
Iterate through the integer array: 
 - if the integer already exists in the hashset, return True because that's a duplicate
 - otherwise, add it to the hashset

Time: O(n) - iterating through all the integers in the array
Space: O(n) - we could be storing all the integers into the hashset except the last integer in the array,
            but O(n) - 1 is still O(n)
'''

class Solution:
    def containsDuplicate(self, nums: List[int]) -> bool:
        intSet = set()

        for num in nums:
            if num in intSet:
                return True
            else:
                intSet.add(num)

        return False
    
'''
Testcases above in problem description.
'''