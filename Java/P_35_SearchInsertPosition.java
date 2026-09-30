public class P_35_SearchInsertPosition {
    /*
    Binary search. Instead of returning -1 if the target isn't found, we
    can return the left pointer, which will be the index where target 
    should be inserted to maintain the sorted order of the array.

    The left pointer is the insertion point because everything before
    left is less than the target and everything after left is greater.

    Time: O(log n)
    Space: O(1), only pointers
    */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        P_35_SearchInsertPosition solver = new P_35_SearchInsertPosition();

        // Test cases: {nums, target, expected}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 3, 5, 6 }, 5, 2 },              // found in middle
                { new int[] { 1, 3, 5, 6 }, 2, 1 },              // insert in middle
                { new int[] { 1, 3, 5, 6 }, 7, 4 },              // insert past the end
                { new int[] { 1, 3, 5, 6 }, 0, 0 },              // insert before the start
                { new int[] { 1, 3, 5, 6 }, 1, 0 },              // found at first index
                { new int[] { 1, 3, 5, 6 }, 6, 3 },              // found at last index
                { new int[] { 1 }, 0, 0 },                       // single element, insert before
                { new int[] { 1 }, 1, 0 },                       // single element, found
                { new int[] { 1 }, 2, 1 },                       // single element, insert after
                { new int[] { 1, 3 }, 2, 1 },                    // two elements, insert between
                { new int[] { 1, 3 }, 3, 1 },                    // two elements, found at right
                { new int[] { -10, -5, 0, 5, 10 }, -7, 1 },      // negatives, insert
                { new int[] { -10, -5, 0, 5, 10 }, 10, 4 },      // negatives, found at end
                { new int[] { 2, 4, 6, 8, 10, 12 }, 11, 5 },     // even length, insert near end
                { new int[] { 2, 4, 6, 8, 10, 12 }, 3, 1 },      // even length, insert near start
                { new int[] { -10000, 10000 }, 0, 1 },           // constraint bounds
        };

        System.out.println("Running tests for P_35_SearchInsertPosition.searchInsert\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int target = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.searchInsert(nums, target);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("searchInsert: %d/%d tests passed\n", pass, tests.length);
    }
}