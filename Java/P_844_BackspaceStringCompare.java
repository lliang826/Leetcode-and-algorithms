public class P_844_BackspaceStringCompare {
    /*
    Stack approach. Since this problem is about strings and characters, we can use StringBuilder as a
    stack data structure (append() to push and deleteCharAt() to pop).
    We iterate through both input strings and build the resulting strings after applying backspaces.
    Finally, we compare the two resulting strings using equals() method (we cannot use '==' operator to
    compare strings in Java as it compares references, not values).

    Time: O(n + m), where n and m are the lengths of the two input strings
    Space: O(n + m), in the worst case scenario, there are no backspaces, which means that all characters are
    added to the StringBuilders
    */
    public boolean backspaceCompare(String s, String t) {
        StringBuilder sb =  new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '#') {
                sb.append(c);
            } else if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (c != '#') {
                sb1.append(c);
            } else if (sb1.length() > 0) {
                sb1.deleteCharAt(sb1.length() - 1);
            }
        }

        return sb.toString().equals(sb1.toString());
    }

    /*
    V2: Cleaner version of the above solution by extracting the string building logic into a helper method.

    Same time and space complexities as above.
    */
    public boolean v2(String s, String t) {
        return buildString(s).equals(buildString(t));
    }

    private String buildString(String s) {
        StringBuilder sb =  new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '#') {
                sb.append(c);
            } else if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        return sb.toString();
    }

    /*
    TODO: Two pointer approach.

    Time: O(n + m), where n and m are the lengths of the two input strings
    Space: O(1), we only use a constant amount of extra space
    */
    public boolean v3(String s, String t) {
        int sIndex = s.length() - 1;
        int tIndex = t.length() - 1;
        int sSkip = 0;
        int tSkip = 0;
        Character sChar = s.charAt(sIndex);
        Character tChar = t.charAt(tIndex);

        while (sIndex >= 0 && tIndex >= 0) {
            if (sChar == '#') {
                sSkip++;
                sIndex--;
                continue;
            }
            if (tChar == '#') {
                tSkip++;
                tIndex--;
                continue;
            }
            if (sSkip > 0) {
                sSkip--;
                sIndex--;
                continue;
            }
            if (tSkip > 0) {
                tSkip--;
                tIndex--;
                continue;
            }
            if (sSkip == 0 && tSkip == 0 && sChar != '#' && tChar != '#' && sChar != tChar) {
                return false;
            }
            sIndex--;
            tIndex--;
        }

        while (sSkip > 0 || sChar == '#') {
            if (sChar == '#') {
                sSkip++;
            } else {
                sSkip--;
                
            }
            sIndex--;
        }
        while (tSkip > 0 || tChar == '#') {
            if (tChar == '#') {
                tSkip++;
            } else {
                tSkip--;
            }
            tIndex--;
        }

        if (sIndex != tIndex) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        P_844_BackspaceStringCompare solver = new P_844_BackspaceStringCompare();

        Object[][] tests = new Object[][] {
                { "ab#c", "ad#c", true },
                { "ab##", "c#d#", true },
                { "a#c", "b", false },
                { "a##c", "#a#c", true },
                { "bxj##tw", "bxo#j##tw", true },
                { "abc", "abc", true },
                { "abc", "def", false },
                { "a#", "", true },
                { "###", "", true },
                { "ab#", "a", true },
                { "abc#d", "abd", true },
                { "xy#z", "xz", true },
                { "xy#z", "xyz#", false },
                { "bbbextm", "bbb#extm", false },
                { "nzp#o#g", "b#nzp#o#g", true }
        };

        System.out.println("Running tests for P_844_BackspaceStringCompare.backspaceCompare\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = (String) tests[i][0];
            String t = (String) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.backspaceCompare(s, t);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: s=\"%s\", t=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, s, t, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_844_BackspaceStringCompare.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = (String) tests[i][0];
            String t = (String) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.v2(s, t);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: s=\"%s\", t=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, s, t, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_844_BackspaceStringCompare.v3\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            String s = (String) tests[i][0];
            String t = (String) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.v3(s, t);

            boolean ok = expected == actual;
            if (ok)
                pass3++;
            System.out.printf("Test %d: s=\"%s\", t=\"%s\" => expected=%b, actual=%b => %s\n",
                    i + 1, s, t, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("backspaceCompare: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("v3: %d/%d tests passed\n", pass3, tests.length);
    }
}