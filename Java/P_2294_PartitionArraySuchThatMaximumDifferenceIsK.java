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
}
