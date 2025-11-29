import java.util.HashMap;
import java.util.Map;

public class P_525_ContiguousArray {
    /*
    Prefix hashmap approach. 
    
    input = [0,1,1,1,1,1,0,0,0,1,0]
    prefix = [-1,0,1,2,3,4,3,2,1,2,1]
    
    The tricky part of this problem is figuring out what to store in the hashmap. As we iterate through the input array, we have to figure
    out if there's an equal number of zeroes and ones. I originally created 2 hash maps, 1 to track the prefix frequencies of ones and the
    other for the prefix frequencies of zeroes, but this didn't work. The trick is to sum/count all the zeroes and ones together, where 
    zeroes are represented by -1 and ones are represented by 1.
    E.g., 2 means we have 2 extra ones than zeroes and -3 means we have 3 extra zeroes than ones.
    
    We will also need to initialize a hash map, where the <key, value> pair is <count, index>. We also need to add an initial pair of (0, -1)
    to the map so the start of the array is a valid starting point for subarrays.
    E.g. input = [0, 1]
         count = [-1, 0]
    At index 1, the count is 0 because we've seen an equal number of zeroes and ones (1 zero and 1 one). We know that [0, 1] is the longest
    contiguous array of length 2. That means the count in the initial pair must also be 0. To find the value of the initial pair, we subtract
    the length from the index of the last element in the subarray: 1 - 2 = -1. So the initial pair is <0, -1>.
    
    As we iterate through the input integer array, we do 3 things:
    - Calculate the sum of zeroes and ones mentioned above
    - Check the hash map if the current count already exists. If it does, we can calculate the distance between that index and the current one
      - If the current count exists in the map, that means a previous index had the same number of ones and zeroes - that means an arbitrary
      number of ones and zeroes were added between that index and the current index, which is the length we're looking for
    - Add the current count and index to the map if it does NOT exist
      - If the current count already exists, we don't want to replace or update it since we're looking for the max length - a previous index
      would result in a larger length
    
    Time: O(n), where n is the number of integers in the input array - iterating through all integers in the input array
    Space: O(n) for the hash map -  worst case scenario, we could add all integers to the map
     */
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int maxLength = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count--;
            } else {
                count++;
            }

            if (map.containsKey(count)) {
                maxLength = Math.max(maxLength, i - map.get(count));
            }

            if (!map.containsKey(count)) {
                map.put(count, i);
            }
        }

        return maxLength;
    }

    /*
    Cleaner solution, but same time and space complexities.
    */
    public int v2(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        
        int maxLength = 0;
        int count = 0;
        
        for (int i = 0; i < nums.length; i++) {
            count += nums[i] == 0 ? -1 : 1;
            
            if (map.containsKey(count)) {
                maxLength = Math.max(maxLength, i - map.get(count));
            } else {
                map.put(count, i);
            }
        }
        
        return maxLength;
    }
    
    public static void main(String[] args) {
        P_525_ContiguousArray solver = new P_525_ContiguousArray();

        Object[][] tests = new Object[][] {
                { new int[] { 0, 1 }, 2 },
                { new int[] { 0, 1, 0 }, 2 },
                { new int[] { 0, 1, 1, 1, 1, 1, 0, 0, 0 }, 6 },
                { new int[] { 0 }, 0 },
                { new int[] { 1 }, 0 },
                { new int[] { 0, 0 }, 0 },
                { new int[] { 1, 1 }, 0 },
                { new int[] { 0, 0, 1, 1 }, 4 },
                { new int[] { 1, 1, 0, 0 }, 4 },
                { new int[] { 0, 1, 0, 1 }, 4 },
                { new int[] { 1, 0, 1, 0, 1 }, 4 },
                { new int[] { 0, 0, 0, 1, 1, 1 }, 6 },
                { new int[] { 1, 1, 1, 0, 0, 0 }, 6 },
                { new int[] { 0, 1, 1, 0, 1, 1, 1, 0, 0, 0 }, 10 },
                { new int[] { 1, 0, 0, 1, 0, 1, 0 }, 6 }
        };

        System.out.println("Running tests for P_525_ContiguousArray.findMaxLength\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.findMaxLength(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_525_ContiguousArray.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.v2(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("findMaxLength: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
