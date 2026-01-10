import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class P_71_SimplifyPath {
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("") || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                int count = 2;
                while (count > 0 && !stack.isEmpty()) {
                    stack.pop();
                    count--;
                }
                continue;
            }
            stack.push("/");
            stack.push(part);
        }

        if (stack.isEmpty()) {
            stack.push("/");
        }

        StringBuilder sb = new StringBuilder();
        for (String s : stack) {
            sb.append(s);
        }
        return sb.toString();
    }

    public String v2(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("") || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.removeLast();
                }
                continue;
            }
            stack.addLast(part);
        }

        StringBuilder sb = new StringBuilder();
        for (String s : stack) {
            sb.append("/");
            sb.append(s);
        }
        return sb.length() > 0 ? sb.toString() : "/";
    }

    public static void main(String[] args) {

    }
}
