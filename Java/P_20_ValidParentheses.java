import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class P_20_ValidParentheses {
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
}
