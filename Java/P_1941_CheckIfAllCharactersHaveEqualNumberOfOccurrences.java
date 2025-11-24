import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P_1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences {
    public boolean areOccurrencesEqual(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        Set<Integer> set = new HashSet<>();
        for (int num : map.values()) {
            set.add(num);
        }

        return set.size() == 1;
    }

    public static void main(String[] args) {
        
    }
}
