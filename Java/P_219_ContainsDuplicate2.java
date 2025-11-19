import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P_219_ContainsDuplicate2 {
    /*
     * HashMap approach. Very similar to the original Contains Duplicate problem, except now we also have to check if the range
     * between the two indices is less than or equal to k. If the integer is already in the map (already seen), but the 
     * range is not within k, we update that integer's index in the map to the new index.
     * 
     * Time: O(n), iterating through all integers in the input array
     * Space: O(n), all integers are added to the map in the worst case scenario
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if ((i - map.get(nums[i])) <= k) {
                    return true;
                } else {
                    map.put(nums[i], i);
                }
            }  else {
                map.put(nums[i], i);
            }
        }
        return false;
    }

    /*
     * Improved version of the HashMap solution above. We can combine some of the if else branches to simplify the code. If
     * the integer doesn't exist in the map or if we need to update the integer's index in the map, we call map.put().
     * 
     * Same time and space complexities.
     */
    public boolean containsNearbyDuplicateImproved(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }  else {
                map.put(nums[i], i);
            }
        }
        return false;
    }

    /*
     * Sliding window approach with a fixed window length of k. Both left and right pointers start at the beginning of the
     * input array. The right pointer is simple: if it finds a duplicate integer, return true. Otherwise, store it in a set.
     * Since a duplicate is only valid if it's within the fixed window length k, if the right pointer exceeds k, we need to
     * increment the left pointer. But before incrementing, we remove the value at the left pointer from the set since it
     * is no longer within the valid window length.
     * 
     * Time: O(n), iterating through all elements within the input array
     * Space: O(k + 1) => O(n), the set can only contain a maximum of k + 1 integers, so a slight memory improvement
     */
    public boolean v2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            if (set.contains(nums[right])) {
                return true;
            } else {
                set.add(nums[right]);
            }
            
            if (right >= k) {
                set.remove(nums[left]);
                left++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        P_219_ContainsDuplicate2 solver = new P_219_ContainsDuplicate2();

        // Test cases: {input, k, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 2, 3, 1}, 3, true },
                { new int[]{1, 0, 1, 1}, 1, true },
                { new int[]{1, 2, 3, 1, 2, 3}, 2, false },
                { new int[]{1}, 0, false },
                { new int[]{1, 1}, 1, true },
                { new int[]{1, 2, 1}, 0, false },
                { new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}, 9, true },
                { new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}, 8, false },
                { new int[]{99, 99}, 2, true },
                { new int[]{1, 2, 3, 4, 1, 2, 3, 4}, 3, false },
                { new int[]{1, 2, 3, 4, 1, 2, 3, 4}, 4, true },
                { new int[]{-1, -1}, 1, true },
                { new int[]{0, 0, 0}, 2, true },
                { new int[]{5, 6, 7, 8, 5}, 3, false },
                { new int[]{5, 6, 7, 8, 5}, 4, true }
        };

        System.out.println("Running tests for P_219_ContainsDuplicate2.containsNearbyDuplicate\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.containsNearbyDuplicate(input.clone(), k);
            
            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%b, actual=%b => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_219_ContainsDuplicate2.containsNearbyDuplicateImproved\n");
        int passImproved = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.containsNearbyDuplicateImproved(input.clone(), k);
            
            boolean ok = expected == actual;
            if (ok)
                passImproved++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%b, actual=%b => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passImproved, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_219_ContainsDuplicate2.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.v2(input.clone(), k);
            
            boolean ok = expected == actual;
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%b, actual=%b => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("containsNearbyDuplicate: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("containsNearbyDuplicateImproved: %d/%d tests passed\n", passImproved, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
