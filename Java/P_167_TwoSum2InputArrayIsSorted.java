public class P_167_TwoSum2InputArrayIsSorted {
    /*
    Two pointer approach. Since the input array is sorted, we can have one pointer at each end
    and move them towards each other based on the sum compared to the target. If the sum is
    greater than the target, we can decrease the sum by moving the right pointer left. If the
    sum is less than the target, we can increase the sum by moving the left pointer right.
    When we find the sum equal to the target, we break the loop and return the 1-based indices.
    
    Time: O(n) in the worst case, where n is the length of the input array. The 2 pointers move
    a total of n steps.
    Space: O(1), we only use a constant amount of extra space for the pointers.
    */
    public int[] twoSum(int[] numbers, int target) {
        int left = 1;
        int right = numbers.length;

        while (left < right) {
            int sum = numbers[left - 1] + numbers[right - 1];
            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                break;
            }
        }

        return new int[] { left, right };
    }
    
    /*
    Slightly more efficient than the solution above because it returns immediately when 
    the target sum is found, rather than breaking out of the loop first and then returning 
    outside. This eliminates one extra step. Also returns {-1, -1} if no solution is found, 
    although the problem guarantees exactly one solution exists.

    Same time and space complexities as above.
    */
    public int[] v2(int[] numbers, int target) {
        int left = 1;
        int right = numbers.length;

        while (left < right) {
            int sum = numbers[left - 1] + numbers[right - 1];
            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                return new int[] { left, right };
            }
        }

        return new int[] { -1, -1 };
    }
    
    public static void main(String[] args) {
        P_167_TwoSum2InputArrayIsSorted solver = new P_167_TwoSum2InputArrayIsSorted();

        // Test cases: each test is {numbers array, target, expected indices}
        Object[][][] testSets = new Object[][][] {
            // Test 1: Example from LeetCode
            {
                {new int[]{2, 7, 11, 15}, 9, new int[]{1, 2}}
            },
            // Test 2: Answer at the beginning
            {
                {new int[]{1, 2, 3, 4, 5}, 3, new int[]{1, 2}}
            },
            // Test 3: Answer at the end
            {
                {new int[]{1, 2, 3, 4, 5}, 9, new int[]{4, 5}}
            },
            // Test 4: Answer in the middle
            {
                {new int[]{1, 3, 5, 7, 9, 11}, 12, new int[]{1, 6}}
            },
            // Test 5: Two elements only
            {
                {new int[]{1, 2}, 3, new int[]{1, 2}}
            },
            // Test 6: Negative numbers
            {
                {new int[]{-10, -5, 0, 5, 10}, 0, new int[]{1, 5}}
            },
            // Test 7: All negative numbers
            {
                {new int[]{-5, -4, -3, -2, -1}, -9, new int[]{1, 2}}
            },
            // Test 8: With zero
            {
                {new int[]{-3, 0, 3, 6}, 3, new int[]{1, 4}}
            },
            // Test 9: Large numbers
            {
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 19, new int[]{9, 10}}
            },
            // Test 10: Same number used twice (different indices)
            {
                {new int[]{1, 2, 3, 3, 4, 5}, 6, new int[]{1, 6}}
            },
            // Test 11: Far apart indices
            {
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 13, new int[]{1, 12}}
            }
        };

        System.out.println("Running tests for P_167_TwoSum2InputArrayIsSorted.twoSum\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] numbers = (int[]) testSet[j][0];
                int target = (int) testSet[j][1];
                int[] expected = (int[]) testSet[j][2];
                int[] actual = solver.twoSum(numbers, target);
                
                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests1++;
                if (ok) pass1++;
                
                System.out.printf("  twoSum(%s, %d) => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(numbers), target,
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_167_TwoSum2InputArrayIsSorted.v2\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] numbers = (int[]) testSet[j][0];
                int target = (int) testSet[j][1];
                int[] expected = (int[]) testSet[j][2];
                int[] actual = solver.v2(numbers, target);
                
                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests2++;
                if (ok) pass2++;
                
                System.out.printf("  v2(%s, %d) => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(numbers), target,
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("twoSum: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("v2: %d/%d tests passed\n", pass2, totalTests2);
    }
}
