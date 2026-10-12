public class P_1283_FindTheSmallestDivisorGivenAThreshold {
    class Solution {
        public int smallestDivisor(int[] nums, int threshold) {
            int left = 1;
            int right = 0;
            for (int n : nums) {
                right = Math.max(right, n);
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(nums, threshold, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(int[] nums, int threshold, double divisor) {
            double sum = 0;
            for (double n : nums) {
                sum += Math.ceil(n / divisor);
            }

            return sum <= threshold;
        }
    }
}
