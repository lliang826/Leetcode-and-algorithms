import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P_46_Permutations {
    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        int[] nums;

        public List<List<Integer>> permute(int[] nums) {
            this.nums = nums;
            backtrack(new ArrayList<>());
            return res;
        }

        private void backtrack(List<Integer> curr) {
            if (curr.size() == nums.length) {
                res.add(new ArrayList<>(curr));
                return;
            }

            for (int i : nums) {
                if (!curr.contains(i)) {
                    curr.add(i);
                    backtrack(curr);
                    curr.remove(curr.size() - 1);
                }
            }
        }
    }

    class Solution2 {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            backtrack(new ArrayList<>(), res, nums, new HashSet<>());
            return res;
        }

        private void backtrack(List<Integer> curr, List<List<Integer>> res, int[] nums, Set<Integer> set) {
            if (curr.size() == nums.length) {
                res.add(new ArrayList<>(curr));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (!set.contains(nums[i])) {
                    curr.add(nums[i]);
                    set.add(nums[i]);
                    backtrack(curr, res, nums, set);
                    curr.remove(curr.size() - 1);
                    set.remove(nums[i]);
                }
            }
        }
    }

    class Solution3 {
        List<List<Integer>> res = new ArrayList<>();
        int[] nums;

        public List<List<Integer>> permute(int[] nums) {
            this.nums = nums;
            backtrack(new ArrayList<>(), new HashSet<>());
            return res;
        }

        private void backtrack(List<Integer> curr, Set<Integer> set) {
            if (curr.size() == nums.length) {
                res.add(new ArrayList<>(curr));
                return;
            }

            for (int i : nums) {
                if (!set.contains(i)) {
                    curr.add(i);
                    set.add(i);
                    backtrack(curr, set);
                    curr.remove(curr.size() - 1);
                    set.remove(i);
                }
            }
        }
    }

    class Solution4 {
        List<List<Integer>> res = new ArrayList<>();
        int[] nums;

        public List<List<Integer>> permute(int[] nums) {
            this.nums = nums;
            backtrack(new ArrayList<>(), new boolean[nums.length]);
            return res;
        }

        private void backtrack(List<Integer> curr, boolean[] used) {
            if (curr.size() == nums.length) {
                res.add(new ArrayList<>(curr));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (!used[i]) {
                    curr.add(nums[i]);
                    used[i] = true;
                    backtrack(curr, used);
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

    /*
    Order-independent check: since nums has distinct values, the output is correct iff it has exactly n! lists,
    no duplicates, and every list is a rearrangement of nums.
    */
    private static boolean isValid(int[] nums, List<List<Integer>> actual) {
        if (actual.size() != factorial(nums.length)) {
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
        P_46_Permutations outer = new P_46_Permutations();

        int[][] tests = new int[][] {
                // LeetCode examples
                { 1, 2, 3 },
                { 0, 1 },
                { 1 },
                // Negative values
                { -1, 5 },
                // Unsorted input: output must still contain every ordering
                { 3, 1, 2 },
                // Boundary values from constraints (-10 <= nums[i] <= 10)
                { -10, 0, 10 },
                // Larger inputs: 24 and 120 permutations
                { 1, 2, 3, 4 },
                { 5, 4, 6, 2, 1 },
                // Max length (n = 6): 720 permutations
                { 1, 2, 3, 4, 5, 6 },
                { -10, -5, 0, 5, 10, 7 }
        };

        System.out.println("Running tests for P_46_Permutations.Solution.permute\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = tests[i];
            // Fresh instance per test since Solution stores results in fields
            Solution solver = outer.new Solution();
            List<List<Integer>> actual = solver.permute(nums);

            boolean ok = isValid(nums, actual);
            if (ok)
                pass1++;
            String output = nums.length <= 3 ? ", output=" + actual : "";
            System.out.printf("Test %d: nums=%s => expected count=%d, actual count=%d%s => %s\n",
                    i + 1, Arrays.toString(nums), factorial(nums.length), actual.size(), output,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_46_Permutations.Solution2.permute\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = tests[i];
            Solution2 solver = outer.new Solution2();
            List<List<Integer>> actual = solver.permute(nums);

            boolean ok = isValid(nums, actual);
            if (ok)
                pass2++;
            String output = nums.length <= 3 ? ", output=" + actual : "";
            System.out.printf("Test %d: nums=%s => expected count=%d, actual count=%d%s => %s\n",
                    i + 1, Arrays.toString(nums), factorial(nums.length), actual.size(), output,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_46_Permutations.Solution3.permute\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = tests[i];
            Solution3 solver = outer.new Solution3();
            List<List<Integer>> actual = solver.permute(nums);

            boolean ok = isValid(nums, actual);
            if (ok)
                pass3++;
            String output = nums.length <= 3 ? ", output=" + actual : "";
            System.out.printf("Test %d: nums=%s => expected count=%d, actual count=%d%s => %s\n",
                    i + 1, Arrays.toString(nums), factorial(nums.length), actual.size(), output,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_46_Permutations.Solution4.permute\n");
        int pass4 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] nums = tests[i];
            Solution4 solver = outer.new Solution4();
            List<List<Integer>> actual = solver.permute(nums);

            boolean ok = isValid(nums, actual);
            if (ok)
                pass4++;
            String output = nums.length <= 3 ? ", output=" + actual : "";
            System.out.printf("Test %d: nums=%s => expected count=%d, actual count=%d%s => %s\n",
                    i + 1, Arrays.toString(nums), factorial(nums.length), actual.size(), output,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution.permute: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2.permute: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("Solution3.permute: %d/%d tests passed\n", pass3, tests.length);
        System.out.printf("Solution4.permute: %d/%d tests passed\n", pass4, tests.length);
    }
}
