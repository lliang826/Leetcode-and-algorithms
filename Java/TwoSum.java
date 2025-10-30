import java.util.*;

/*
 * Practicing Leetcode in Java, same solution as in Python.
 * 
 * We could implement a brute force solution where we iterate through the integers in the nums array, and for each intger, we make a
 * second iteration to find the complement. 
 * But this is inefficient: 2 for loops => O(n^2) time complexity
 * O(1) space complexity because no additional data structure used
 * 
 * The hashmap solution is a lot better since it only requires a single pass through the array
 * O(n) time complexity, where n is the # of elements in the array
 * O(n) space complexity because we could store all the elements in the array into the hasmap in the worst case scenario
 */

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { i, map.get(complement) };
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[] {};
    }

    public static void main(String[] args) {
        // Test Case 1: Example 1 from problem
        System.out.println("Test 1: " + Arrays.toString(TwoSum.twoSum(new int[] { 2, 7, 11, 15 }, 9)));
        // Expected: [0, 1]
        
        // Test Case 2: Example 2 from problem  
        System.out.println("Test 2: " + Arrays.toString(TwoSum.twoSum(new int[] { 3, 2, 4 }, 6)));
        // Expected: [1, 2]
        
        // Test Case 3: Example 3 from problem
        System.out.println("Test 3: " + Arrays.toString(TwoSum.twoSum(new int[] { 3, 3 }, 6)));
        // Expected: [0, 1]
        
        // Test Case 4: Negative numbers
        System.out.println("Test 4: " + Arrays.toString(TwoSum.twoSum(new int[] { -1, -2, -3, -4, -5 }, -8)));
        // Expected: [2, 4] (-3 + -5 = -8)
        
        // Test Case 5: Mix of positive and negative
        System.out.println("Test 5: " + Arrays.toString(TwoSum.twoSum(new int[] { -3, 4, 3, 90 }, 0)));
        // Expected: [0, 2] (-3 + 3 = 0)
        
        // Test Case 6: Large numbers  
        System.out.println("Test 6: " + Arrays.toString(TwoSum.twoSum(new int[] { 1000000000, 5, -999999995 }, 5)));
        // Expected: [0, 2] (1000000000 + (-999999995) = 5)
        
        // Test Case 7: Zero target
        System.out.println("Test 7: " + Arrays.toString(TwoSum.twoSum(new int[] { 0, 4, 3, 0 }, 0)));
        // Expected: [0, 3] (0 + 0 = 0)
        
        // Test Case 8: Adjacent elements
        System.out.println("Test 8: " + Arrays.toString(TwoSum.twoSum(new int[] { 1, 2, 3, 4, 5 }, 9)));
        // Expected: [3, 4] (4 + 5 = 9)
        
        // Test Case 9: First and last elements
        System.out.println("Test 9: " + Arrays.toString(TwoSum.twoSum(new int[] { 5, 1, 2, 3, 4 }, 9)));
        // Expected: [0, 4] (5 + 4 = 9)
    }
}