import java.util.Arrays;

public class P_27_RemoveElement {
    /*
    Replace every occurrence of val with a large value (Integer.MAX_VALUE) and 
    count the rest; sorting then pushes all large values to the end, leaving the
    rest in the first `count` slots.
    
    Time: O(n log n) - not that efficient because we have to sort
    Space: O(1) if we sort in place, no additional data structures
    */
    public int removeElement(int[] nums, int val) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                nums[i] = Integer.MAX_VALUE;
            } else {
                count++;
            }
        }

        Arrays.sort(nums);

        return count;
    }

    /*
    Two pointers, copy non-val elements onto val elements. 
    Single forward scan: `left` marks where the next non-val element goes while `right` 
    scans the array. Every non-val element is copied forward, PRESERVING ORDER.
    
    Time: O(n)
    Space: O(1)
    */
    public int v2(int[] nums, int val) {
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != val) {
                nums[left] = nums[right];
                left++;
            }
        }

        return left;
    }

    /*
    Two pointers, swap-from-end. 
    Instead of copying every non-val element forward, overwrite a val element 
    with the last unprocessed one and shrink the boundary. The copied-in value 
    may itself be val, so it gets re-checked on the next iteration (that's why 
    left only advances in the else branch). Order is not preserved, but the 
    number of writes is minimized.
    
    Time: O(n)
    Space: O(1)
    */
    public int v3(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            if (nums[left] == val) {
                nums[left] = nums[right];
                right--;
            } else {
                left++;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        P_27_RemoveElement solver = new P_27_RemoveElement();

        // Test cases: {input array, val, expected k}
        // LeetCode only checks the first k elements (order-independent), so we
        // validate by comparing the multiset of the first k elements.
        Object[][] tests = new Object[][] {
                { new int[] { 3, 2, 2, 3 }, 3, 2 },
                { new int[] { 0, 1, 2, 2, 3, 0, 4, 2 }, 2, 5 },
                { new int[] {}, 0, 0 },                    // empty array
                { new int[] { 1 }, 1, 0 },                 // single elem, removed
                { new int[] { 1 }, 2, 1 },                 // single elem, kept
                { new int[] { 2, 2, 2 }, 2, 0 },           // all removed
                { new int[] { 4, 5, 6 }, 9, 3 },           // none removed
                { new int[] { 3, 3 }, 3, 0 },              // all removed, len 2
                { new int[] { 2, 1, 2, 1, 2 }, 2, 2 },     // alternating
                { new int[] { 5, 5, 4, 5, 5 }, 5, 1 }      // single survivor in middle
        };

        int pass1 = runSuite(solver, tests, "removeElement", 1);
        System.out.println("\n" + "=".repeat(50));
        int pass2 = runSuite(solver, tests, "v2", 2);
        System.out.println("\n" + "=".repeat(50));
        int pass3 = runSuite(solver, tests, "v3", 3);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("removeElement: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", pass3, tests.length);
    }

    /*
    Runs all test cases against one method (selected by `which`) and prints
    PASS/FAIL per case. Returns the number of passing cases.
    */
    private static int runSuite(P_27_RemoveElement solver, Object[][] tests, String name, int which) {
        System.out.printf("Running tests for P_27_RemoveElement.%s\n\n", name);
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] original = (int[]) tests[i][0];
            int[] input = original.clone();
            int val = (int) tests[i][1];
            int expectedK = (int) tests[i][2];

            int k;
            switch (which) {
                case 1: k = solver.removeElement(input, val); break;
                case 2: k = solver.v2(input, val); break;
                default: k = solver.v3(input, val); break;
            }

            boolean ok = checkResult(input, k, original, val, expectedK);
            if (ok)
                pass++;
            System.out.printf("Test %d: val=%d => expectedK=%d, actualK=%d, first %d=%s => %s\n",
                    i + 1, val, expectedK, k, Math.max(k, 0),
                    Arrays.toString(Arrays.copyOf(input, Math.max(k, 0))),
                    (ok ? "PASS" : "FAIL"));
        }
        return pass;
    }

    /*
    Validates a result the way LeetCode does: k must match, and the multiset of
    the first k elements must equal the multiset of non-val elements from the
    original input (order is irrelevant).
    */
    private static boolean checkResult(int[] result, int k, int[] original, int val, int expectedK) {
        if (k != expectedK)
            return false;

        int[] first = Arrays.copyOf(result, k);
        Arrays.sort(first);

        int[] expected = Arrays.stream(original).filter(x -> x != val).toArray();
        Arrays.sort(expected);

        return Arrays.equals(first, expected);
    }
}
