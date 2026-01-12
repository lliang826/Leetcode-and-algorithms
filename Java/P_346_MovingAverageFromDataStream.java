import java.util.ArrayDeque;
import java.util.Queue;

public class P_346_MovingAverageFromDataStream {
    /*
    Queue approach, first attempt. Every time we call next(), we add the new value to the queue,
    and if the size of the queue exceeds the window size, we remove the oldest value. Then, we
    calculate the sum of the values in the queue to compute the average. But this solution's way
    of calculating the sum is incorrect because it compares Integer object references instead of
    their values.
    This solution wasn't accepted by Leetcode because of a precision error in the average calculation,
    but I still wanted to include it here for completeness.
    
    Time: O(n * m) for n calls to next() and m is the window size, since for each call we need to
    compute the sum of up to m elements in the queue.
    Space: O(m) for storing up to m elements in the queue.
    */
    class MovingAverage {
        private Queue<Integer> queue;
        private int windowSize;

        public MovingAverage(int size) {
            this.queue = new ArrayDeque<>();
            this.windowSize = size;
        }

        public double next(int val) {
            this.queue.offer(val);
            while (this.queue.size() > this.windowSize) {
                this.queue.poll();
            }

            int start = this.queue.poll();
            this.queue.offer(start);
            double sum = start;
            while (this.queue.peek() != start) {
                int num = this.queue.poll();
                sum += num;
                this.queue.offer(num);
            }

            return sum / this.queue.size();
        }
    }
    
    /*
    Fixed version of the above solution. Instead of using object comparison to determine when
    we've looped through all elements in the queue, we use a counter to track how many elements
    we've processed. This avoids the pitfalls of comparing Integer object references.
    
    Same time and space complexities as above.
    */
    class MovingAverage2 {
        private Queue<Integer> queue;
        private int windowSize;

        public MovingAverage2(int size) {
            this.queue = new ArrayDeque<>();
            this.windowSize = size;
        }

        public double next(int val) {
            this.queue.offer(val);
            while (this.queue.size() > this.windowSize) {
                this.queue.poll();
            }

            // Use counter i to track number of elements processed
            double sum = 0;
            int queueSize = this.queue.size();
            for (int i = 0; i < queueSize; i++) {
                int num = this.queue.poll();
                sum += num;
                this.queue.offer(num);
            }

            return sum / queueSize;
        }
    }

    /*
    Cleaner and more efficient implementation of the queue approach. Instead of recalculating the sum
    of the queue integers every time we call next(), we maintain a running sum that is updated as we
    add and remove elements from the queue. This reduces the time complexity of each next() call to O(1).
    
    Time: O(n) for n calls to next(), each call is O(1).
    Space: O(m) for storing up to m elements in the queue.
    */
    class MovingAverage3 {
        private Queue<Integer> queue;
        private int windowSize;
        private double sum;

        public MovingAverage3(int size) {
            this.queue = new ArrayDeque<>();
            this.windowSize = size;
            this.sum = 0;
        }
        
        public double next(int val) {
            this.queue.offer(val);
            this.sum += val;
            while (this.queue.size() > this.windowSize) {
                this.sum -= this.queue.poll();
            }
            return this.sum / this.queue.size();
        }
    }

    public static void main(String[] args) {
        P_346_MovingAverageFromDataStream solver = new P_346_MovingAverageFromDataStream();

        // Test cases: each test is an array of [windowSize, values[], expected_averages[]]
        int[][][] testValues = new int[][][] {
            // Test 1: Example from LeetCode
            {{3}, {1, 10, 3, 5}},
            // Test 2: Window size 1
            {{1}, {5, 10, 15}},
            // Test 3: Window size larger than number of elements
            {{5}, {1, 2, 3}},
            // Test 4: All same values
            {{3}, {5, 5, 5, 5}},
            // Test 5: Negative numbers
            {{2}, {-1, -2, -3, 4}},
            // Test 6: Single element window
            {{1}, {100}},
            // Test 7: Larger window with varied values
            {{4}, {1, 3, 5, 7, 9, 11}}
        };

        double[][] expectedAverages = new double[][] {
            // Test 1: Example from LeetCode
            {1.0, 5.5, 4.666666666666667, 6.0},
            // Test 2: Window size 1
            {5.0, 10.0, 15.0},
            // Test 3: Window size larger than number of elements
            {1.0, 1.5, 2.0},
            // Test 4: All same values
            {5.0, 5.0, 5.0, 5.0},
            // Test 5: Negative numbers
            {-1.0, -1.5, -2.5, 0.5},
            // Test 6: Single element window
            {100.0},
            // Test 7: Larger window with varied values
            {1.0, 2.0, 3.0, 4.0, 6.0, 8.0}
        };

        System.out.println("Running tests for P_346_MovingAverageFromDataStream.MovingAverage\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testValues.length; i++) {
            int windowSize = testValues[i][0][0];
            int[] values = testValues[i][1];
            double[] expected = expectedAverages[i];
            MovingAverage ma = solver.new MovingAverage(windowSize);
            
            System.out.printf("Test Set %d (window size = %d):\n", i + 1, windowSize);
            for (int j = 0; j < values.length; j++) {
                int val = values[j];
                double expectedAvg = expected[j];
                double actual = ma.next(val);
                
                boolean ok = Math.abs(expectedAvg - actual) < 1e-9;
                totalTests1++;
                if (ok) pass1++;
                
                System.out.printf("  next(%d) => expected=%.6f, actual=%.6f => %s\n",
                        val, expectedAvg, actual, (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_346_MovingAverageFromDataStream.MovingAverage2\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testValues.length; i++) {
            int windowSize = testValues[i][0][0];
            int[] values = testValues[i][1];
            double[] expected = expectedAverages[i];
            MovingAverage2 ma = solver.new MovingAverage2(windowSize);
            
            System.out.printf("Test Set %d (window size = %d):\n", i + 1, windowSize);
            for (int j = 0; j < values.length; j++) {
                int val = values[j];
                double expectedAvg = expected[j];
                double actual = ma.next(val);
                
                boolean ok = Math.abs(expectedAvg - actual) < 1e-9;
                totalTests2++;
                if (ok) pass2++;
                
                System.out.printf("  next(%d) => expected=%.6f, actual=%.6f => %s\n",
                        val, expectedAvg, actual, (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_346_MovingAverageFromDataStream.MovingAverage3\n");
        int pass3 = 0;
        int totalTests3 = 0;
        
        for (int i = 0; i < testValues.length; i++) {
            int windowSize = testValues[i][0][0];
            int[] values = testValues[i][1];
            double[] expected = expectedAverages[i];
            MovingAverage3 ma = solver.new MovingAverage3(windowSize);
            
            System.out.printf("Test Set %d (window size = %d):\n", i + 1, windowSize);
            for (int j = 0; j < values.length; j++) {
                int val = values[j];
                double expectedAvg = expected[j];
                double actual = ma.next(val);
                
                boolean ok = Math.abs(expectedAvg - actual) < 1e-9;
                totalTests3++;
                if (ok) pass3++;
                
                System.out.printf("  next(%d) => expected=%.6f, actual=%.6f => %s\n",
                        val, expectedAvg, actual, (ok ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
        System.out.printf("Summary: %d/%d tests passed.\n", pass3, totalTests3);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("MovingAverage: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("MovingAverage2: %d/%d tests passed\n", pass2, totalTests2);
        System.out.printf("MovingAverage3: %d/%d tests passed\n", pass3, totalTests3);
    }
}