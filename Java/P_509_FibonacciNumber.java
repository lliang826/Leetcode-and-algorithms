public class P_509_FibonacciNumber {
    // 0 1 1 2 3 5 8 13 21 34 


    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return fib(n - 1) + fib(n - 2);
    }

    public int fib1(int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

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
}
