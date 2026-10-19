public class P_410_SplitArrayLargestSum {
    /*
    Minimize the maximum subarray sum

    If the selected subarray sum gives us too many subarrays (more than k), we need to look for a larger value

    If the selected subarray sum gives us not enough subarrays, we need to look for a smaller value

    If selected subarray sum gives us enough subarrays, there still could be a smaller value
    */
    class Solution {
        public int splitArray(int[] nums, int k) {
            int left = 0;
            int right = 0;
            for (int n : nums) {
                left = Math.max(left, n);
                right += n;
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(nums, k, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(int[] nums, int k, int subarraySum) {
            int count = 1;
            int sum = 0;
            for (int n : nums) {
                if (sum + n > subarraySum) {
                    count++;
                    sum = n;
                } else {
                    sum += n;
                }
            }
            return count <= k;
        }
    }
}
