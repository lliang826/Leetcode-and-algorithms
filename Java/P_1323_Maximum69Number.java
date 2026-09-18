public class P_1323_Maximum69Number {
    /*
    Greedy approach.

    Since we want to return the maximum number, all we have to do is replace the leftmost digit
    that is a 6 into a 9.
    There are multiple ways to do this, but in this solution, I converted the input integer 
    into a string first and then replaced the first '6' character into a '9' using substring.

    Time: O(n) for iterating through all digits/characters, where n is the number of digits

    Space: O(n), converting the integer into a string
    */
    public int maximum69Number(int num) {
        String s = Integer.toString(num);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '6') {
                String res = s.substring(0, i) + "9" + s.substring(i + 1);
                return Integer.parseInt(res);
            }
        }

        return num;
    }

    public static void main(String[] args) {
        P_1323_Maximum69Number solver = new P_1323_Maximum69Number();

        // Test cases: {input, expected output}
        int[][] tests = new int[][] {
                { 9669, 9969 },
                { 9996, 9999 },
                { 9999, 9999 },
                { 6, 9 },
                { 9, 9 },
                { 66, 96 },
                { 69, 99 },
                { 96, 99 },
                { 6666, 9666 },
                { 6999, 9999 },
                { 9666, 9966 },
                { 6969, 9969 }
        };

        System.out.println("Running tests for P_1323_Maximum69Number.maximum69Number\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int input = tests[i][0];
            int expected = tests[i][1];
            int actual = solver.maximum69Number(input);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%d => expected=%d, actual=%d => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("maximum69Number: %d/%d tests passed\n", pass, tests.length);
    }
}
