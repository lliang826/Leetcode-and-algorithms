import java.util.HashMap;
import java.util.Map;

public class P_1248_CountNumberOfNiceSubarrays {
    /*
     * Prefix hashing approach.
     * 
     * input = [3, 3, 2, 3, 3], k = 3
     * prefix = [1, 2, 2, 3, 4]
     * 
     * output = 2 subarrays with 3 odd numbers
     * [3, 3, 2, 3] k = prefix[j] - prefix[i - 1] = 3 = 3 - 0
     * [3, 2, 3, 3] k = prefix[j] - prefix[i - 1] = 3 = 4 - 1
     * 
     * For this problem, the condition is about counting the number of odd numbers.
     * So, for each number in the input array, we can do 3 things:
     * - Determine the current count of odd numbers in the array
     * - Since we know that prefix[j] - k = prefix[i - 1], we look for prefix[i - 1]
     * in the hashmap. If it exists, we track its frequency - this is the answer we
     * will return at the end
     * - Store the odd number count in the hash map, or update its frequency
     * 
     * Time: O(n * 1) => O(n), we iterate through all integers in the input array,
     * but hash map operations are O(1) constant time
     * 
     * Space: O(n), we may store up to n prefixes in the hash map (n is the number
     * of integers in the input array)
     */
    public int numberOfSubarrays(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int oddCount = 0;
        int niceCount = 0;

        for (int num : nums) {
            if (num % 2 == 1) {
                oddCount++;
            }

            int prefixComplement = oddCount - k;
            if (map.containsKey(prefixComplement)) {
                niceCount += map.get(prefixComplement);
            }

            map.put(oddCount, map.getOrDefault(oddCount, 0) + 1);
        }

        return niceCount;
    }

    /*
     * Improved version of the method above. No change in time/space complexities or
     * in logic; it's just simplified so the code is cleaner.
     */
    public int v2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int oddCount = 0;
        int niceCount = 0;

        for (int num : nums) {
            oddCount += num % 2;
            niceCount += map.getOrDefault(oddCount - k, 0);
            map.put(oddCount, map.getOrDefault(oddCount, 0) + 1);
        }

        return niceCount;
    }

    class Solution {
        public int twoPointerApproach(int[] nums, int k) {
            return exactly(k, nums);
        }

        private int exactly(int k, int[] nums) {
            return atMost(k, nums) - atMost(k - 1, nums);
        }

        private int atMost(int k, int[] nums) {
            int left = 0;
            int oddCount = 0;
            int nice = 0;

            for (int right = 0; right < nums.length; right++) {
                if (nums[right] % 2 == 1) {
                    oddCount++;
                }

                while (oddCount > k) {
                    if (nums[left] % 2 == 1) {
                        oddCount--;
                    }
                    left++;
                }

                nice += right - left + 1;
            }

            return nice;
        }
    }

    public static void main(String[] args) {
        P_1248_CountNumberOfNiceSubarrays solver = new P_1248_CountNumberOfNiceSubarrays();

        Object[][] tests = new Object[][] {
                { new int[] { 1, 1, 2, 1, 1 }, 3, 2 },
                { new int[] { 2, 4, 6 }, 1, 0 },
                { new int[] { 2, 2, 2, 1, 2, 2, 1, 2, 2, 2 }, 2, 16 },
                { new int[] { 1, 1, 1, 1, 1 }, 1, 5 },
                { new int[] { 1, 1, 1, 1, 1 }, 5, 1 },
                { new int[] { 2, 2, 2, 2 }, 1, 0 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 2 }, 1, 0 },
                { new int[] { 1, 2, 3, 4, 5 }, 2, 4 },
                { new int[] { 1, 1, 2, 2, 3 }, 3, 1 },
                { new int[] { 2, 2, 1, 2, 1, 2, 1 }, 3, 3 },
                { new int[] { 1, 3, 5 }, 1, 3 },
                { new int[] { 1, 3, 5 }, 2, 2 },
                { new int[] { 1, 3, 5 }, 3, 1 },
                { new int[] { 2, 4, 1, 6, 8, 3, 10 }, 2, 6 },
                // Single odd with an even on each side: 2 start choices x 2 end choices
                { new int[] { 2, 1, 2 }, 1, 4 },
                // Odd at the very end: only the start position can vary
                { new int[] { 2, 2, 1 }, 1, 3 },
                // Odd at the very start: only the end position can vary
                { new int[] { 1, 2, 2 }, 1, 3 },
                // Alternating odds and evens
                { new int[] { 1, 2, 1, 2, 1 }, 2, 4 },
                // Wide even gaps around each odd: 3x3 windows around each one
                { new int[] { 2, 2, 1, 2, 2, 1, 2, 2 }, 1, 18 },
                // Same array, but both odds must be included: 3x3
                { new int[] { 2, 2, 1, 2, 2, 1, 2, 2 }, 2, 9 },
                // k is larger than the total number of odds in the array
                { new int[] { 1, 2, 3 }, 3, 0 }
        };

        System.out.println("Running tests for P_1248_CountNumberOfNiceSubarrays.numberOfSubarrays\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.numberOfSubarrays(input.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1248_CountNumberOfNiceSubarrays.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.v2(input.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1248_CountNumberOfNiceSubarrays.Solution.numberOfSubarrays\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            P_1248_CountNumberOfNiceSubarrays.Solution slidingWindow = solver.new Solution();
            int actual = slidingWindow.twoPointerApproach(input.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass3++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("numberOfSubarrays: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("Solution.twoPointerApproach: %d/%d tests passed\n", pass3, tests.length);
    }
}
