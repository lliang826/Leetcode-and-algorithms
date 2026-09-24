public class P_704_BinarySearch {
    /*
    Binary search implementation with overflow protection.

    Key implementation details:
    - Uses (right - left) / 2 + left instead of (left + right) / 2 to prevent integer overflow
      - The difference between the two pointers is the mid point; add this difference to the left
        pointer to reach it
    - The condition left <= right ensures we check all possible positions

    Time complexity: O(log n) because we are halving the search space in each iteration
    Space complexity: O(1) because we are using pointers, no additional data structures
    */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

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

        return -1;
    }

    /*
    We can also return the left pointer, which will be at the index where the target integer would
    need to be inserted to to maintain the array nums being sorted (if target doesn't exist).

    Same time and space complexities as above.
    */
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // target is not in arr, but left is at the insertion point
        return left;
    }

    /*
    Leftmost insertion point: if the target exists, this is the index of its leftmost duplicate;
    otherwise it is where target would be inserted. When the target is found, keep going left.
    The answer is the first copy of it.

    right starts at nums.length so "past the end" is a possible answer, and the loop uses
    left < right because right = mid does not shrink the range when left == right.

    Time: O(log n)
    Space: O(1)
    */
    public int duplicatesLeftMostInsertionPoint(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (target <= nums[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /*
    Rightmost insertion point: the first index where nums[i] > target. If target exists, this is
    one past its rightmost duplicate (rightmost duplicate = result - 1); otherwise it is where
    target would be inserted. When nums[mid] == target we move past mid (left = mid + 1) to keep
    searching right.

    Time: O(log n)
    Space: O(1)
    */
    public int duplicatesRightMostInsertionPoint(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (target < nums[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /*
    Rightmost index: the last index where nums[i] <= target. If target exists, this is its
    rightmost duplicate. Same search as duplicatesRightMostInsertionPoint; the answer is the index
    just before the insertion point. If target is missing, this is the last element smaller than
    target, or -1 if every element is larger.

    Time: O(log n)
    Space: O(1)
    */
    public int duplicatesRightMostIndex(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (target < nums[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left - 1;
    }

    public static void main(String[] args) {
        P_704_BinarySearch solver = new P_704_BinarySearch();

        // Test cases: {nums, target, expected}
        Object[][] tests = new Object[][] {
                { new int[] { -1, 0, 3, 5, 9, 12 }, 9, 4 },
                { new int[] { -1, 0, 3, 5, 9, 12 }, 2, -1 },
                { new int[] { 5 }, 5, 0 },
                { new int[] { 5 }, 3, -1 },
                { new int[] { 1, 3, 5, 7, 9 }, 1, 0 },
                { new int[] { 1, 3, 5, 7, 9 }, 9, 4 },
                { new int[] { 1, 3, 5, 7, 9 }, 5, 2 },
                { new int[] { 1, 3, 5, 7, 9 }, 4, -1 },
                { new int[] { 1, 3, 5, 7, 9 }, 0, -1 },
                { new int[] { 1, 3, 5, 7, 9 }, 10, -1 },
                { new int[] { 2, 4, 6, 8, 10, 12, 14, 16 }, 8, 3 },
                { new int[] { 2, 4, 6, 8, 10, 12, 14, 16 }, 1, -1 },
                { new int[] { 2, 4, 6, 8, 10, 12, 14, 16 }, 17, -1 },
                { new int[] { -10, -5, 0, 5, 10 }, -5, 1 },
                { new int[] { -10, -5, 0, 5, 10 }, 0, 2 },
                { new int[] { 1, 2 }, 1, 0 },
                { new int[] { 1, 2 }, 2, 1 },
                { new int[] { 1, 2 }, 3, -1 }
        };

        System.out.println("Running tests for P_704_BinarySearch.search\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = (int[]) tests[i][0];
            int target = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.search(nums, target);
            boolean ok = (expected == actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        // Test cases: {nums, target, expected index or insertion point}
        Object[][] insertTests = new Object[][] {
                { new int[] { -1, 0, 3, 5, 9, 12 }, 9, 4 },
                { new int[] { -1, 0, 3, 5, 9, 12 }, 2, 2 },
                { new int[] { 1, 3, 5, 6 }, 5, 2 },
                { new int[] { 1, 3, 5, 6 }, 2, 1 },
                { new int[] { 1, 3, 5, 6 }, 7, 4 },
                { new int[] { 1, 3, 5, 6 }, 0, 0 },
                { new int[] { 5 }, 5, 0 },
                { new int[] { 5 }, 3, 0 },
                { new int[] { 5 }, 8, 1 },
                { new int[] { 1, 2 }, 2, 1 },
                { new int[] { -10, -5, 0, 5, 10 }, -7, 1 },
                { new int[] {}, 1, 0 }
        };

        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nRunning tests for P_704_BinarySearch.binarySearch\n");
        int passInsert = 0;
        for (int i = 0; i < insertTests.length; i++) {
            int[] nums = (int[]) insertTests[i][0];
            int target = (int) insertTests[i][1];
            int expected = (int) insertTests[i][2];
            int actual = solver.binarySearch(nums, target);
            boolean ok = (expected == actual);
            if (ok)
                passInsert++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        // Test cases: {nums, target, expectedLeftMost, expectedRightMostInsertionPoint, expectedRightMostIndex}
        Object[][] dupTests = new Object[][] {
                { new int[] { 1, 2, 2, 2, 3 }, 2, 1, 4, 3 },
                { new int[] { 2, 2, 2, 2 }, 2, 0, 4, 3 },
                { new int[] { 1, 1, 2, 3 }, 1, 0, 2, 1 },
                { new int[] { 1, 2, 3, 3 }, 3, 2, 4, 3 },
                { new int[] { -3, -3, 0, 0, 0, 7 }, 0, 2, 5, 4 },
                { new int[] { 1, 3, 5, 7 }, 5, 2, 3, 2 },
                { new int[] { 5 }, 5, 0, 1, 0 },
                { new int[] { 5 }, 4, 0, 0, -1 },
                { new int[] { 1, 2, 2, 4 }, 3, 3, 3, 2 },
                { new int[] { 1, 2, 3 }, 0, 0, 0, -1 },
                { new int[] { 1, 2, 3 }, 5, 3, 3, 2 },
                { new int[] {}, 1, 0, 0, -1 }
        };

        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nRunning tests for P_704_BinarySearch.duplicatesLeftMostIndex\n");
        int passLeft = 0;
        for (int i = 0; i < dupTests.length; i++) {
            int[] nums = (int[]) dupTests[i][0];
            int target = (int) dupTests[i][1];
            int expected = (int) dupTests[i][2];
            int actual = solver.duplicatesLeftMostInsertionPoint(nums, target);
            boolean ok = (expected == actual);
            if (ok)
                passLeft++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nRunning tests for P_704_BinarySearch.duplicatesRightMostInsertionPoint\n");
        int passRight = 0;
        for (int i = 0; i < dupTests.length; i++) {
            int[] nums = (int[]) dupTests[i][0];
            int target = (int) dupTests[i][1];
            int expected = (int) dupTests[i][3];
            int actual = solver.duplicatesRightMostInsertionPoint(nums, target);
            boolean ok = (expected == actual);
            if (ok)
                passRight++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nRunning tests for P_704_BinarySearch.duplicatesRightMostIndex\n");
        int passRightIndex = 0;
        for (int i = 0; i < dupTests.length; i++) {
            int[] nums = (int[]) dupTests[i][0];
            int target = (int) dupTests[i][1];
            int expected = (int) dupTests[i][4];
            int actual = solver.duplicatesRightMostIndex(nums, target);
            boolean ok = (expected == actual);
            if (ok)
                passRightIndex++;
            System.out.printf("Test %d: nums=%s, target=%d => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(nums), target, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("search: %d/%d tests passed\n", pass, tests.length);
        System.out.printf("binarySearch: %d/%d tests passed\n", passInsert, insertTests.length);
        System.out.printf("duplicatesLeftMostIndex: %d/%d tests passed\n", passLeft, dupTests.length);
        System.out.printf("duplicatesRightMostInsertionPoint: %d/%d tests passed\n", passRight, dupTests.length);
        System.out.printf("duplicatesRightMostIndex: %d/%d tests passed\n", passRightIndex, dupTests.length);
    }
}
