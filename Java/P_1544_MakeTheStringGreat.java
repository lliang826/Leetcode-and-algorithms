import java.util.Stack;

public class P_1544_MakeTheStringGreat {
    /*
    
    */
    public String makeGood(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isCurrCharUpperCase = Character.isUpperCase(c);
            boolean isSameAsLastChar = sb.length() > 0
                    && Character.toLowerCase(sb.charAt(sb.length() - 1)) == Character.toLowerCase(c);

            if (sb.length() > 0) {
                boolean isLastCharUpperCase = Character.isUpperCase(sb.charAt(sb.length() - 1));
                if (isSameAsLastChar && isLastCharUpperCase != isCurrCharUpperCase) {
                    sb.deleteCharAt(sb.length() - 1);
                    continue;
                }
            }
            sb.append(c);
        }

        return sb.toString();
    }

    public String v2(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (sb.length() > 0 && Math.abs(sb.charAt(sb.length() - 1) - c) == 32) {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
    
    public String v3(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && Math.abs(stack.peek() - c) == 32) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }
}
