'''
1. Two sum

DESCRIPTION:
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.
'''

from typing import List

'''
In each iteration of the for loop, check if the difference between the current number and the target exists in the hashmap.
Since the hashmap stores each number and its index as the key-value pair, lookup is instantaneous O(1) and we can return
the 2 indices as the answer. If the difference does not exist in the hashmap, store the current number and its index in the
hashmap for future iterations.

Time: O(n) - we could potentially iterate through the entire list
Space: O(n) - we could potentially store all the items in the list except for the last one, but O(n - 1) is still O(n)
'''
class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        # hashmap
        # number: index
        dict = {}

        for i in range(len(nums)):
            currNum = nums[i]
            diff = target - currNum
            if diff in dict:
                return [dict[diff], i]
            else:
                dict[currNum] = i

'''
Test cases:
nums = [2,7,11,15]
nums = [3,2,4]
nums = [3,3]
'''