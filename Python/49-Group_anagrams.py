'''
49. Group anagrams

Given an array of strings strs, group the anagrams together. You can return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, using all the original letters exactly once.

Constraints:
    - 1 <= strs.length <= 104
    - 0 <= strs[i].length <= 100
    - strs[i] consists of lowercase English letters.
'''

from collections import defaultdict
from typing import List


class Solution:
    '''
    This solution requires a hashmap (or dictionary in Python), where the key:value pair is [# of letter occurences]: [string].
    The tricky part of this problem is determing which strings are anagrams of each other. This problem is very similar to 
    242. Valid anagram, but in problem 242, I used a hashmap for the solution. Initially, I tried to come up with a nested
    hashmap solution, but having the inner hashmap as the key doesn't work; it only works as the value.

    A smarter solution is to use an array to keep track of the letters. Anagrams can be determined by matching all the letters 
    in the strings, including duplicates. Since the input array of strings contains only lowercase letters, this can be done by 
    creating an array of 26 zeroes, where each zero represents one of the 26 letters in the alphabet according to the array index. 
    When iterating through the characters in the string, the integer value at each index can be incremented to represent the 
    corresponding letter. 

    Once a string has been converted to its integer array representation, it can be stored in the hashmap; anagrams will be
    grouped together so the result can be found by returning the dictionary.values() function. 

    Some other things to note:
        - the ord() function in Python returns the decimal integer Unicode/Ascii representation of a character, and subtracting
        ord(char) by ord('a') returns the index (e.g., 'a' - 'a' = 97 - 97 = 0)
        - defaultdict() is very important/useful to know: it's a subclass of the dictionary class and it works the same way, except
        that it provides a default value for keys that do not exist. The argument passed in could be "int", "str", "list", or a 
        lambda function
            foo = defaultdict(int)
            print(foo["apple"])
        - In Python, dictionary keys cannot be mutable. Lists are mutable, but tuples are immutable, so the integer array has to be
        cast as a tuple in order for the code to work

    Time: O(m * n), where m is the length of the input string array, and n is the average length of each string
    Space: O(n), where n is the length of the input string array (if the input string array has no anagrams, we would need to create
        a new [int]: [string] record in the dictionary for each string because they're all different)
    '''
    def groupAnagrams1(self, strs: List[str]) -> List[List[str]]:
        dict = defaultdict(list)

        for str in strs:
            chars = [0] * 26

            for char in str:
                chars[ord(char) - ord('a')] += 1

            dict[tuple(chars)].append(str)

        return list(dict.values())
    

    '''
    Another solution: instead of using an array to keep track of the letters, sorting the characters in a string is also a way to
    determine anagrams. Sorting the characters in a string will rearrange them alphabetically, which means that anagrams will have
    the same sorted string. This solution also uses a hashmap, where the key:value pair is sortedString: [string]. In addition, this
    solution is slightly less efficient than the one above because sorting is an O(n log n) operation.

    Some things to note:
        - sort() is a method of the list class and can only be used for lists (e.g. strList.sort()). It returns the sorted list in place
        - sorted() can be used on any iterable and it returns a new sorted list, leaving the original unchanged

    Time: O(m * n log n), where m is the length of the input string array, and n log n is for sorting the characters in each string
    Space: O(n), where n is the length of the input string array (if every string is different and there are no anagrams, there would
        be a new hashmap record for each string)
    '''
    def groupAnagrams2(self, strs: List[str]) -> List[List[str]]:
        d = defaultdict(list)

        for s in strs:
            letters = str(sorted(s))
            d[letters].append(s)

        return list(d.values())
    
solution = Solution()

input1 = ["eat","tea","tan","ate","nat","bat"]
print(solution.groupAnagrams1(input1))
print(solution.groupAnagrams2(input1))

input2 = [""]
print(solution.groupAnagrams1(input2))
print(solution.groupAnagrams2(input2))

input3 = ["a"]
print(solution.groupAnagrams1(input3))
print(solution.groupAnagrams2(input3))

input4 = ["ddddddddddg","dgggggggggg"]
print(solution.groupAnagrams1(input4))
print(solution.groupAnagrams2(input4))