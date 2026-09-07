import java.util.Arrays;

public class P_2294_PartitionArraySuchThatMaximumDifferenceIsK {
    /*
    Greedy algorithm approach.
    */
    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);
        int first = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (first + k < nums[i]) {
                count++;
                first = nums[i];
            }
        }

        return count;
    }

    public static void main(String[] args) {
        P_2294_PartitionArraySuchThatMaximumDifferenceIsK solver = new P_2294_PartitionArraySuchThatMaximumDifferenceIsK();

        // Test cases: {input nums, k, expected number of subsequences}
        Object[][] tests = new Object[][] {
                { new int[] { 3, 6, 1, 2, 5 }, 2, 2 },
                { new int[] { 1, 2, 3 }, 1, 2 },
                { new int[] { 2, 2, 4, 5 }, 0, 3 },
                { new int[] { 1 }, 0, 1 },
                { new int[] { 1, 1, 1, 1 }, 0, 1 },
                { new int[] { 1, 3 }, 2, 1 },
                { new int[] { 1, 4 }, 2, 2 },
                { new int[] { 10, 9, 8, 7, 6, 5 }, 1, 3 },
                { new int[] { 1, 5, 9, 13 }, 3, 4 },
                { new int[] { 100000, 1, 50000 }, 99999, 1 },
                { new int[] { 1, 2, 3, 4, 5, 6 }, 5, 1 },
        };

        System.out.println("Running tests for P_2294_PartitionArraySuchThatMaximumDifferenceIsK.partitionArray\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int k = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.partitionArray(input.clone(), k);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: nums=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, Arrays.toString(input), k, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("partitionArray: %d/%d tests passed\n", pass1, tests.length);
    }
}
