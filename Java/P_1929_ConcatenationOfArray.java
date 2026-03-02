public class P_1929_ConcatenationOfArray {
    /*
    Very easy problem. To concatenate an array with a copy of itself, we simply create an output array
    with twice the size of the original, then iterate through the original array twice.
    
    Time: O(2n) => O(n)
    Space: O(2n) => O(n)
    */
    public int[] getConcatenation(int[] nums) {
        int[] res = new int[nums.length * 2];

        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            res[i + nums.length] = nums[i];
        }

        return res;
    }

    /*
    Improved version of the solution above. Instead of iterating twice through the original array, we can
    iterate a single time and store the integer into both spots in the output array.
    
    Same time and space complexities as above, but slightly faster due to the single loop.
    */
    public int[] getConcatenation2(int[] nums) {
        int[] res = new int[nums.length * 2];

        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i];
            res[i + nums.length] = nums[i];
        }

        return res;
    }

    public static void main(String[] args) {
        P_1929_ConcatenationOfArray solver = new P_1929_ConcatenationOfArray();

        // Test cases: {input nums, expected output}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2, 1 }, new int[] { 1, 2, 1, 1, 2, 1 } },
                { new int[] { 1, 3, 2, 1 }, new int[] { 1, 3, 2, 1, 1, 3, 2, 1 } },
                { new int[] { 1 }, new int[] { 1, 1 } },
                { new int[] { 5, 10 }, new int[] { 5, 10, 5, 10 } },
                { new int[] { 1000 }, new int[] { 1000, 1000 } },
                { new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 } },
                { new int[] { 7, 7, 7 }, new int[] { 7, 7, 7, 7, 7, 7 } },
                { new int[] { 1, 1000 }, new int[] { 1, 1000, 1, 1000 } },
                { new int[] { 3, 1, 4, 1, 5 }, new int[] { 3, 1, 4, 1, 5, 3, 1, 4, 1, 5 } },
                { new int[] { 42 }, new int[] { 42, 42 } },
        };

        System.out.println("Running tests for P_1929_ConcatenationOfArray.getConcatenation\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            int[] actual = solver.getConcatenation(input.clone());

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1929_ConcatenationOfArray.getConcatenation2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int[] expected = (int[]) tests[i][1];
            int[] actual = solver.getConcatenation2(input.clone());

            boolean ok = java.util.Arrays.equals(expected, actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                    java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("getConcatenation: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("getConcatenation2: %d/%d tests passed\n", pass2, tests.length);
    }
}
