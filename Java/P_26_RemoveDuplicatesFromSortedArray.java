/*
 * Two pointers. Both left and right pointers start at index 1 because the integer at index 0 is already unique. As the right pointer
 * traverses the length of the array, it checks if the current index's value is the same as the previous index's value. If it isn't,
 * copy that value to the left pointer and increment the left pointer by one. Finally, we can return the left pointer because it 
 * represents the number of unique values in the array (it increments every time a unique integer is found by the right pointer).
 * 
 * Essentially, the right pointer finds the unique integers and the left pointer builds the new array in-place.
 * 
 * Time complexity: O(n), the right pointer must traverse the length of the array and check all integers
 * Space complexity: O(1), no additional data structures, only pointers
 */

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
