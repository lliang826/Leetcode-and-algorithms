# Project Guidelines

## Code Style
- Primary language is Java in `Java/`; Python solutions are in `Python/`.
- Java file/class naming convention: `P_<id>_<TitleCase>.java` with matching public class name (example: `Java/P_1929_ConcatenationOfArray.java`).
- Python file naming convention: `<id>-<Title_with_underscores>.py` (example: `Python/206-Reverse_linked_list.py`).
- Keep solution methods focused and include short complexity notes in block comments (see `Java/P_206_ReverseLinkedList.java`).
- For linked-list/tree problems in Java, reuse helpers from `Java/data_structures/` instead of redefining nodes (`ListNode`, `TreeNode`). These helpers also provide static factory/conversion methods (`createList`, `buildTree`, `listToArray`, `treeToString`, `findNodeByValue`) — prefer using them in test harnesses. If a needed helper doesn't exist there, create it in `Java/data_structures/` rather than defining it inline.
- Python files redefine helper classes (e.g. `ListNode`) inline — there is no shared `data_structures` module for Python.
- Typical Java test harness pattern is in `main` with multiple test cases and PASS/FAIL output.
- Python files are usually standalone scripts with a `Solution` class and explanatory comments (see `Python/1-Two_sum.py`).

## Architecture
- Repo is organized by problem solutions, not a single application runtime:
  - `Java/` = Java LeetCode solutions
  - `Python/` = Python LeetCode solutions
  - `SortingAlgorithms/` = standalone algorithm implementations (currently Python-only, function-based style)
- Most files are independent per problem; shared Java structures live in `Java/data_structures/`.

## Build and Test
- There is no Maven/Gradle/pytest project setup in this workspace; run files directly.
- All commands assume execution from the **repo root**, where `Java/` is the classpath root matching the `data_structures` package structure.
- Java (single file without helper imports):
  - `javac Java/P_1929_ConcatenationOfArray.java`
  - `java -cp Java P_1929_ConcatenationOfArray`
- Java (files that use `data_structures`):
  - `javac Java/data_structures/*.java Java/P_206_ReverseLinkedList.java`
  - `java -cp Java P_206_ReverseLinkedList`
- Python:
  - `python Python/1-Two_sum.py`
- Prefer validating only the file you changed unless asked to do broader verification.

## Project Conventions
- Preserve the per-file manual test harness style in Java `main` methods.
- Keep output readable and include an `Overall Summary:` block at the end of test output when creating/updating harnesses in both Java and Python (see `Java/P_1929_ConcatenationOfArray.java`). Only one summary block per test run, covering all methods tested.
- Follow the repo skill guidance when solving/editing LeetCode tasks: `.github/skills/leetcode-helper/SKILL.md`.
- Keep edits surgical: do not rename files/classes or refactor unrelated solutions.

## Integration Points
- Internal integration is mostly Java imports from `Java/data_structures/`.
- AI-agent workflow references in `.github/skills/leetcode-helper/` and `.github/agents/` provide repo-specific behavior and expectations.

## Security
- This repository is local algorithm code with no standard auth/secrets flow.
- Do not add network calls, credential handling, or external service dependencies unless explicitly requested.
- Treat all script execution as local code execution: run only target files needed for the task.