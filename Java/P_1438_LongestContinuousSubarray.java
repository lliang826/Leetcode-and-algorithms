import java.util.ArrayDeque;
import java.util.Deque;

public class P_1438_LongestContinuousSubarray {
    public int longestSubarrayV1(int[] nums, int limit) {
        Deque<Integer> inc = new ArrayDeque<>();
        Deque<Integer> dec = new ArrayDeque<>();
        int max = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            while (!inc.isEmpty() && inc.peekLast() > nums[right]) {
                inc.removeLast();
            }
            while (!dec.isEmpty() && dec.peekLast() < nums[right]) {
                dec.removeLast();
            }
            inc.addLast(nums[right]);
            dec.addLast(nums[right]);

            if (Math.abs(inc.peekFirst() - dec.peekFirst()) <= limit) {
                max = Math.max(max, right - left + 1);
            } else {
                if (!inc.isEmpty() && inc.peekFirst() == nums[left]) {
                    inc.removeFirst();
                }
                if (!dec.isEmpty() && dec.peekFirst() == nums[left]) {
                    dec.removeFirst();
                }
                left++;
            }
        }

        return max;
    }

    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> inc = new ArrayDeque<>();
        Deque<Integer> dec = new ArrayDeque<>();
        int max = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            while (!inc.isEmpty() && inc.peekLast() > nums[right]) {
                inc.removeLast();
            }
            while (!dec.isEmpty() && dec.peekLast() < nums[right]) {
                dec.removeLast();
            }
            inc.addLast(nums[right]);
            dec.addLast(nums[right]);

            while (dec.peekFirst() - inc.peekFirst() > limit) {
                if (!inc.isEmpty() && inc.peekFirst() == nums[left]) {
                    inc.removeFirst();
                }
                if (!dec.isEmpty() && dec.peekFirst() == nums[left]) {
                    dec.removeFirst();
                }
                left++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }
}