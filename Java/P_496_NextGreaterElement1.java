import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class P_496_NextGreaterElement1 {
    /*
    Initial attempt at the problem, brute force approach. For each element in nums1, we search for it in nums2,
    and then look for the next greater element to its right. If found, we store it; otherwise, we store -1.
    
    Time: O(n * m), where n is the length of nums1 and m is the length of nums2
    Space: O(n) for the output array
    */
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

    /*
    Stack (deque) with hashmap approach. We iterate through nums2 and maintain a stack of elements for 
    which we haven't found their next greater element yet. When we find a greater element, we pop all
    smaller elements from the stack and map them to this greater element in a hashmap. After processing
    nums2, we simply look up the next greater elements for nums1 in the hashmap.
    This solution has a much better time complexity than the brute force approach, but it has some small
    issues that can be improved, like using map.getOrDefault() to simplify the final lookup, and using
    deque.removeLast() directly in the first while loop instead of peeking first.
    
    Time: O(n + m), where n is the length of nums1 and m is the length of nums2. Each element in nums2
    is pushed and popped from the stack at most once, and we do a single pass through nums1 for the final
    lookup.
    Space: O(m) for the stack and hashmap in the worst case, where all elements in nums2 are in increasing
    order, so they are all stored in the stack and mapped in the hashmap.
    */
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

    /*
    Most optimal solution using the stack (deque) with hashmap approach. This version improves upon v2
    by correcting the two small issues mentioned earlier. It uses deque.removeLast() directly in the
    map.put() call, avoiding the redundant peek that v2 does inside the loop body. It also uses 
    map.getOrDefault() for the final lookup to simplify the code and eliminate the extra while loop.
    
    Same time and space complexities as v2.
    */
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

    public static void main(String[] args) {
        P_496_NextGreaterElement1 solver = new P_496_NextGreaterElement1();

        // Test cases: each test is {nums1 array, nums2 array, expected result}
        Object[][][] testSets = new Object[][][] {
            // Test 1: Example from LeetCode
            {
                {new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}, new int[]{-1, 3, -1}}
            },
            // Test 2: Example from LeetCode
            {
                {new int[]{2, 4}, new int[]{1, 2, 3, 4}, new int[]{3, -1}}
            },
            // Test 3: All elements have next greater
            {
                {new int[]{1, 2, 3}, new int[]{1, 2, 3, 4}, new int[]{2, 3, 4}}
            },
            // Test 4: No elements have next greater
            {
                {new int[]{5, 4, 3}, new int[]{5, 4, 3, 2, 1}, new int[]{-1, -1, -1}}
            },
            // Test 5: Single element
            {
                {new int[]{1}, new int[]{1, 2}, new int[]{2}}
            },
            // Test 6: Single element with no next greater
            {
                {new int[]{2}, new int[]{1, 2}, new int[]{-1}}
            },
            // Test 7: Mixed results
            {
                {new int[]{1, 5, 3}, new int[]{1, 3, 5, 7}, new int[]{3, 7, 5}}
            },
            // Test 8: Descending order in nums2
            {
                {new int[]{5, 3, 1}, new int[]{5, 4, 3, 2, 1}, new int[]{-1, -1, -1}}
            },
            // Test 9: Ascending order in nums2
            {
                {new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4, 5}, new int[]{2, 3, 4, 5}}
            },
            // Test 10: Large gap between elements
            {
                {new int[]{1, 10}, new int[]{1, 5, 10, 20}, new int[]{5, 20}}
            },
            // Test 11: Element at the end
            {
                {new int[]{4}, new int[]{1, 2, 3, 4}, new int[]{-1}}
            },
            // Test 12: Multiple queries for same pattern
            {
                {new int[]{2, 1, 3}, new int[]{1, 2, 3, 4}, new int[]{3, 2, 4}}
            }
        };

        System.out.println("Running tests for P_496_NextGreaterElement1.nextGreaterElement\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] nums1 = (int[]) testSet[j][0];
                int[] nums2 = (int[]) testSet[j][1];
                int[] expected = (int[]) testSet[j][2];
                int[] actual = solver.nextGreaterElement(nums1, nums2);
                
                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests1++;
                if (ok) pass1++;
                
                System.out.printf("  nextGreaterElement(%s, %s) => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(nums1),
                        java.util.Arrays.toString(nums2),
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_496_NextGreaterElement1.v2\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] nums1 = (int[]) testSet[j][0];
                int[] nums2 = (int[]) testSet[j][1];
                int[] expected = (int[]) testSet[j][2];
                int[] actual = solver.v2(nums1, nums2);
                
                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests2++;
                if (ok) pass2++;
                
                System.out.printf("  v2(%s, %s) => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(nums1),
                        java.util.Arrays.toString(nums2),
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_496_NextGreaterElement1.v3\n");
        int pass3 = 0;
        int totalTests3 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];
            
            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] nums1 = (int[]) testSet[j][0];
                int[] nums2 = (int[]) testSet[j][1];
                int[] expected = (int[]) testSet[j][2];
                int[] actual = solver.v3(nums1, nums2);
                
                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests3++;
                if (ok) pass3++;
                
                System.out.printf("  v3(%s, %s) => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(nums1),
                        java.util.Arrays.toString(nums2),
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass3, totalTests3);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("nextGreaterElement: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("v2: %d/%d tests passed\n", pass2, totalTests2);
        System.out.printf("v3: %d/%d tests passed\n", pass3, totalTests3);
    }
}
