import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class P_20_ValidParentheses {
    /*
    Stack + hashmap approach. To determine if the input string is valid, we use a stack data structure.
    If a set of parentheses (opening and closing brackets) match, we can pop the opening parentheses
    from the stack. If it's not a match, or if it's an opening bracket, we add it to the stack.
    After we iterate through all the characters in the string, we check the size of the stack. If there
    are any parentheses left, the string is not valid and we return false. Otherwise, true.
    We can use a hashmap to make lookup constant time.
    
    Time: O(n), we need to iterate through all character in the input string
    Space: O(n), in the worst case scenario, none of the parentheses match and we add all of them to the
    stack
    */
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.size() > 0 && stack.peek() == map.get(c)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.size() > 0 ? false : true;
    }

    public static void main(String[] args) {
        P_20_ValidParentheses solver = new P_20_ValidParentheses();

        Object[][] tests = new Object[][] {
                { "()", true },
                { "()[]{}", true },
                { "(]", false },
                { "([)]", false },
                { "{[]}", true },
                { "", true },
                { "(", false },
                { ")", false },
                { "((", false },
                { "))", false },
                { "(())", true },
                { "((()))", true },
                { "{[()]}", true },
                { "{[(])}", false },
                { "(({{[[]]}}))", true },
                { "({[", false },
                { "}}}]]])))", false },
                { "((({{{}}})))", true },
                { "[{()}]", true },
                { "[({})]", true }
        };

        System.out.println("Running tests for P_20_ValidParentheses.isValid\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            boolean actual = solver.isValid(input);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=\"%s\" => expected=%s, actual=%s => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("isValid: %d/%d tests passed\n", pass1, tests.length);
    }
}
