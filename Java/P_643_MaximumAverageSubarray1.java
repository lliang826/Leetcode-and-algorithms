/*
 * Sliding window algorithm. Instead of a dynamic window, the size of the window for this problem is fixed at length k.
 * To keep the window length fixed, every time we increment the right pointer, we also increment the left pointer. The
 * left pointer can be found at i - k, and the left pointer's value should be deducted/removed from the requirement's
 * calculation.
 * There is an initial for loop to calculate the current window's sum. Then, the second for loop starts at the next
 * index, and it continues to the end of the array. In each iteration, we shift both the left and right pointer by one.
 * When we move the pointers, there is an overlap in the values between the pointers, which is why we don't need to 
 * recalculate the sum in each iteration; we only need to add the rightmost value and deduct the leftmost value.
 * 
 * Time complexity: O(n), the right pointer must traverse the length of the array
 * Space complexity: O(1), no additional data structure
 */

public class P_643_MaximumAverageSubarray1 {
    public double findMaxAverage(int[] nums, int k) {
        double sum = 0;
        for (int right = 0; right < k; right++) {
            sum += nums[right];
        }
        
        double avg = sum / k;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i];
            sum -= nums[i - k];
            
            double tempAvg = sum / k;
            avg = Math.max(avg, tempAvg);
        }
        
        return avg;
    }

    /*
     * Better solution which calculates the average at the end for better performance. We can do this because the ratio k
     * is the same for all subarrays. Since we calculate the average at the end, we can store the sum as integers, which
     * is also a speed/space improvement.
     */
    public double v2(int[] nums, int k) {
        int sum = 0;
        for (int right = 0; right < k; right++) {
            sum += nums[right];
        }
        
        int maxSum = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, sum);
        }
        
        return (double) maxSum / k;
    }
    
    public static void main(String[] args) {
        P_643_MaximumAverageSubarray1 solver = new P_643_MaximumAverageSubarray1();

        // Test cases: {input, k, expected}
        Object[][] tests = new Object[][] {
                { new int[]{1, 12, -5, -6, 50, 3}, 4, 12.75000 },
                { new int[]{5}, 1, 5.00000 },
                { new int[]{0, 1, 1, 3, 3}, 4, 2.00000 },
                { new int[]{-1}, 1, -1.00000 },
                { new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5, 8.00000 },
                { new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, 3, 9.00000 },
                { new int[]{4, 0, 4, 3, 3}, 5, 2.80000 },
                { new int[]{3, 3, 4, 3, 0}, 3, 3.33333 },
                { new int[]{-6662, 5432, -8558, -8935, 8731, -3083}, 4, -832.50000 },
                { new int[]{8860, -853, 6534, 4477, -4589, 8646, -6155, -5577, -1656, -5779, -2619, -8604, -1358, -8009, 4983, 7063, 3104, -1560, 4080, 2763, 5616, -2375, 2848, 1394, -7173, -5225, -8244, -5960, 5414, -5694, -364, 4867, 4839, 5169, 9904, -1155, -1273, -9663, 8497, 3987, -277, -3535, -6856, 7221, -3193, 3088, 4918, 8939, -2783, -1202, -1505, 3094, -1544, 4952, 3767, 3473, -2715, 2562}, 3, 6637.33333 }
        };

        System.out.println("Running tests for P_643_MaximumAverageSubarray1.findMaxAverage\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            double expected = (double) tests[i][2];
            double actual = solver.findMaxAverage(input.clone(), k);
            
            boolean ok = Math.abs(expected - actual) < 0.00001;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%.5f, actual=%.5f => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_643_MaximumAverageSubarray1.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            double expected = (double) tests[i][2];
            double actual = solver.v2(input.clone(), k);
            
            boolean ok = Math.abs(expected - actual) < 0.00001;
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s, k=%d => expected=%.5f, actual=%.5f => %s\n",
                    i + 1, java.util.Arrays.toString(input), k, expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("findMaxAverage: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
