import java.util.HashMap;
import java.util.Map;

public class P_169_MajorityElement {
    /*
    Hashmap approach.
    First, we iterate through the input array and store a count of each element into a hashmap. Then, we iterate
    on the hashmap; the key with a count greater than the floor of n/2 is the majority element.
    The question's constraints guarantee that a majority element exists in the input array.
    
    Time: O(n), we iterate through the input array and through the hashmap
    Space: O(n), we build a hashmap using the array's elements
    */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {
                map.put(num, map.get(num) + 1);
            }
        }

        for (int key : map.keySet()) {
            if (map.get(key) > nums.length / 2) {
                return key;
            }
        }

        return 0;
    }
}
