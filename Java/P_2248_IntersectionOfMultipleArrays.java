import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_2248_IntersectionOfMultipleArrays {
    /*
    Pretty straightforward hashmap approach. The tricky parts of this problem are iterating through the 2D array and 
    remembering to sort the output list.
    First, iterate through the inner arrays and store a count of all integers into a hashmap. Then, in a second for
    loop, iterate through all the keys in the map and add the integers that appear in ALL inner arrays (count equals
    the input array length) to an arraylist. Finally, sort the arraylist in ascending order and return it.
    
    Time: O(n * m + k log k), where n is the number of arrays, m is the average array length, and k is the number of 
    unique integers. We iterate through all elements (n * m), then sort the result (k log k).
    Space: O(k), where k is the number of unique integers. We store counts in a hashmap and results in an arraylist.
    */
    public List<Integer> intersection(int[][] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] arr : nums) {
            for (int num : arr) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int num : map.keySet()) {
            if (map.get(num) == nums.length) {
                list.add(num);
            }
        }

        Collections.sort(list);
        return list;
    }

    public static void main(String[] args) {
        P_2248_IntersectionOfMultipleArrays solver = new P_2248_IntersectionOfMultipleArrays();

        // Test cases: {input, expected}
        Object[][] tests = new Object[][] {
                { new int[][]{{3,1,2,4,5},{1,2,3,4},{3,4,5,6}}, List.of(3, 4) },
                { new int[][]{{1,2,3},{4,5,6}}, List.of() },
                { new int[][]{{1,2,3,4,5},{2,3,4,5,6},{3,4,5,6,7}}, List.of(3, 4, 5) },
                { new int[][]{{7,34,45,10,12,27,13},{27,21,45,10,12,13}}, List.of(10, 12, 13, 27, 45) },
                { new int[][]{{1,2,3},{1,2,3},{1,2,3}}, List.of(1, 2, 3) },
                { new int[][]{{1}}, List.of(1) },
                { new int[][]{{1},{1}}, List.of(1) },
                { new int[][]{{1},{2}}, List.of() },
                { new int[][]{{5,10,15,20},{10,15,20,25},{15,20,25,30}}, List.of(15, 20) },
                { new int[][]{{100},{100},{100}}, List.of(100) },
                { new int[][]{{1,2,3,4,5,6,7,8,9,10},{1,3,5,7,9},{1,5,9}}, List.of(1, 5, 9) },
                { new int[][]{{7},{7},{7},{7}}, List.of(7) },
                { new int[][]{{1,2,3},{2,3,4},{3,4,5}}, List.of(3) }
        };

        System.out.println("Running tests for P_2248_IntersectionOfMultipleArrays.intersection\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] input = (int[][]) tests[i][0];
            @SuppressWarnings("unchecked")
            List<Integer> expected = (List<Integer>) tests[i][1];
            List<Integer> actual = solver.intersection(input);
            
            boolean ok = expected.equals(actual);
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                    i + 1, arrayToString(input), expected, actual, 
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("intersection: %d/%d tests passed\n", pass, tests.length);
    }

    private static String arrayToString(int[][] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(java.util.Arrays.toString(arr[i]));
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
