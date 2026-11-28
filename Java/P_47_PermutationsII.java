import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_47_PermutationsII {
    class Solution {
        public List<List<Integer>> permuteUnique(int[] nums) {
            Set<List<Integer>> res = new HashSet<>();
            backtrack(new ArrayList<>(), res, nums, new boolean[nums.length]);
            return new ArrayList<>(res);
        }

        private void backtrack(List<Integer> curr, Set<List<Integer>> res, int[] nums, boolean[] used) {
            if (curr.size() == nums.length) {
                res.add(new ArrayList<>(curr));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (!used[i]) {
                    curr.add(nums[i]);
                    used[i] = true;
                    backtrack(curr, res, nums, used);
                    curr.remove(curr.size() - 1);
                    used[i] = false;
                }
            }
        }
    }

    private static int factorial(int n) {
        int result = 1;
        for (int k = 2; k <= n; k++) {
            result *= k;
        }
        return result;
    }

    // Number of unique permutations: n! / (c1! * c2! * ...), where each c is how many times a value repeats
    private static int uniqueCount(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        int result = factorial(nums.length);
        for (int c : counts.values()) {
            result /= factorial(c);
        }
        return result;
    }

    /*
    Order-independent check: the output is correct iff it has exactly uniqueCount(nums) lists,
    no duplicates, and every list is a rearrangement of nums.
    */
    private static boolean isValid(int[] nums, List<List<Integer>> actual) {
        if (actual.size() != uniqueCount(nums)) {
            return false;
        }

        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);
        Set<List<Integer>> unique = new HashSet<>();

        for (List<Integer> perm : actual) {
            int[] sortedPerm = perm.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(sortedPerm);
            if (!Arrays.equals(sortedNums, sortedPerm) || !unique.add(perm)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        P_47_PermutationsII outer = new P_47_PermutationsII();

        int[][] tests = new int[][] {
                // LeetCode examples
                { 1, 1, 2 },
                { 1, 2, 3 },
                // Single element
                { 1 },
                // All duplicates: only one unique permutation
                { 1, 1 },
                { 2, 2, 2 },
                // Duplicates not next to each other in the input
                { 1, 2, 1 },
                { 3, 3, 0, 3 },
                // Negative values and zeros, two pairs of duplicates
                { -1, -1, 0, 0 },
                { 1, 1, 2, 2 },
                // Three copies of one value mixed with distinct values: 5! / 3! = 20
                { 0, 1, 0, 0, 9 },
                // Max length (n = 8) with four pairs: 8! / (2!^4) = 2520
                { -10, 10, -10, 10, 0, 0, 5, 5 },
                // Max length, all distinct: 8! = 40320
                { 1, 2, 3, 4, 5, 6, 7, 8 },
                // Max length, all the same: 40320 generated, only 1 unique
                { 7, 7, 7, 7, 7, 7, 7, 7 }
        };

        System.out.println("Running tests for P_47_PermutationsII.Solution.permuteUnique\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = tests[i];
            Solution solver = outer.new Solution();
            List<List<Integer>> actual = solver.permuteUnique(nums);

            boolean ok = isValid(nums, actual);
            if (ok)
                pass1++;
            int expectedCount = uniqueCount(nums);
            String output = expectedCount <= 6 ? ", output=" + actual : "";
            System.out.printf("Test %d: nums=%s => expected count=%d, actual count=%d%s => %s\n",
                    i + 1, Arrays.toString(nums), expectedCount, actual.size(), output,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution.permuteUnique: %d/%d tests passed\n", pass1, tests.length);
    }
}
