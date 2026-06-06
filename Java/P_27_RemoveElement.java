import java.util.Arrays;

public class P_27_RemoveElement {
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

    public int v2(int[] nums, int val) {
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

    public int v3(int[] nums, int val) {
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}
