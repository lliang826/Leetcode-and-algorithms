import java.util.Arrays;

public class P_1710_MaximumUnitsOnATruck {
    /*
    Greedy approach.

    To maximize the number of units that can be put on the truck, we want to load boxes with
    the higher number of units first (each box takes exactly 1 spot, no matter how many units
    it holds). Therefore, we can sort the 2D array based on the inner array's value at index
    1 in descending order.

    Then, all we have to do is iterate through the 2D array while we still have room to load
    more boxes. If the number of boxes is less than or equal to the available room, we can
    take all of them. But if the number of boxes is greater than the available room, we can 
    only take boxes equal to the available room.

    Time: O(n log n)
    - O(n log n) to sort the 2D input array
    - O(n) to iterate through each boxType

    Space: O(1)
    - O(1) for sorting in place
    */
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);

        int sum = 0;
        for (int i = 0; i < boxTypes.length; i++) {
            int boxes = boxTypes[i][0];
            int units = boxTypes[i][1];

            if (boxes <= truckSize) {
                sum += boxes * units;
                truckSize -= boxes;
            } else {
                sum += truckSize * units;
                break;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        P_1710_MaximumUnitsOnATruck solver = new P_1710_MaximumUnitsOnATruck();

        // Test cases: {boxTypes, truckSize, expected units}
        Object[][] tests = new Object[][] {
                // LeetCode example 1: partial load on the last type taken
                { new int[][] { { 1, 3 }, { 2, 2 }, { 3, 1 } }, 4, 8 },
                // LeetCode example 2
                { new int[][] { { 5, 10 }, { 2, 5 }, { 4, 7 }, { 3, 9 } }, 10, 91 },
                // Truck holds everything, so the sort order cannot change the answer
                { new int[][] { { 5, 10 }, { 2, 5 }, { 4, 7 }, { 3, 9 } }, 100, 115 },
                // Capacity exactly equals the total box count (14): no partial load, no leftover
                { new int[][] { { 5, 10 }, { 2, 5 }, { 4, 7 }, { 3, 9 } }, 14, 115 },
                // Catches sorting by numberOfBoxes instead of unitsPerBox
                { new int[][] { { 100, 1 }, { 1, 100 } }, 1, 100 },
                // Catches the (boxes - truckSize) partial-fill bug: correct is 2*7=14, not 8*7=56
                { new int[][] { { 10, 7 } }, 2, 14 },
                // Single type, single box
                { new int[][] { { 1, 1 } }, 1, 1 },
                // Zero capacity means zero units even though boxes are available
                { new int[][] { { 5, 10 }, { 3, 9 } }, 0, 0 },
                // All types share the same unitsPerBox, so any stable order must still work
                { new int[][] { { 2, 4 }, { 3, 4 }, { 1, 4 } }, 4, 16 },
                // Already sorted descending
                { new int[][] { { 3, 9 }, { 2, 6 }, { 4, 3 } }, 6, 42 },
                // Already sorted ascending, forcing a full reversal
                { new int[][] { { 4, 3 }, { 2, 6 }, { 3, 9 } }, 6, 42 },
                // Partial load happens on the very first type
                { new int[][] { { 9, 8 }, { 2, 3 } }, 5, 40 },
                // Upper-constraint values, checking the multiplication does not misbehave
                { new int[][] { { 1000, 1000 }, { 1000, 999 } }, 1500, 1499500 }
        };

        System.out.println("Running tests for P_1710_MaximumUnitsOnATruck.maximumUnits\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] boxTypes = (int[][]) tests[i][0];
            int truckSize = (int) tests[i][1];
            int expected = (int) tests[i][2];

            String inputStr = Arrays.deepToString(boxTypes);
            int actual = solver.maximumUnits(boxTypes, truckSize);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: boxTypes=%s, truckSize=%d => expected=%d, actual=%d => %s\n",
                    i + 1, inputStr, truckSize, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("maximumUnits: %d/%d tests passed\n", pass, tests.length);
    }
}
