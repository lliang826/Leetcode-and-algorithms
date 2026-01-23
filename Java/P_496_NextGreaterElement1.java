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
}
