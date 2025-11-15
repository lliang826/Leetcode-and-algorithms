public class P_26_RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        int left = 1;

        for (int right = 1; right < nums.length; right++) {
            if (nums[right] != nums[right - 1]) {
                nums[left] = nums[right];
                left++;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        P_26_RemoveDuplicatesFromSortedArray solver = new P_26_RemoveDuplicatesFromSortedArray();

        // Test cases: {input, expected length, expected array (first k elements)}
        Object[][] tests = new Object[][] {
                { new int[]{1, 1, 2}, 2, new int[]{1, 2} },
                { new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}, 5, new int[]{0, 1, 2, 3, 4} },
                { new int[]{1}, 1, new int[]{1} },
                { new int[]{1, 1, 1, 1, 1}, 1, new int[]{1} },
                { new int[]{1, 2, 3, 4, 5}, 5, new int[]{1, 2, 3, 4, 5} },
                { new int[]{1, 1, 2, 2, 3, 3}, 3, new int[]{1, 2, 3} },
                { new int[]{-3, -1, 0, 0, 0, 3, 3}, 4, new int[]{-3, -1, 0, 3} },
                { new int[]{1, 2}, 2, new int[]{1, 2} },
                { new int[]{1, 1}, 1, new int[]{1} },
                { new int[]{-1, 0, 0, 0, 3, 3, 3}, 3, new int[]{-1, 0, 3} }
        };

        System.out.println("Running tests for P_26_RemoveDuplicatesFromSortedArray.removeDuplicates\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expectedLength = (int) tests[i][1];
            int[] expectedArray = (int[]) tests[i][2];
            int[] inputCopy = input.clone();
            int actualLength = solver.removeDuplicates(inputCopy);
            
            boolean lengthMatch = (expectedLength == actualLength);
            boolean arrayMatch = true;
            if (lengthMatch) {
                for (int j = 0; j < expectedLength; j++) {
                    if (inputCopy[j] != expectedArray[j]) {
                        arrayMatch = false;
                        break;
                    }
                }
            }
            
            boolean ok = lengthMatch && arrayMatch;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected length=%d, actual length=%d, array match=%s => %s\n",
                    i + 1, java.util.Arrays.toString(input), expectedLength, actualLength, 
                    arrayMatch, (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("removeDuplicates: %d/%d tests passed\n", pass, tests.length);
    }
}
