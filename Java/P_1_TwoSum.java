import java.util.*;

/*
 * Practicing Leetcode in Java, same solution as in Python.
 * 
 * We could implement a brute force solution where we iterate through the integers in the nums array, and for each intger, we make a
 * second iteration to find the difference. 
 * But this is inefficient: 2 for loops => O(n^2) time complexity
 * O(1) space complexity because no additional data structure used
 * 
 * The hashmap solution is a lot better since it only requires a single pass through the array
 * O(n) time complexity, where n is the # of elements in the array
 * O(n) space complexity because we could store all the elements in the array into the hasmap in the worst case scenario
 */

public class P_1_TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                return new int[] { map.get(diff), i };
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[] {};
    }

    public static void main(String[] args) {
        // Test cases: {nums, target, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 2, 7, 11, 15 }, 9, new int[] { 0, 1 } },
                { new int[] { 3, 2, 4 }, 6, new int[] { 1, 2 } },
                { new int[] { 3, 3 }, 6, new int[] { 0, 1 } },
                { new int[] { -1, -2, -3, -4, -5 }, -8, new int[] { 2, 4 } },
                { new int[] { -3, 4, 3, 90 }, 0, new int[] { 0, 2 } },
                { new int[] { 1000000000, 5, -999999995 }, 5, new int[] { 0, 2 } },
                { new int[] { 0, 4, 3, 0 }, 0, new int[] { 0, 3 } },
                { new int[] { 1, 2, 3, 4, 5 }, 9, new int[] { 3, 4 } },
                { new int[] { 5, 1, 2, 3, 4 }, 9, new int[] { 0, 4 } },
        };

        System.out.println("Running tests for P_1_TwoSum.twoSum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int target = (int) tests[i][1];
            int[] expected = (int[]) tests[i][2];
            int[] actual = twoSum(nums, target);
            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%s, actual=%s => %s\n",
                    i + 1, Arrays.toString(nums), target, Arrays.toString(expected), Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}