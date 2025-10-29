'''
125. Valid Palindrome

DESCRIPTION:
A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all 
non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters 
and numbers.

Given a string s, return true if it is a palindrome, or false otherwise.
'''

'''
Time: O(n) - using 2 pointers, we have to iterate through at most n / 2 characters, but O(n / 2) is still O(n)
Space: O(1) - does not require additional data structures other than the 2 pointer variables
'''
class Solution:
    def isPalindrome(self, s: str) -> bool:
        left = 0
        right = len(s) - 1

        while left < right:
            while left < right and not s[left].isalnum():
                left += 1
            while left < right and not s[right].isalnum():
                right -= 1

            if s[left].lower() != s[right].lower():
                return False

            left += 1
            right -= 1

        return True

solution = Solution()
print(f'"A man, a plan, a canal: Panama" => {solution.isPalindrome("A man, a plan, a canal: Panama")}')
print(f'"race a car" => {solution.isPalindrome("race a car")}')
print(f'" " => {solution.isPalindrome(" ")}')