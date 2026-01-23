import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class P_496_NextGreaterElement1 {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            boolean found = false;
            for (int j = 0; j < nums2.length; j++) {
                if (nums2[j] > nums1[i] && found == true) {
                    ans[i] = nums2[j];
                    break;
                }
                if (nums1[i] == nums2[j]) {
                    found = true;
                }
            }
            if (ans[i] == 0) {
                ans[i] = -1;
            }
        }

        return ans;
    }

    public int[] v2(int[] nums1, int[] nums2) {
        Deque<Integer> deque = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums2) {
            while (!deque.isEmpty() && deque.peekLast() < num) {
                map.put(deque.peekLast(), num);
                deque.removeLast();
            }
            deque.addLast(num);
        }

        while (!deque.isEmpty()) {
            map.put(deque.removeLast(), -1);
        }

        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.get(nums1[i]);
        }

        return ans;
    }

    public int[] v3(int[] nums1, int[] nums2) {
        Deque<Integer> deque = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums2) {
            while (!deque.isEmpty() && deque.peekLast() < num) {
                map.put(deque.removeLast(), num);
            }
            deque.addLast(num);
        }

        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.getOrDefault(nums1[i], -1);
        }

        return ans;
    }
}
