public class P_2270_NumberOfWaysToSplitArray {
    public int waysToSplitArray(int[] nums) {
        long[] prefix = new long[nums.length];
        prefix[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }

        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (prefix[i] >= prefix[nums.length - 1] - prefix[i]) {
                count++;
            }
        }
        return count;
    }

    public int v2(int[] nums) {
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        int count = 0;
        long leftSum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            leftSum += nums[i];
            if (leftSum >= totalSum - leftSum) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        P_2270_NumberOfWaysToSplitArray solver = new P_2270_NumberOfWaysToSplitArray();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[]{10, 4, -8, 7}, 2 },
                { new int[]{2, 3, 1, 0}, 2 },
                { new int[]{1, 2, 3, 4, 5}, 1 },
                { new int[]{5, 4, 3, 2, 1}, 3 },
                { new int[]{1, 1}, 1 },
                { new int[]{-1, -1}, 1 },
                { new int[]{0, 0}, 1 },
                { new int[]{10, -10, 10, -10, 10}, 2 },
                { new int[]{100, 1, 1, 1, 1}, 4 },
                { new int[]{1, 1, 1, 1, 100}, 0 }
        };

        System.out.println("Running tests for P_2270_NumberOfWaysToSplitArray.waysToSplitArray\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.waysToSplitArray(input.clone());
            
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_2270_NumberOfWaysToSplitArray.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v2(input.clone());
            
            boolean ok = (expected == actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("waysToSplitArray: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}