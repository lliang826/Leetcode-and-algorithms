import java.util.HashMap;
import java.util.Map;

public class P_2342_MaxSumOfAPairWithEqualSumOfDigits {
    /*
    Hash map approach. <key, value> pair is <digitSum, actualNumber>.
    
    As we iterate through the input array, we check if the current integer's digit sum exists in the 
    map. If it does, we can add the two numbers together and store the maximum sum. Then, we update 
    that digit sum's value in the map with the larger actual number in case we run into subsequent
    integers with the same digit sum.
    
    e.g., [18,43,36,81]
    pairs: [18,36] and [18,81] and [36,81]
    output: 36 + 81 = 117
    When we are iterating on index 2, <9, 36> will replace <9, 18> since 36 > 18.
    
    Time complexity: O(n), iterating through all integers in the input array
    Space: O(n), if all integers have different digit sums, the map size will be equal to the array length
    */
    public int maximumSum(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = -1;

        for (int num : nums) {
            int digitSum = 0;
            String str = Integer.toString(num);
            for (int i = 0; i < str.length(); i++) {
                char s = str.charAt(i);
                digitSum += s - '0';
            }

            if (map.containsKey(digitSum)) {
                int actualNum = map.get(digitSum);
                max = Math.max(max, actualNum + num);
                map.put(digitSum, Math.max(actualNum, num));
            } else {
                map.put(digitSum, num);
            }
        }
        return max;
    }

    /*
    Alternate solution which calculates the digit sum using modulo and division; this math
    approach is slightly faster than the string approach.
    
    Time and space complexities are still the same.
    */
    public int v2(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = -1;

        for (int num : nums) {
            int digitSum = 0;
            int temp = num;
            while (temp > 0) {
                digitSum += temp % 10;
                temp /= 10;
            }

            if (map.containsKey(digitSum)) {
                int actualNum = map.get(digitSum);
                max = Math.max(max, actualNum + num);
                map.put(digitSum, Math.max(actualNum, num));
            } else {
                map.put(digitSum, num);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P_2342_MaxSumOfAPairWithEqualSumOfDigits solver = new P_2342_MaxSumOfAPairWithEqualSumOfDigits();

        Object[][] tests = new Object[][] {
                { new int[] { 18, 43, 36, 13, 7 }, 54 },
                { new int[] { 10, 12, 19, 14 }, -1 },
                { new int[] { 1, 2, 3, 4 }, -1 },
                { new int[] { 51, 71, 17, 24, 42 }, 93 },
                { new int[] { 1, 1, 1, 1 }, 2 },
                { new int[] { 99, 99 }, 198 },
                { new int[] { 9, 18, 27, 36, 45 }, 81 },
                { new int[] { 123, 321, 213 }, 534 },
                { new int[] { 1 }, -1 },
                { new int[] { 11, 22, 33, 44 }, -1 }
        };

        System.out.println("Running tests for P_2342_MaxSumOfAPairWithEqualSumOfDigits.maximumSum\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.maximumSum(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_2342_MaxSumOfAPairWithEqualSumOfDigits.v2\n");
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
        System.out.printf("maximumSum: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
