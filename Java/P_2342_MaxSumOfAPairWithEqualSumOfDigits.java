import java.util.HashMap;
import java.util.Map;

public class P_2342_MaxSumOfAPairWithEqualSumOfDigits {
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
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] input = (int[]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.maximumSum(input.clone());

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                    i + 1, java.util.Arrays.toString(input), expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
