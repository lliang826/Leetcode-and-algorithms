'''
287. Find the duplicate number

DESCRIPTION:
Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
There is only one repeated number in nums, return this repeated number.
You must solve the problem without modifying the array nums and uses only constant extra space.
'''

test1 = [1,3,4,2,2]
test2 = [3,1,3,4,2]
test3 = [5,4,3,2,1] # question guarantees one repeated number, but let's pretend this isn't the case (for practice)

# Floyd's tortoise and hare algorithm
# Time: 
# Space: 


'''
Not the real solution, but if we had extra space then we could use a hashmap/dictionary
Can also use a set
Time: O(n)
Space: O(n)
'''
def findDuplicateNumber_hashMap(intArray):
    dict = {}
    for i in intArray:
        if i in dict:
            return i
        else:
            dict[i] = 1

    return "No duplicate number"

print(f"findDuplicateNumber_hashMap1: {findDuplicateNumber_hashMap(test1)}")
print(f"findDuplicateNumber_hashMap2: {findDuplicateNumber_hashMap(test2)}")
print(f"findDuplicateNumber_hashMap3: {findDuplicateNumber_hashMap(test3)}")
print("-----------------------------------")

'''
If we could modify the input array then we could sort it in the first iteration, 
then look for adjacent repeating numbers in the second iteration 
Time: O(n log n) MergeSort
Space: O(n)
'''
def findDuplicateNumber_mergeSort(intArray):
    intArray = mergeSort(intArray)

    for i in range(1, len(intArray)):
        if intArray[i] == intArray[i - 1]:
            return intArray[i]
        
    return "No duplicate number"

# Divide and conquer: break down the array into the smallest pieces possible; integer primitives
def mergeSort(array):
    if len(array) == 1:
        return array
    
    mid = len(array) // 2
    left = array[0:mid]
    right = array[mid:len(array)]

    left = mergeSort(left)
    right = mergeSort(right)
    return merge(left, right)

# Put the pieces/integers back together in ascending order
def merge(firstArray, secondArray):
    newArray = []
    while firstArray and secondArray:
        if firstArray[0] < secondArray[0]:
            newArray.append(firstArray.pop(0))
        else:
            newArray.append(secondArray.pop(0))

    # Add any leftovers to the end of the new array
    if firstArray:
        newArray.extend(firstArray)
    if secondArray:
        newArray.extend(secondArray)

    return newArray
    
print(f"mergeSort1: {mergeSort(test1)}")
print(f"mergeSort2: {mergeSort(test2)}")
print(f"mergeSort2: {mergeSort(test3)}")

print(f"findDuplicateNumber_mergeSort1: {findDuplicateNumber_mergeSort(test1)}")
print(f"findDuplicateNumber_mergeSort2: {findDuplicateNumber_mergeSort(test2)}")
print(f"findDuplicateNumber_mergeSort3: {findDuplicateNumber_mergeSort(test3)}")
print("-----------------------------------")
