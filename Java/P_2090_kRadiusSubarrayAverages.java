public class P_2090_kRadiusSubarrayAverages {
    public int[] getAverages(int[] nums, int k) {
        long[] prefixSum = new long[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        
        long[] output = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i - k < 0 || i + k > nums.length - 1) {
                output[i] = -1;
            } else {
                long sum = prefixSum[i + k] - prefixSum[i - k] + nums[i - k];
                output[i] = sum / (2 * k + 1);
            }
        }
        
        int[] intOutput = new int[nums.length];
        for (int i = 0; i < output.length; i++) {
            intOutput[i] = (int) output[i];
        }
        
        return intOutput;
    }
    // [7, 11, 14, 23, 24, 32, 37, 39, 45]

    public static void main(String[] args) {
        P_2090_kRadiusSubarrayAverages solver = new P_2090_kRadiusSubarrayAverages();

        // Test cases: {nums, k, expected}
        Object[][] tests = new Object[][] {
                { new int[]{7, 4, 3, 9, 1, 8, 5, 2, 6}, 3, new int[]{-1, -1, -1, 5, 4, 4, -1, -1, -1} },
                { new int[]{100000}, 0, new int[]{100000} },
                { new int[]{8}, 100000, new int[]{-1} },
                { new int[]{1, 2, 3, 4, 5}, 1, new int[]{-1, 2, 3, 4, -1} },
                { new int[]{1, 2, 3, 4, 5}, 2, new int[]{-1, -1, 3, -1, -1} },
                { new int[]{10, 20, 30}, 0, new int[]{10, 20, 30} },
                { new int[]{5, 5, 5, 5, 5}, 1, new int[]{-1, 5, 5, 5, -1} },
                { new int[]{1, 1, 1, 1, 1, 1, 1}, 3, new int[]{-1, -1, -1, 1, -1, -1, -1} },
                { new int[]{40527, 53696, 93730, 81345, 60298}, 1, new int[]{-1, 62651, 76257, 78457, -1} },
                { new int[]{18, 16, 9, 5, 4, 15, 17, 8, 2, 20}, 2, new int[]{-1, -1, 10, 9, 10, 10, 11, -1, -1, -1} }
        };

        System.out.println("Running tests for P_2090_kRadiusSubarrayAverages.getAverages\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int[] expected = (int[]) tests[i][2];
            int[] actual = solver.getAverages(nums.clone(), k);
            
            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(nums), k,
                    java.util.Arrays.toString(expected), 
                    java.util.Arrays.toString(actual), 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("getAverages: %d/%d tests passed\n", pass, tests.length);
    }
}
