public class P_509_FibonacciNumber {
    /*
    0 1 1 2 3 5 8 13 21 34 
    
    Recursive approach. The base case is where a recursive algorithm stops calling itself, and in this case,
    it's when n is 0 or 1. Otherwise, the function calls itself twice to compute the sum of the two
    preceding Fibonacci numbers.
    
    Time: O(2^n) - exponential because each call generates approximately two more calls.
    e.g., fib(6):
    Level 0:   1 call   fib(6)
    Level 1:   2 calls  fib(5), fib(4)
    Level 2:   4 calls  fib(4), fib(3), fib(3), fib(2)
    Level 3:   8 calls  fib(3), fib(2), fib(2), fib(1), fib(2), fib(1), fib(1), fib(0)
    ...
    
    Space: O(n) - the maximum depth of the recursion stack.
    e.g., fib(6)
    Level 0:   fib(6)
    Level 1:   fib(5)
    Level 2:   fib(4)
    Level 3:   fib(3)
    Level 4:   fib(2)
    Level 5:   fib(1)
    
    Recursion tree is drawn top-down, while the recursion stack is bottom-up (we push and pop from the bottom).
    The maximum depth of the recursion stack is n.
    */
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return fib(n - 1) + fib(n - 2);
    }

    /*
    More concise version of the above recursive approach. Instead of having two separate base cases,
    we can combine them into a single condition that checks if n is less than or equal to 1.
    
    Same time and space complexity as above.
    */
    public int fib1(int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    /*
    0 1 1 2 3 5 8 13 21 34
    0 1 2 3 4 5 6 7  8  9
    
    Iterative approach. We start from the base cases and build up to the desired Fibonacci number
    by iteratively calculating each Fibonacci number up to n. We maintain two variables to store
    the two most recent Fibonacci numbers, updating them as we progress through the loop. We also
    store the current sum to return at the end.
    
    Time: O(n) - we compute each Fibonacci number from 2 to n once.
    Space: O(1) - we only use a constant amount of space for the variables
    */
    public int fib2(int n) {
        if (n <= 1) {
            return n;
        }

        int twoPrev = 0;
        int prev = 1;
        int sum = 0;

        for (int i = 2; i <= n; i++) {
            sum = twoPrev + prev;
            twoPrev = prev;
            prev = sum;
        }
        return sum;
    }
    
    /*
    Improved version of the above iterative approach. Since the variable 'sum' is only used within the
    loop, we can remove it and return 'prev' at the end, which holds the value of the nth Fibonacci number.

    Same time and space complexity as above.
    */
    public int fib3(int n) {
        if (n <= 1) {
            return n;
        }

        int twoPrev = 0;
        int prev = 1;

        for (int i = 2; i <= n; i++) {
            int sum = twoPrev + prev;
            twoPrev = prev;
            prev = sum;
        }
        return prev;
    }
    
    public static void main(String[] args) {
        P_509_FibonacciNumber solver = new P_509_FibonacciNumber();

        // Test cases: each test is {input n, expected result}
        Object[][] testSets = new Object[][] {
            // Test 1: Base case - F(0)
            {0, 0},
            // Test 2: Base case - F(1)
            {1, 1},
            // Test 3: F(2)
            {2, 1},
            // Test 4: F(3)
            {3, 2},
            // Test 5: F(4)
            {4, 3},
            // Test 6: F(5)
            {5, 5},
            // Test 7: F(6)
            {6, 8},
            // Test 8: F(7)
            {7, 13},
            // Test 9: F(8)
            {8, 21},
            // Test 10: F(9)
            {9, 34},
            // Test 11: F(10)
            {10, 55},
            // Test 12: F(15)
            {15, 610},
            // Test 13: F(20)
            {20, 6765},
            // Test 14: F(25)
            {25, 75025},
            // Test 15: F(30)
            {30, 832040}
        };

        System.out.println("Running tests for P_509_FibonacciNumber.fib\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            int n = (int) testSets[i][0];
            int expected = (int) testSets[i][1];
            int actual = solver.fib(n);
            
            boolean ok = expected == actual;
            totalTests1++;
            if (ok) pass1++;
            
            System.out.printf("  Test %d: fib(%d) => expected=%d, actual=%d => %s\n",
                    i + 1, n, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_509_FibonacciNumber.fib1\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            int n = (int) testSets[i][0];
            int expected = (int) testSets[i][1];
            int actual = solver.fib1(n);
            
            boolean ok = expected == actual;
            totalTests2++;
            if (ok) pass2++;
            
            System.out.printf("  Test %d: fib1(%d) => expected=%d, actual=%d => %s\n",
                    i + 1, n, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_509_FibonacciNumber.fib2\n");
        int pass3 = 0;
        int totalTests3 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            int n = (int) testSets[i][0];
            int expected = (int) testSets[i][1];
            int actual = solver.fib2(n);
            
            boolean ok = expected == actual;
            totalTests3++;
            if (ok) pass3++;
            
            System.out.printf("  Test %d: fib2(%d) => expected=%d, actual=%d => %s\n",
                    i + 1, n, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass3, totalTests3);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_509_FibonacciNumber.fib3\n");
        int pass4 = 0;
        int totalTests4 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            int n = (int) testSets[i][0];
            int expected = (int) testSets[i][1];
            int actual = solver.fib3(n);
            
            boolean ok = expected == actual;
            totalTests4++;
            if (ok) pass4++;
            
            System.out.printf("  Test %d: fib3(%d) => expected=%d, actual=%d => %s\n",
                    i + 1, n, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass4, totalTests4);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("fib: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("fib1: %d/%d tests passed\n", pass2, totalTests2);
        System.out.printf("fib2: %d/%d tests passed\n", pass3, totalTests3);
        System.out.printf("fib3: %d/%d tests passed\n", pass4, totalTests4);
    }
}
