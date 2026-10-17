public class P_1231_Divide_Chocolate {
    class Solution {
        public int maximizeSweetness(int[] sweetness, int k) {
            int left = 1;
            int right = 0;
            for (int i : sweetness) {
                right += i;
            }

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(sweetness, k, mid)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return right;
        }

        private boolean check(int[] sweetness, int k, int ts) {
            int count = 0;
            int sum = 0;
            for (int i = 0; i < sweetness.length; i++) {
                sum += sweetness[i];
                if (sum >= ts) {
                    count++;
                    sum = 0;
                }
            }

            int pieces = k + 1;
            return count >= pieces;
        }
    }

    class v2 {
        public int maximizeSweetness(int[] sweetness, int k) {
            int left = Integer.MAX_VALUE;
            int sum = 0;
            for (int i : sweetness) {
                left = Math.min(i, left);
                sum += i;
            }
            int right = sum / (k + 1);

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(sweetness, k, mid)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return right;
        }

        private boolean check(int[] sweetness, int k, int ts) {
            int count = 0;
            int sum = 0;
            for (int i = 0; i < sweetness.length; i++) {
                sum += sweetness[i];
                if (sum >= ts) {
                    count++;
                    sum = 0;
                    if (count == k + 1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
