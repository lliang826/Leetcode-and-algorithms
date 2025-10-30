import java.util.*;

public class P_217_ContainsDuplicate {
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num: nums) {
            if (set.contains(num)) {
                return true;
            } else {
                set.add(num);
            }
        }
        return false;
    }

    public static void main(String[] args) {        
        // Test Case 1: Example 1 from problem
        System.out.println("Test 1: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1, 2, 3, 1}));
        // Expected: true (element 1 appears twice)
        
        // Test Case 2: Example 2 from problem
        System.out.println("Test 2: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1, 2, 3, 4}));
        // Expected: false (all elements distinct)
        
        // Test Case 3: Example 3 from problem
        System.out.println("Test 3: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}));
        // Expected: true (multiple duplicates)
        
        // Test Case 4: Single element
        System.out.println("Test 4: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1}));
        // Expected: false (only one element, no duplicates possible)
        
        // Test Case 5: Two identical elements
        System.out.println("Test 5: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{5, 5}));
        // Expected: true (immediate duplicate)
        
        // Test Case 6: Two different elements
        System.out.println("Test 6: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1, 2}));
        // Expected: false (no duplicates)
        
        // Test Case 7: Negative numbers with duplicates
        System.out.println("Test 7: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{-1, -2, -3, -1}));
        // Expected: true (-1 appears twice)
        
        // Test Case 8: All negative numbers, no duplicates
        System.out.println("Test 8: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{-1, -2, -3, -4}));
        // Expected: false (all distinct)
        
        // Test Case 9: Mix of positive, negative, and zero
        System.out.println("Test 9: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{0, -1, 2, -3, 0}));
        // Expected: true (0 appears twice)
        
        // Test Case 10: Large numbers within constraints
        System.out.println("Test 10: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1000000000, -1000000000, 999999999, 1000000000}));
        // Expected: true (1000000000 appears twice)
        
        // Test Case 11: Duplicates at the end
        System.out.println("Test 11: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}));
        // Expected: true (1 appears at beginning and end)
        
        // Test Case 12: All same elements
        System.out.println("Test 12: " + P_217_ContainsDuplicate.containsDuplicate(new int[]{7, 7, 7, 7, 7}));
        // Expected: true (all elements are duplicates)
    }
}