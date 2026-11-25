import java.util.Arrays;

public class P_1472_DesignBrowserHistory {
    /*
    */
    class ListNode {
        String url;
        ListNode next;
        ListNode prev;

        public ListNode(String url, ListNode next, ListNode prev) {
            this.url = url;
            this.next = next;
            this.prev = prev;
        }
    }

    class BrowserHistory {
        ListNode curr;

        public BrowserHistory(String homepage) {
            ListNode node = new ListNode(homepage, null, null);
            curr = node;
        }

        public void visit(String url) {
            ListNode node = new ListNode(url, null, curr);
            curr.next = node;
            curr = node;
        }

        public String back(int steps) {
            while (steps > 0) {
                if (curr.prev == null) {
                    break;
                }
                curr = curr.prev;
                steps--;
            }

            return curr.url;
        }

        public String forward(int steps) {
            while (steps > 0) {
                if (curr.next == null) {
                    break;
                }
                curr = curr.next;
                steps--;
            }

            return curr.url;
        }
    }

    /**
     * Your BrowserHistory object will be instantiated and called as such:
     * BrowserHistory obj = new BrowserHistory(homepage);
     * obj.visit(url);
     * String param_2 = obj.back(steps);
     * String param_3 = obj.forward(steps);
     */

    public static void main(String[] args) {
        P_1472_DesignBrowserHistory outer = new P_1472_DesignBrowserHistory();

        // Test cases: {homepage, operations, arguments, expected outputs (null for
        // visit), description}
        Object[][] tests = new Object[][] {
                { "leetcode.com",
                        new String[] { "visit", "visit", "visit", "back", "back", "forward", "visit", "forward",
                                "back", "back" },
                        new String[] { "google.com", "facebook.com", "youtube.com", "1", "1", "1", "linkedin.com",
                                "2", "2", "7" },
                        new String[] { null, null, null, "facebook.com", "google.com", "facebook.com", null,
                                "linkedin.com", "google.com", "leetcode.com" },
                        "LC Example 1" },
                { "home.com",
                        new String[] { "back", "forward" },
                        new String[] { "1", "1" },
                        new String[] { "home.com", "home.com" },
                        "Only homepage: back and forward both stay on homepage" },
                { "home.com",
                        new String[] { "visit", "visit", "back", "forward" },
                        new String[] { "a.com", "b.com", "100", "100" },
                        new String[] { null, null, "home.com", "b.com" },
                        "Steps larger than history: clamp to homepage / newest page" },
                { "home.com",
                        new String[] { "visit", "visit", "back", "forward" },
                        new String[] { "a.com", "b.com", "2", "2" },
                        new String[] { null, null, "home.com", "b.com" },
                        "Steps exactly reach both ends of history" },
                { "home.com",
                        new String[] { "visit", "visit", "visit", "back", "visit", "forward", "back", "forward" },
                        new String[] { "a.com", "b.com", "c.com", "2", "d.com", "1", "1", "5" },
                        new String[] { null, null, null, "a.com", null, "d.com", "a.com", "d.com" },
                        "Visit clears forward history (b.com and c.com are gone)" },
                { "a.com",
                        new String[] { "visit", "visit", "back", "forward", "forward" },
                        new String[] { "a.com", "b.com", "2", "1", "1" },
                        new String[] { null, null, "a.com", "a.com", "b.com" },
                        "Duplicate URLs are separate history entries" },
                { "home.com",
                        new String[] { "visit", "visit", "visit", "visit", "back", "back", "forward", "forward",
                                "back" },
                        new String[] { "a.com", "b.com", "c.com", "d.com", "1", "2", "1", "1", "3" },
                        new String[] { null, null, null, null, "c.com", "a.com", "b.com", "c.com", "home.com" },
                        "Interleaved back/forward moves within history" },
                { "home.com",
                        new String[] { "visit", "back", "back", "forward", "forward" },
                        new String[] { "a.com", "1", "1", "1", "1" },
                        new String[] { null, "home.com", "home.com", "a.com", "a.com" },
                        "Repeated back at homepage / forward at newest page are no-ops" }
        };

        System.out.println("Running tests for P_1472_DesignBrowserHistory.BrowserHistory\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String homepage = (String) tests[i][0];
            String[] ops = (String[]) tests[i][1];
            String[] opArgs = (String[]) tests[i][2];
            String[] expected = (String[]) tests[i][3];
            String desc = (String) tests[i][4];

            // Fresh browser history per test case.
            P_1472_DesignBrowserHistory.BrowserHistory history = outer.new BrowserHistory(homepage);
            String[] actual = new String[ops.length];
            for (int j = 0; j < ops.length; j++) {
                switch (ops[j]) {
                    case "visit":
                        history.visit(opArgs[j]);
                        actual[j] = null;
                        break;
                    case "back":
                        actual[j] = history.back(Integer.parseInt(opArgs[j]));
                        break;
                    case "forward":
                        actual[j] = history.forward(Integer.parseInt(opArgs[j]));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operation: " + ops[j]);
                }
            }

            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass++;
            System.out.printf(
                    "Test %d (%s): homepage=%s%n  ops=%s%n  args=%s%n  expected=%s%n  actual=  %s%n  => %s%n%n",
                    i + 1, desc, homepage, Arrays.toString(ops), Arrays.toString(opArgs),
                    Arrays.toString(expected), Arrays.toString(actual), ok ? "PASS" : "FAIL");
        }

        System.out.println("=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("BrowserHistory: %d/%d tests passed%n", pass, tests.length);
    }
}
