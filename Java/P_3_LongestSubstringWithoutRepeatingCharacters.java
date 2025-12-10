import java.util.HashMap;
import java.util.Map;

public class P_3_LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int currLength = 0;
        int lastIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c) && map.get(c) > lastIndex) {
                currLength = i - map.get(c);
                lastIndex = map.get(c);
            } else {
                currLength++;
            }

            map.put(c, i);
            maxLength = Math.max(maxLength, currLength);
        }

        return maxLength;
    }
    
    public static void main(String[] args) {
        
    }
}