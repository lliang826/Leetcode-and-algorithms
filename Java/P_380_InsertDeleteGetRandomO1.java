import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class P_380_InsertDeleteGetRandomO1 {
    class RandomizedSet {
        private Map<Integer, Integer> map;
        List<Integer> list;
        Random random;

        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
            random = new Random();
        }

        public boolean insert(int val) {
            boolean contains = map.containsKey(val);
            if (contains) {
                return false;
            }

            list.add(val);
            map.put(val, list.size() - 1);

            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }

            int index = map.get(val);
            int lastElement = list.get(list.size() - 1);

            list.set(index, lastElement);
            map.put(lastElement, index);

            list.remove(list.size() - 1);
            map.remove(val);

            return true;
        }

        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }
    }

    public static void main(String[] args) {
        P_380_InsertDeleteGetRandomO1 outer = new P_380_InsertDeleteGetRandomO1();

        // Each test is a sequence of operations. insert/remove results are checked against a
        // reference HashSet; getRandom is checked by verifying the value is currently in the set.
        // Per the constraints, getRandom is only called when the set is non-empty.
        // Test i uses descriptions[i], allOps[i], and allVals[i]; vals are ignored for "getRandom".
        String[] descriptions = new String[] {
                "LeetCode example",
                "duplicate insert returns false",
                "remove missing value returns false",
                "remove the last element",
                "remove the only element, then reinsert it",
                "remove from the middle",
                "remove the first element, then remove the moved element",
                "negative values and zero",
                "insert/remove the same value repeatedly",
                "remove all elements in insertion order"
        };

        String[][] allOps = new String[][] {
                { "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom" },
                { "insert", "insert", "getRandom" },
                { "remove", "insert", "remove", "remove" },
                { "insert", "insert", "remove", "insert", "remove", "getRandom" },
                { "insert", "remove", "insert", "getRandom", "remove", "remove" },
                { "insert", "insert", "insert", "remove", "remove", "getRandom" },
                { "insert", "insert", "insert", "remove", "remove", "getRandom", "getRandom" },
                { "insert", "insert", "insert", "remove", "insert", "getRandom" },
                { "insert", "remove", "insert", "remove", "insert", "insert", "getRandom" },
                { "insert", "insert", "insert", "insert", "remove", "remove", "remove", "remove", "remove" }
        };

        int[][] allVals = new int[][] {
                { 1, 2, 2, 0, 1, 2, 0 },
                { 5, 5, 0 },
                { 3, 3, 4, 3 },
                { 5, 7, 7, 7, 7, 0 },
                { 9, 9, 9, 0, 9, 9 },
                { 1, 2, 3, 1, 3, 0 },
                { 10, 20, 30, 10, 30, 0, 0 },
                { -1, 0, Integer.MIN_VALUE, 0, Integer.MAX_VALUE, 0 },
                { 4, 4, 4, 4, 4, 4, 0 },
                { 1, 2, 3, 4, 1, 2, 3, 4, 1 }
        };

        System.out.println("Running tests for P_380_InsertDeleteGetRandomO1.RandomizedSet\n");
        int pass = 0;
        for (int i = 0; i < descriptions.length; i++) {
            String description = descriptions[i];
            String[] ops = allOps[i];
            int[] vals = allVals[i];

            RandomizedSet rs = outer.new RandomizedSet();
            Set<Integer> reference = new HashSet<>();
            List<String> expected = new ArrayList<>();
            List<String> actual = new ArrayList<>();
            boolean ok = true;

            for (int j = 0; j < ops.length; j++) {
                switch (ops[j]) {
                    case "insert": {
                        boolean exp = reference.add(vals[j]);
                        boolean act = rs.insert(vals[j]);
                        expected.add(String.valueOf(exp));
                        actual.add(String.valueOf(act));
                        ok &= exp == act;
                        break;
                    }
                    case "remove": {
                        boolean exp = reference.remove(vals[j]);
                        boolean act = rs.remove(vals[j]);
                        expected.add(String.valueOf(exp));
                        actual.add(String.valueOf(act));
                        ok &= exp == act;
                        break;
                    }
                    case "getRandom": {
                        int act = rs.getRandom();
                        expected.add("any of " + reference);
                        actual.add(String.valueOf(act));
                        ok &= reference.contains(act);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unknown op: " + ops[j]);
                }
            }

            if (ok)
                pass++;
            System.out.printf("Test %d (%s): ops=%s, vals=%s\n  expected=%s\n  actual=  %s => %s\n",
                    i + 1, description, java.util.Arrays.toString(ops), java.util.Arrays.toString(vals),
                    expected, actual, (ok ? "PASS" : "FAIL"));
        }

        // getRandom must be uniform: each of 4 elements should appear ~25% of the time.
        // Tolerance of 20%-30% over 40,000 draws makes a false failure astronomically unlikely.
        System.out.println("\nRunning uniformity test for getRandom\n");
        RandomizedSet rs = outer.new RandomizedSet();
        int[] values = { 1, 2, 3, 4 };
        for (int v : values) {
            rs.insert(v);
        }
        rs.remove(2);
        rs.insert(5);
        Map<Integer, Integer> counts = new HashMap<>();
        int draws = 40000;
        for (int d = 0; d < draws; d++) {
            counts.merge(rs.getRandom(), 1, Integer::sum);
        }
        boolean uniformOk = counts.keySet().equals(Set.of(1, 3, 4, 5));
        for (int count : counts.values()) {
            double ratio = (double) count / draws;
            uniformOk &= ratio >= 0.20 && ratio <= 0.30;
        }
        System.out.printf("Set {1, 3, 4, 5}, %d draws => counts=%s => %s\n", draws, counts,
                (uniformOk ? "PASS" : "FAIL"));

        int total = descriptions.length + 1;
        int passed = pass + (uniformOk ? 1 : 0);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("RandomizedSet: %d/%d tests passed\n", passed, total);
    }
}
