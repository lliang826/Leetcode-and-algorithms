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

    public static void main(String[] args) {
        P_169_MajorityElement solver = new P_169_MajorityElement();

        // Test cases: {input array, expected majority element}
        Object[][] tests = new Object[][] {
                { new int[] { 3, 2, 3 }, 3 },                          // basic odd-length
                { new int[] { 2, 2, 1, 1, 1, 2, 2 }, 2 },              // candidate flips then recovers
                { new int[] { 1 }, 1 },                                // single element
                { new int[] { 4, 4 }, 4 },                             // all same, even length
                { new int[] { 5, 5, 5, 5, 5 }, 5 },                    // all same, odd length
                { new int[] { 1, 2, 1 }, 1 },                          // majority at the ends
                { new int[] { 2, 1, 1 }, 1 },                          // majority at the tail
                { new int[] { -1, -1, -1, 2, 3 }, -1 },                // negative majority value
                { new int[] { 6, 5, 5, 6, 6, 6, 6 }, 6 },              // strong majority, interleaved
                { new int[] { 7, 7, 8, 8, 7, 7, 8, 7, 7 }, 7 }         // long array, repeated cancellations
        };

        System.out.println("Running tests for P_169_MajorityElement.majorityElement\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.majorityElement(input);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("majorityElement: %d/%d tests passed\n", pass, tests.length);
    }
}
