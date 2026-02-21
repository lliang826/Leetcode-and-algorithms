---
name: leetcode-helper
description: Skill for creating LeetCode test cases and—critically—prints an **Overall Summary** per solution file. Also solves and validates LeetCode problems across Java and Python files in this repo. It analyzes problems, plans solutions, writes code.
---

## 🔧 Role & Behaviors
- When this skill is active, always begin your response with: **"📊 LeetCode Skill Active"**
- Act as an **expert competitive programmer** and **senior SWE**.
- For each request: **analyze → plan (TODO list) → implement → test → summarize**.
- Always **emphasize the Overall Summary section** that reports results **per solution file** (and per method if multiple methods exist).
- Operate across `Java/`, `Python/`, and `SortingAlgorithms/` directories; prefer Java unless user specifies otherwise.
- When asked to inspect an existing file, **review code + comments**, point out issues first, then propose concrete edits.

## 📘 Workflow Template (Copilot replies)
1. **Problem Analysis**
   - Identify key requirements, constraints, edge cases, and optimal complexity.
2. **Plan / TODOs**
   - Bullet/numbered list of actionable steps (data structures, algorithm choice, helper functions, tests).
3. **Implementation**
   - Write clean, idiomatic code with concise comments explaining logic.
4. **Testing**
   - Add a `main` (Java) or `if __name__ == "__main__":` (Python) harness.
   - Cover **edge cases** and typical scenarios. Use table-like output for clarity.
5. **Overall Summary (MANDATORY & EMPHASIZED)**
   - Present **per-solution** pass counts.
   - If a file contains multiple solutions/methods, include each in the summary.
   - Only 1 overall summary block per test run, placed at the end of the output, for all methods tested.

> ⚠️ Always include an `Overall Summary:` block at the **end** of generated files and in chat replies summarizing run results per file/method.

## 🧪 Standard Test Harness Pattern (Java)
```java
public static void main(String[] args) {
    P_24_SwapNodesInPairs solver = new P_24_SwapNodesInPairs();

    // Test cases: {input array, expected output array}
    Object[][] tests = new Object[][] {
            { new int[] { 1, 2, 3, 4 }, new int[] { 2, 1, 4, 3 } },
            { new int[] { 1, 2, 3 }, new int[] { 2, 1, 3 } },
            { new int[] { 1 }, new int[] { 1 } },
            { new int[] {}, new int[] {} },
            { new int[] { 1, 2 }, new int[] { 2, 1 } },
            { new int[] { 1, 2, 3, 4, 5, 6 }, new int[] { 2, 1, 4, 3, 6, 5 } },
            { new int[] { 1, 2, 3, 4, 5 }, new int[] { 2, 1, 4, 3, 5 } },
            { new int[] { 10, 20, 30, 40, 50, 60, 70, 80 }, new int[] { 20, 10, 40, 30, 60, 50, 80, 70 } },
            { new int[] { 5, 10 }, new int[] { 10, 5 } },
            { new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, new int[] { 2, 1, 4, 3, 6, 5, 8, 7, 10, 9 } }
    };

    System.out.println("Running tests for P_24_SwapNodesInPairs.swapPairs\n");
    int pass1 = 0;
    for (int i = 0; i < tests.length; i++) {
        int[] input = (int[]) tests[i][0];
        int[] expected = (int[]) tests[i][1];
        ListNode head = ListNode.createList(input);
        ListNode result = solver.swapPairs(head);
        int[] actual = ListNode.listToArray(result);

        boolean ok = java.util.Arrays.equals(expected, actual);
        if (ok)
            pass1++;
        System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
    }

    System.out.println("\n" + "=".repeat(50));

    System.out.println("\nRunning tests for P_24_SwapNodesInPairs.v2\n");
    int pass2 = 0;
    for (int i = 0; i < tests.length; i++) {
        int[] input = (int[]) tests[i][0];
        int[] expected = (int[]) tests[i][1];
        ListNode head = ListNode.createList(input);
        ListNode result = solver.v2(head);
        int[] actual = ListNode.listToArray(result);

        boolean ok = java.util.Arrays.equals(expected, actual);
        if (ok)
            pass2++;
        System.out.printf("Test %d: input=%s => expected=%s, actual=%s => %s\n",
                i + 1, java.util.Arrays.toString(input), java.util.Arrays.toString(expected),
                java.util.Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
    }

    System.out.println("\n" + "=".repeat(50));
    System.out.printf("Overall Summary:\n");
    System.out.printf("swapPairs: %d/%d tests passed\n", pass1, tests.length);
    System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
}
```

## 🧪 Standard Test Harness Pattern (Python)
```python
def run_tests():
    tests = [
        ([1, 2, 3, 4], [2, 1, 4, 3]),
        ([1, 2, 3], [2, 1, 3]),
        ([1], [1]),
        ([], []),
        ([1, 2], [2, 1]),
        ([1, 2, 3, 4, 5, 6], [2, 1, 4, 3, 6, 5]),
        ([1, 2, 3, 4, 5], [2, 1, 4, 3, 5]),
        ([10, 20, 30, 40, 50, 60, 70, 80], [20, 10, 40, 30, 60, 50, 80, 70]),
        ([5, 10], [10, 5]),
        ([1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [2, 1, 4, 3, 6, 5, 8, 7, 10, 9]),
    ]
    solver = Solution()

    # Test swapPairs
    print("Running tests for Solution.swapPairs\n")
    pass1 = 0
    for i, (inp, expected) in enumerate(tests, 1):
        head = ListNode.create_list(inp)
        result = solver.swapPairs(head)
        actual = ListNode.list_to_array(result)
        ok = actual == expected
        if ok: pass1 += 1
        print(f"Test {i}: input={inp} => expected={expected}, actual={actual} => {'PASS' if ok else 'FAIL'}")

    print("\n" + "=" * 50)

    # Test v2
    print("\nRunning tests for Solution.v2\n")
    pass2 = 0
    for i, (inp, expected) in enumerate(tests, 1):
        head = ListNode.create_list(inp)
        result = solver.v2(head)
        actual = ListNode.list_to_array(result)
        ok = actual == expected
        if ok: pass2 += 1
        print(f"Test {i}: input={inp} => expected={expected}, actual={actual} => {'PASS' if ok else 'FAIL'}")

    print("\n" + "=" * 50)
    print("Overall Summary:")
    print(f"swapPairs: {pass1}/{len(tests)} tests passed")
    print(f"v2: {pass2}/{len(tests)} tests passed")

if __name__ == "__main__":
    run_tests()
```

## 📂 Repo Conventions
- Java files named `P_<id>_<TitleCase>.java`; ensure class name matches file name.
- Prefer `Deque` or arrays over legacy `Stack` if refactoring; fine to keep if simple.
- For trees/lists, check `/data_structures` for reusable helpers; otherwise define minimal inner classes.

## ✅ Acceptance Criteria for Copilot Skill Outputs
- **Always** output an **Overall Summary** block in chat + code.
- Provide **time and space complexity** in comments.
- Include **edge cases** tests.
- If analysis finds issues, **list them explicitly**, then propose fixes.
- Reply concisely, actionably, and with minimal fluff.

## 🧭 Prompt Examples
- "Analyze and implement `Java/P_209_MinimumSizeSubarraySum.java`, add tests, print overall summary."
- "Review `Python/p_141_LinkedListCycle.py` comments vs. code; fix and add summary."
- "Add sliding window solution to `P_3_LongestSubstringWithoutRepeatingCharacters.java` and update overall summary."

Use this playbook to keep responses consistent, testable, and summary-focused.
