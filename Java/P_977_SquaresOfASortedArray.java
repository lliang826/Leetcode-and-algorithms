/*
 * Requires the two pointer technique. Since the input array holds both negative and positive integers but it is sorted,
 * we know that the largest squares will be at either the left and right ends of the array, or both.
 * [-2, -1, 0, 1, 2] => [4, 1, 0, 1, 4]
 * [-1, 0, 1, 2, 3] => [1, 0, 1, 4, 9]
 * [-3, -2, -1, 0, 1] => [9, 4, 1, 0, 1]
 * 
 * We can therefore initialize a left pointer at the start of the input array and a right pointer at the end. In each
 * iteration, we compare the squares at each pointer. The larger number is stored in the output array and that pointer
 * moves towards the center of the array.
 * Storing the number in the output array requires an additional variable to track the index.
 */
public class P_977_SquaresOfASortedArray {
    public int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int[] output = new int[nums.length];
               
        for (int i = nums.length - 1; i >= 0; i--) {
            int leftInt = nums[left] * nums[left];
            int rightInt = nums[right] * nums[right];
            
            if (leftInt > rightInt) {
                output[i] = leftInt;
                left++;
            } else {
                output[i] = rightInt;
                right--;
            }
        }
        
        return output;
    }

    public int[] v2(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int[] output = new int[nums.length];
        int index = nums.length - 1;
               
        while (left <= right) {
            int leftInt = (int) Math.pow(nums[left], 2);
            int rightInt = (int) Math.pow(nums[right], 2);

            if (leftInt >= rightInt) {
                output[index] = leftInt;
                left++;
            } else {
                output[index] = rightInt;
                right--;
            }
            index--;
        }
        
        return output;
    }

    public static void main(String[] args) {
        P_977_SquaresOfASortedArray solver = new P_977_SquaresOfASortedArray();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[]{-4, -1, 0, 3, 10}, new int[]{0, 1, 9, 16, 100} },
                { new int[]{-7, -3, 2, 3, 11}, new int[]{4, 9, 9, 49, 121} },
                { new int[]{-5, -3, -2, -1}, new int[]{1, 4, 9, 25} },
                { new int[]{1, 2, 3, 4, 5}, new int[]{1, 4, 9, 16, 25} },
                { new int[]{0}, new int[]{0} },
                { new int[]{-1}, new int[]{1} },
                { new int[]{2}, new int[]{4} },
                { new int[]{-3, -2, -1, 0, 1, 2, 3}, new int[]{0, 1, 1, 4, 4, 9, 9} },
                { new int[]{-10, -5, 0, 5, 10}, new int[]{0, 25, 25, 100, 100} },
                { new int[]{-2, 0, 1}, new int[]{0, 1, 4} },
                { new int[]{-1, 0, 1, 2}, new int[]{0, 1, 1, 4} },
                { new int[]{-6, -4, -2, 1, 3}, new int[]{1, 4, 9, 16, 36} }
        };

        System.out.println("Running tests for P_977_SquaresOfASortedArray.sortedSquares\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            int[] actual = solver.sortedSquares(input.clone()); // Clone to avoid modifying original
            
            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected), 
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_977_SquaresOfASortedArray.v2\n");
        int passV2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            int[] actual = solver.v2(input.clone()); // Clone to avoid modifying original
            
            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                passV2++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected), 
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", passV2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("sortedSquares: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", passV2, tests.length);
    }
}
