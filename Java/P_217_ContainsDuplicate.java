import java.util.*;

/*
 * Brute force solution: iterating through the array, and at each integer in the array, make a second iteration
 * to check if subsequent integers are duplicates.
 * Time complexity: O(n^2), very inefficient because of nested for loops
 * Space complexity: O(1), no additional data structures
 * 
 * Set solution (implemented below): this is the most efficient solution because it only requires a single pass
 * through the array. First, create an empty set. At each integer, check if the integer already exists in the set.
 * If it does, it's a duplicate and we can return true. If it doesn't exist, add the integer to the set so it can 
 * be checked by the reset of the integers in the array. If we go through the entire array and there are no 
 * duplicates, return false.
 * Instead of using a set, we could also use a map, where the [key: value] pair is [an integer: the # of times the
 * integer appears in the array]. This results in the exact same time and space complexity as the set, but we would
 * also have to add a value in each iteration, which is unnecessary.
 * Time complexity: O(n), where n is the number of integers in the array
 * Space complexity: O(n), we could potentially store all the integers into the set in the worst case scenario
 * 
 * Sorting solution: if we wanted the most efficient solution with no additional space, this would be the choice.
 * This requires a first pass through the array to sort all the integers in either ascending or descending order.
 * Then, in the second pass (not nested), we would check for identical integers next to each other.
 * Time complexity: O(n log n), since we are constrained by the sorting algorithm efficiency
 * Space complexity: O(1), no additional data structure if we sort the array in-place without creating a copy
 *  - Sorting algorithm CAN'T be Merge Sort since that creates temporary arrays
 *  - To achieve in-place sorting with n log n time, it has to be Heap Sort
 */

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
        // Test cases: {nums, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 2, 3, 1}, true },
                { new int[]{1, 2, 3, 4}, false },
                { new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}, true },
                { new int[]{1}, false },
                { new int[]{5, 5}, true },
                { new int[]{1, 2}, false },
                { new int[]{-1, -2, -3, -1}, true },
                { new int[]{-1, -2, -3, -4}, false },
                { new int[]{0, -1, 2, -3, 0}, true },
                { new int[]{1000000000, -1000000000, 999999999, 1000000000}, true },
                { new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}, true },
                { new int[]{7, 7, 7, 7, 7}, true },
        };

        System.out.println("Running tests for P_217_ContainsDuplicate.containsDuplicate\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            boolean actual = containsDuplicate(nums);
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s => expected=%b, actual=%b => %s\n",
                    i + 1, Arrays.toString(nums), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}