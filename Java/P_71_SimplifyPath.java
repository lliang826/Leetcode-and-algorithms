import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class P_71_SimplifyPath {
    /*
    Stack approach. To parse the input string, we use the split() method, which removes all instances of
    the "/" delimiter - this takes care of the multiple consecutive slashes scenarios. We are left with
    names and empty spaces. In this solution, I add "/" to the start of each name, which was unnecessary
    and created extra complexity.
    Empty spaces and '.' should not be included, so we don't do anything. If the name is "..", we need to
    pop the previous name and the "/" before it. Otherwise, we push the name to the stack.
    
    Time: O(n), where n is the number of characters in the input string
    Space: O(n + n + n) => O(n), stack, split() and StringBuilder
    
    Note: in Java, the split() method can produce empty tokens if there are multiple consecutive 
    delimiters. Trailing empty tokens are always removed in Java.
    e.g.
    input = /a//b///
    output = ["", "a", "", "b", "", "", ""] => ["", "a", "", "b"]
    */
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

    /*
    Cleaner solution that doesn't add "/" to the start of each name until the final construction of the output
    string. Without that bit of complexity, the ".." if case is a lot simpler.
    In addition, I swapped the Stack for an ArrayDeque since the latter is more modern and is probably better
    for interviews.
    
    Deque (pronounced "deck") stands for double ended queue. Since it is double ended, items can be added/removed
    from both the start and the end. ArrayDeque has push() and pop() methods, but since these methods are for
    adding/removing items at the start, it's better to use addLast() and removeLast(). Otherwise, items will be
    in reverse order, which is unnecessary complexity.
    
    **Why we use ArrayDeque over Stack**:
    Stack is a legacy class that extends Vector, which exposes methods like get(index) that don't fit stack 
    behavior. ArrayDeque is modern and provides proper stack operations like addLast() and removeLast(), making
    it faster and safer.
    
    Same time and space complexities.
    */
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
        P_71_SimplifyPath solver = new P_71_SimplifyPath();

        Object[][] tests = new Object[][] {
                { "/home/", "/home" },
                { "/home//foo/", "/home/foo" },
                { "/home/user/Documents/../Pictures", "/home/user/Pictures" },
                { "/../", "/" },
                { "/.../a/../b/c/../d/./", "/.../b/d" },
                { "/", "/" },
                { "/a/./b/../../c/", "/c" },
                { "/a/../../b/../c//.//", "/c" },
                { "/a//b////c/d//././/..", "/a/b/c" },
                { "/..hidden", "/..hidden" },
                { "/.hidden", "/.hidden" },
                { "/home/./foo/../bar/", "/home/bar" },
                { "/a/b/c/../../../d", "/d" },
                { "/a/b/c/../../../../", "/" },
                { "/....", "/...." },
                { "/./././.", "/" },
                { "/abc/def/ghi", "/abc/def/ghi" },
                { "//", "/" },
                { "///", "/" },
                { "/home/user/.../docs", "/home/user/.../docs" }
        };

        System.out.println("Running tests for P_71_SimplifyPath.simplifyPath\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.simplifyPath(input);

            boolean ok = expected.equals(actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: input=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_71_SimplifyPath.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            String expected = (String) tests[i][1];
            String actual = solver.v2(input);

            boolean ok = expected.equals(actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: input=\"%s\" => expected=\"%s\", actual=\"%s\" => %s\n",
                    i + 1, input, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("simplifyPath: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
