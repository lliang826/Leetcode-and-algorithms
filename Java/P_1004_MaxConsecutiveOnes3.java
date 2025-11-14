/*
 * Sliding window algorithm. This is a dynamic window where the subarray must fulfill the condition of k zeroes.
 * Every time the right pointer increments, we need to track the number of zeroes that have passed. If the 
 * number of zeroes is greater than k, we need to shrink the window by incrementing the left pointer until the
 * condition of k zeroes is met again.
 * 
 * Time complexity: O(n), the right pointer must traverse the length of the array
 * Space complexity: O(1), constant space, no additional data structure
 */

public class P_1004_MaxConsecutiveOnes3 {
    public int longestOnes(int[] nums, int k) {
        int ones = 0;
        int zeroes = 0;
        int left = 0;
        int max = 0;
        
        for (int right = 0 ; right < nums.length; right++) {
            ones++;
            if (nums[right] == 0) {
                zeroes++;
            }
            
            while (zeroes > k) {
                if (nums[left] == 1) {
                    ones--;
                } else if (nums[left] == 0) {
                    zeroes--;
                    ones--;
                }
                left++;
            }
            max = Math.max(max, ones);
        }
        
        return max;
    }

    /*
     * Better solution which doesn't need to track the number of ones. Since we are only counting 1s, we can 
     * simply use the length of the window (right - left + 1) to find the number of ones.
     */
    public int v2(int[] nums, int k) {
        int zeroes = 0;
        int left = 0;
        int max = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroes++;
            }

            while (zeroes > k) {
                if (nums[left] == 0) {
                    zeroes--;
                }
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        P_1004_MaxConsecutiveOnes3 solver = new P_1004_MaxConsecutiveOnes3();

        // Test cases: {input, k, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2, 6 },
                { new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3, 10 },
                { new int[]{1, 1, 1, 1, 1}, 0, 5 },
                { new int[]{0, 0, 0, 0, 0}, 2, 2 },
                { new int[]{1}, 1, 1 },
                { new int[]{0}, 0, 0 },
                { new int[]{0}, 1, 1 },
                { new int[]{1, 0, 1, 0, 1}, 1, 3 },
                { new int[]{0, 0, 1, 1, 1, 0, 0}, 0, 3 },
                { new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 0, 4 },
                { new int[]{0, 0, 0, 1}, 4, 4 },
                { new int[]{1, 0, 0, 0, 1, 1, 0, 0, 1, 1}, 2, 6 },
                { new int[]{0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1}, 2, 9 },
                { new int[]{1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1}, 3, 10 }
        };

        System.out.println("Running tests for P_1004_MaxConsecutiveOnes3.longestOnes\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.longestOnes(input.clone(), k);
            
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1004_MaxConsecutiveOnes3.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.v2(input.clone(), k);
            
            boolean ok = (expected == actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("longestOnes: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
