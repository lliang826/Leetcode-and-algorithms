import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class P_901_OnlineStockSpan {
    /*
    My initial solution using a Stack (Deque in Java) and a Map to track indices. When a new price comes in, we pop
    all lower or equal prices from the stack to find the last higher price. Then we calculate the span based on the
    indices, which are stored in the map.
    
    Time: O(n), where n is the number of calls to next(). Each price is pushed and popped at most once.
    Space: O(n + n) => O(2n) => O(n), for the stack and the map.
    */
    class StockSpanner {
        private Deque<Integer> deque;
        private Map<Integer, Integer> map;
        int index;

        public StockSpanner() {
            deque = new ArrayDeque<>();
            map = new HashMap<>();
            index = 0;
        }

        public int next(int price) {
            while (!deque.isEmpty() && deque.peekLast() <= price) {
                deque.removeLast();
            }
            int span = index - map.getOrDefault(deque.peekLast(), -1);
            map.put(price, index);
            deque.addLast(price);
            this.index++;
            return span;
        }
    }

    /*
    Alternate solution using a Stack (Deque) and an ArrayList to store prices. Similar logic as above, but we use
    the ArrayList to retrieve prices by index instead of using a Map. The deque stores indices, and the array list
    stores the actual prices.
    
    Same time and space complexities as above, but slightly more efficient than the solution above since hash maps
    require hash code calculations.
    */
    class StockSpanner2 {
        private Deque<Integer> deque;
        private ArrayList<Integer> list;
        int index;

        public StockSpanner2() {
            deque = new ArrayDeque<>();
            list = new ArrayList<>();
            index = 0;
        }

        public int next(int price) {
            while (!deque.isEmpty() && list.get(deque.peekLast()) <= price) {
                deque.removeLast();
            }

            int span = index - (deque.isEmpty() ? -1 : deque.peekLast());
            list.add(price);
            deque.addLast(index);
            this.index++;
            return span;
        }
    }

    /*
    Most optimal solution using only a Stack (Deque). Each element in the stack is an integer array of size 2,
    represented as {price, span}. When a new price comes in, we pop all lower or equal prices from the stack,
    accumulating their spans. Finally, we push the new price along with its calculated span onto the stack.
    
    Same time and space complexities as the solutions above, but more space efficient since there is only a
    deque, and more time efficient since we no longer call the methods from the other data structures.
    */
    class StockSpanner3 {
        private Deque<int[]> deque;

        public StockSpanner3() {
            deque = new ArrayDeque<>();
        }

        public int next(int price) {
            int count = 0;
            while (!deque.isEmpty() && deque.peekLast()[0] <= price) {
                count += deque.removeLast()[1];
            }

            int span = count + 1;
            deque.addLast(new int[] { price, span });
            return span;
        }
    }

    public static void main(String[] args) {
        P_901_OnlineStockSpan solution = new P_901_OnlineStockSpan();

        // Test cases: each test is {prices array, expected spans array}
        Object[][][] testSets = new Object[][][] {
                // Test 1: Example from LeetCode
                {
                        { new int[] { 100, 80, 60, 70, 60, 75, 85 }, new int[] { 1, 1, 1, 2, 1, 4, 6 } }
                },
                // Test 2: All increasing prices
                {
                        { new int[] { 10, 20, 30, 40, 50 }, new int[] { 1, 2, 3, 4, 5 } }
                },
                // Test 3: All decreasing prices
                {
                        { new int[] { 50, 40, 30, 20, 10 }, new int[] { 1, 1, 1, 1, 1 } }
                },
                // Test 4: All same prices
                {
                        { new int[] { 30, 30, 30, 30 }, new int[] { 1, 2, 3, 4 } }
                },
                // Test 5: Single price
                {
                        { new int[] { 100 }, new int[] { 1 } }
                },
                // Test 6: Alternating high/low
                {
                        { new int[] { 100, 50, 100, 50, 100 }, new int[] { 1, 1, 3, 1, 5 } }
                },
                // Test 7: Multiple peaks
                {
                        { new int[] { 10, 20, 15, 25, 20, 30 }, new int[] { 1, 2, 1, 4, 1, 6 } }
                },
                // Test 8: Large span case
                {
                        { new int[] { 100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 200 },
                                new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11 } }
                },
                // Test 9: Two elements
                {
                        { new int[] { 50, 100 }, new int[] { 1, 2 } }
                },
                // Test 10: Peak at start
                {
                        { new int[] { 100, 80, 90, 85, 95 }, new int[] { 1, 1, 2, 1, 4 } }
                },
                // Test 11: Gradual increase with dips
                {
                        { new int[] { 5, 10, 8, 12, 11, 15 }, new int[] { 1, 2, 1, 4, 1, 6 } }
                },
                // Test 12: Complex pattern
                {
                        { new int[] { 31, 41, 48, 59, 79, 10, 20, 30, 40, 50, 60 },
                                new int[] { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6 } }
                },
                {
                        { new int[] { 100, 80, 80, 90 }, new int[] { 1, 1, 2, 3 } }
                },
                {
                        { new int[] { 100, 80, 60, 80 }, new int[] { 1, 1, 1, 3 } }
                },
        };

        System.out.println("Running tests for P_901_OnlineStockSpan.StockSpanner\n");
        int pass1 = 0;
        int totalTests1 = 0;

        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];

            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] prices = (int[]) testSet[j][0];
                int[] expected = (int[]) testSet[j][1];
                int[] actual = new int[prices.length];

                StockSpanner spanner = solution.new StockSpanner();
                for (int k = 0; k < prices.length; k++) {
                    actual[k] = spanner.next(prices[k]);
                }

                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests1++;
                if (ok)
                    pass1++;

                System.out.printf("  StockSpanner with prices %s => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(prices),
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_901_OnlineStockSpan.StockSpanner2\n");
        int pass2 = 0;
        int totalTests2 = 0;

        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];

            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] prices = (int[]) testSet[j][0];
                int[] expected = (int[]) testSet[j][1];
                int[] actual = new int[prices.length];

                StockSpanner2 spanner = solution.new StockSpanner2();
                for (int k = 0; k < prices.length; k++) {
                    actual[k] = spanner.next(prices[k]);
                }

                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests2++;
                if (ok)
                    pass2++;

                System.out.printf("  StockSpanner2 with prices %s => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(prices),
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_901_OnlineStockSpan.StockSpanner3\n");
        int pass3 = 0;
        int totalTests3 = 0;

        for (int i = 0; i < testSets.length; i++) {
            Object[][] testSet = testSets[i];

            System.out.printf("Test Set %d:\n", i + 1);
            for (int j = 0; j < testSet.length; j++) {
                int[] prices = (int[]) testSet[j][0];
                int[] expected = (int[]) testSet[j][1];
                int[] actual = new int[prices.length];

                StockSpanner3 spanner = solution.new StockSpanner3();
                for (int k = 0; k < prices.length; k++) {
                    actual[k] = spanner.next(prices[k]);
                }

                boolean ok = java.util.Arrays.equals(expected, actual);
                totalTests3++;
                if (ok)
                    pass3++;

                System.out.printf("  StockSpanner3 with prices %s => expected=%s, actual=%s => %s\n",
                        java.util.Arrays.toString(prices),
                        java.util.Arrays.toString(expected),
                        java.util.Arrays.toString(actual),
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass3, totalTests3);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("StockSpanner: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("StockSpanner2: %d/%d tests passed\n", pass2, totalTests2);
        System.out.printf("StockSpanner3: %d/%d tests passed\n", pass3, totalTests3);
    }
}