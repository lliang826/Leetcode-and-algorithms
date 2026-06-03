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

    /*
    Boyer-Moore Voting approach.
    Picture the majority element as one team and all other elements combined as the opposing team. Each 
    time you see a different element, you cancel one majority occurrence against one non-majority occurrence 
    (count--). Since the majority element appears more than ⌊n/2⌋ times, it outnumbers everyone else put 
    together — so it can never be fully cancelled. Whatever is left standing when the scan ends is the 
    majority element.
    
    We keep a single candidate and a running count. Scanning left to right: when count is 0 we adopt the
    current element as the candidate; when the current element matches the candidate we increment count (a
    vote for it); otherwise we decrement count (a vote against, cancelling one majority occurrence with one
    non-majority occurrence). Because the majority element appears more than floor(n/2) times, it outnumbers
    every other element combined and can never be fully cancelled, so it survives as the final candidate.
    The question's constraints guarantee a majority element exists, so no verification pass is needed.
    
    By cancelling out every instance of the majority element with another element, we will eventually have 
    one or more instances of the majority element left over.
    This only works if the input array has a clear majority element; if we had [1,1,2,2] it wouldn't work.
    
    Time: O(n), a single pass through the input array
    Space: O(1), only two variables regardless of input size
    */
    public int boyerMoore(int[] nums) {
        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }

            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
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
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.majorityElement(input);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_169_MajorityElement.boyerMoore\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.boyerMoore(input);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("majorityElement: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("boyerMoore: %d/%d tests passed\n", pass2, tests.length);
    }
}
