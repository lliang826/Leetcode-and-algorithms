import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P_219_ContainsDuplicate2 {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }  else {
                map.put(nums[i], i);
            }
        }
        return false;
    }

    public boolean v2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            if (set.contains(nums[right])) {
                return true;
            } else {
                set.add(nums[right]);
            }
            
            if (right >= k) {
                set.remove(nums[left]);
                left++;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
