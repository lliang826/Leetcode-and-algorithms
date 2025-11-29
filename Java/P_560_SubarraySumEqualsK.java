import java.util.HashMap;
import java.util.Map;

public class P_560_SubarraySumEqualsK {
    /*
     * Prefix sum with hashmap approach. We can't use sliding window for this
     * problem because the input array could contain negative numbers. Instead,
     * we calculate the prefix sum first, then look for the complement prefix
     * sum in the hashmap (kind of like two sum but with prefix sums).
     * 
     * A subarray's sum can be found with with the following formula:
     * prefix[j] - prefix[i - 1]
     * Prefix at last element of subarray - prefix before first element
     * 
     * array = [1, 2, 3, 4]
     * ---- Subarray from indices 1 to 2
     * prefix = [1, 3, 6, 10]
     * Sum of subarray from indices 1 to 2 is 6 - 1 = 5
     * 
     * Since the sum is k, we can rewrite the formula as:
     * prefix[j] - prefix[i - 1] = k
     * 
     * Since we know prefix[j] (the prefix sum at the current index) and we also
     * know k, we just have to look for the complement prefix[i - 1] in the
     * hashmap. Formula rewritten as (moved variables around):
     * prefix[i - 1] = prefix[j] - k
     * 
     * e.g.
     * nums = [-1, 1, -1, 1]
     * k = 0
     * 
     * prefixSum = [-1, 0, -1, 0]
     * 
     * # of valid subarrays => 4
     * [-1, 1] indices 0 to 1
     * [1, -1] indices 1 to 2
     * [-1, 1] indices 2 to 3
     * [-1, 1, -1, 1] indices 0 to 3 (entire array)
     * 
     * Before we start iterating through the input array, we have to put { 0, 1 }
     * into the hashmap because an empty prefix has a sum of 0. Without this,
     * sum - k = 0 wouldn't be a valid subarray and our answer would be incorrect.
     * 
     * Instead of creating a separate loop to pre-process the prefix sums, we can
     * create the prefixes at the same time that we iterate through the input array.
     * 
     * In each iteration, we do 3 things:
     * - Calculate the prefix sum
     * - Calculate and find the prefix complement (sum - k) in the hashmap. If it
     * exists, add the complement's count/frequency to the total (this is the
     * answer that we return at the end)
     * - Add the current prefix sum to the hashmap (or update its count)
     * 
     * The complement's count is the number of valid subarray starting indices.
     * 
     * As mentioned above, this problem is like a combination of the Two Sum
     * problem, but with prefix sum complements instead.
     * 
     * Time: O(n), iterating through the entire array
     * Space: O(n), creating a hash map with up to n distinct prefix sums
     */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int total = 0;
        int sum = 0;
        for (int num : nums) {
            sum += num;
            if (map.containsKey(sum - k)) {
                total += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return total;
    }

    /*
     * Cleaner solution: we can calculate the total on a single line; if sum - k
     * doesn't exist in the hash map, we add 0 to the total (nothing happens).
     * 
     * Same time and space complexities.
     */
    public int v2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int total = 0;
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            total += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return total;
    }

    /*
     * Not the cleanest solution, but this might be the easiest way to understand
     * the problem. First we calculate the prefix sums, then we use them to find
     * the number of subarrays.
     * 
     * Same time and space complexities, but space is O(n + n) => O(n) since we
     * create both the prefix sum array and a frequency map.
     */
    public int v3(int[] nums, int k) {
        int[] prefix = new int[nums.length];
        prefix[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int total = 0;

        for (int p : prefix) {
            total += map.getOrDefault(p - k, 0);
            map.put(p, map.getOrDefault(p, 0) + 1);
        }

        return total;
    }

    public static void main(String[] args) {
        P_560_SubarraySumEqualsK solver = new P_560_SubarraySumEqualsK();

        // Test cases: {input array, k, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 1, 1 }, 2, 2 },
                { new int[] { 1, 2, 3 }, 3, 2 },
                { new int[] { 1 }, 0, 0 },
                { new int[] { -1, -1, 1 }, 0, 1 },
                { new int[] { 1, -1, 0 }, 0, 3 },
                { new int[] { 1, 2, 1, 2, 1 }, 3, 4 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 1, 2, 3 }, 6, 1 },
                { new int[] { 3, 4, 7, 2, -3, 1, 4, 2 }, 7, 4 },
                { new int[] { 0, 0, 0, 0, 0 }, 0, 15 },
                { new int[] { -1, 1, -1, 1 }, 0, 4 },
                { new int[] { 1, -1, 1, -1, 1 }, 0, 6 },
                { new int[] { 5, -5, 5, -5 }, 0, 4 },
                { new int[] { 10 }, 10, 1 },
                { new int[] { 1, 2, 3, 4, 5 }, 15, 1 }
        };

        System.out.println("Running tests for P_560_SubarraySumEqualsK.subarraySum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.subarraySum(input.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("subarraySum: %d/%d tests passed\n", pass, tests.length);
    }
}
