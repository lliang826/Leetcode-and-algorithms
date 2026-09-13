import java.util.Arrays;

public class P_881_BoatsToSavePeople {
    /*
    Greedy approach with 2 pointers.

    For this problem, given that each boat can only fit at most two people, and given
    that we want to find the minimum number of boats, we want to use a greedy approach:
    pair the heaviest people with the lightest people so we can minimize the number of
    boats.
    First, we sort the input array - ascending or descending doesn't matter, but for this
    implementation, we do ascending. The heaviest people will be at the end of the array,
    where the right pointer is, and the lightest will be at the beginning, where the left
    pointer is.
    If we can pair both the heavy and the light together, we move the left pointer up by
    one and the right pointer down by one. If we can't pair them, there is no lighter
    person (the array is sorted), so that heavy person gets the boat by themselves and
    we only decrement the right pointer by one. Either way, the counter is increased by
    one.

    Time: O(n log n)
    - O(n log n) for sorting the input array
    - O(n) for the two pointers

    Space: O(1)
    - Sorting the input array in place, no additional space needed
    */
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);

        int right = people.length - 1;
        int left = 0;
        int count = 0;

        while (left <= right) {
            if (people[left] + people[right] <= limit) {
                left++;
                right--;
            } else {
                right--;
            }

            count++;
        }

        return count;
    }

    /*
    Refactored the code - the right pointer always decrements, so we can take it out
    of the if statement.

    Same time and space complexities.
    */
    public int v2(int[] people, int limit) {
        Arrays.sort(people);

        int right = people.length - 1;
        int left = 0;
        int count = 0;

        while (left <= right) {
            if (people[left] + people[right] <= limit) {
                left++;
            }
            
            right--;
            count++;
        }

        return count;
    }

    public static void main(String[] args) {
        P_881_BoatsToSavePeople solver = new P_881_BoatsToSavePeople();

        // Test cases: {people, limit, expected number of boats}
        Object[][] tests = new Object[][] {
                { new int[] { 1, 2 }, 3, 1 },
                { new int[] { 3, 2, 2, 1 }, 3, 3 },
                { new int[] { 3, 5, 3, 4 }, 5, 4 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 5, 5, 5, 5 }, 5, 4 },
                { new int[] { 1, 1, 1, 1 }, 2, 2 },
                { new int[] { 3, 1, 7, 5 }, 8, 2 },
                { new int[] { 1, 2, 3, 4, 5 }, 6, 3 },
                { new int[] { 30000, 30000 }, 30000, 2 },
                { new int[] { 2, 2, 2, 2, 2, 2, 2 }, 4, 4 },
                { new int[] { 5, 4, 3, 2, 1 }, 100, 3 },
                { new int[] { 4, 2, 3, 3 }, 5, 3 },
        };

        System.out.println("Running tests for P_881_BoatsToSavePeople.numRescueBoats\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] people = (int[]) tests[i][0];
            int limit = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.numRescueBoats(people.clone(), limit);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: people=%s, limit=%d => expected=%d, actual=%d => %s\n",
                    i + 1, Arrays.toString(people), limit, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_881_BoatsToSavePeople.v2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[] people = (int[]) tests[i][0];
            int limit = (int) tests[i][1];
            int expected = (int) tests[i][2];
            int actual = solver.v2(people.clone(), limit);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: people=%s, limit=%d => expected=%d, actual=%d => %s\n",
                    i + 1, Arrays.toString(people), limit, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("numRescueBoats: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("v2: %d/%d tests passed\n", pass2, tests.length);
    }
}
