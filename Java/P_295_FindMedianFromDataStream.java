import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Supplier;

public class P_295_FindMedianFromDataStream {
    /*
    2 heap approach. If we used a regular array/arraylist approach, addNum() would have a O(1) constant time
    complexity (technically O(1) amortized because arraylist/dynamic array may resize), but findMedian() 
    would be O(n log n) because we would have to sort the array before finding and returning the median.

    By using a 2 heap approach, addNum() would have a O(log n) time complexity and findMedian would be O(1)
    constant time, which is a big improvement. To achieve this, we divide the numbers into 2 sections; the
    left section is a maxHeap and the right section is a minHeap. Pushing/popping elements to and from the
    heaps are O(log n) operations, and we can easily find the median in O(1) by peeking at the 2 heaps if
    both heaps have the same size, or by peeking at whichever heap is bigger.

    The tricky part of the 2 heap approach is balancing them: both heaps must be within 1 size of each other
    and the biggest element in maxHeap must be smaller than or equals to the smallest element in minHeap.

    The second solution is the cleanest, but the first and third solutions may be more intuitive and more 
    realistic for a first approach in an interview.

    Time complexity for all 3 approaches: 
    O(log n) for addNum()
    O(1) for findMedian

    Space complexity for all 3 approaches:
    2 heaps, O(n/2) + O(n/2) = O(n)
    */
    class MedianFinder {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;

        public MedianFinder() {
            this.maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            this.minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            this.maxHeap.offer(num);
            if (this.minHeap.size() > 0 && this.maxHeap.peek() > this.minHeap.peek()) {
                int i = this.maxHeap.poll();
                this.minHeap.offer(i);
            }
            if (this.maxHeap.size() - this.minHeap.size() > 1) {
                int i = this.maxHeap.poll();
                this.minHeap.offer(i);
            }
            if (this.maxHeap.size() - this.minHeap.size() < -1) {
                int i = this.minHeap.poll();
                this.maxHeap.offer(i);
            }
        }

        public double findMedian() {
            if (this.maxHeap.size() == this.minHeap.size()) {
                int x = maxHeap.peek();
                int y = minHeap.peek();
                return (double) (x + y) / 2;
            } else if (this.maxHeap.size() > this.minHeap.size()) {
                return this.maxHeap.peek();
            } else {
                return this.minHeap.peek();
            }
        }
    }

    class MedianFinder2 {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;

        public MedianFinder2() {
            maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
            if (minHeap.size() - maxHeap.size() > 1) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return (double) (maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                return minHeap.peek();
            }
        }
    }

    class MedianFinder3 {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;

        public MedianFinder3() {
            maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            maxHeap.offer(num);
            if (minHeap.peek() != null && maxHeap.peek() > minHeap.peek() || maxHeap.size() - minHeap.size() > 1) {
                minHeap.offer(maxHeap.poll());
            }
            if (minHeap.peek() != null && minHeap.peek() < maxHeap.peek() || minHeap.size() - maxHeap.size() > 1) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if ((maxHeap.size() + minHeap.size()) % 2 == 0) {
                return (double) (maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
            }
        }
    }

    /* Lets the harness drive all three implementations through one loop. */
    private interface Finder {
        void add(int num);

        double median();
    }

    private static Finder wrap(MedianFinder m) {
        return new Finder() {
            @Override
            public void add(int num) {
                m.addNum(num);
            }

            @Override
            public double median() {
                return m.findMedian();
            }
        };
    }

    private static Finder wrap(MedianFinder2 m) {
        return new Finder() {
            @Override
            public void add(int num) {
                m.addNum(num);
            }

            @Override
            public double median() {
                return m.findMedian();
            }
        };
    }

    private static Finder wrap(MedianFinder3 m) {
        return new Finder() {
            @Override
            public void add(int num) {
                m.addNum(num);
            }

            @Override
            public double median() {
                return m.findMedian();
            }
        };
    }

    private static double expectedMedian(List<Integer> seen) {
        List<Integer> sorted = new ArrayList<>(seen);
        Collections.sort(sorted);
        int n = sorted.size();
        if (n % 2 == 0) {
            return (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
        }
        return sorted.get(n / 2);
    }

    public static void main(String[] args) {
        P_295_FindMedianFromDataStream outer = new P_295_FindMedianFromDataStream();

        // Each test feeds a stream one number at a time and checks the median after every insertion.
        int[][] streams = new int[][] {
                { 1, 2, 3 },
                { 5 },
                { 1, 2 },
                { 5, 5, 5, 5, 5, 5 },
                { 5, 4, 3, 2, 1 },
                { 1, 2, 3, 4, 5 },
                { -1, -2, -3, 4, 5, 0 },
                { 100000, -100000, 100000, -100000 },
                { 0, 100, 1, 99, 2, 98, 3, 97 },
                { 6, 10, 2, 6, 5, 0, 6, 3 },
                { -50 },
                { 2, 2, 1, 1, 3, 3 }
        };

        String[] why = new String[] {
                "LeetCode example: median after 1, then 1,2, then 1,2,3",
                "single element: odd size with one heap left empty",
                "two elements: even size must average, catches size-parity bugs",
                "all duplicates: max(maxHeap) == min(minHeap) is legal, not a violation",
                "strictly decreasing: every new number belongs below the current median",
                "strictly increasing: every new number belongs above the current median",
                "mixed signs crossing zero",
                "constraint boundaries +/-100000, alternating extremes",
                "converging from both ends: forces rebalancing on nearly every insert",
                "unsorted with duplicates, repeated odd/even transitions",
                "single negative value",
                "duplicate pairs: even-size averaging when both peeks are equal"
        };

        String[] names = new String[] { "MedianFinder", "MedianFinder2", "MedianFinder3" };
        List<Supplier<Finder>> factories = List.of(
                () -> wrap(outer.new MedianFinder()),
                () -> wrap(outer.new MedianFinder2()),
                () -> wrap(outer.new MedianFinder3()));

        int[] passCounts = new int[names.length];

        for (int impl = 0; impl < names.length; impl++) {
            System.out.printf("Running tests for P_295_FindMedianFromDataStream.%s%n%n", names[impl]);

            for (int t = 0; t < streams.length; t++) {
                int[] stream = streams[t];
                Finder finder = factories.get(impl).get(); // fresh instance per test case
                List<Integer> seen = new ArrayList<>();
                double[] expected = new double[stream.length];
                double[] actual = new double[stream.length];
                boolean ok = true;

                for (int i = 0; i < stream.length; i++) {
                    finder.add(stream[i]);
                    seen.add(stream[i]);
                    expected[i] = expectedMedian(seen);
                    actual[i] = finder.median();
                    if (expected[i] != actual[i]) {
                        ok = false;
                    }
                }

                if (ok) {
                    passCounts[impl]++;
                }

                System.out.printf("Test %d: stream=%s%n", t + 1, Arrays.toString(stream));
                System.out.printf("        %s%n", why[t]);
                System.out.printf("        expected=%s%n", Arrays.toString(expected));
                System.out.printf("        actual  =%s => %s%n%n", Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
            }

            System.out.println("=".repeat(50));
            System.out.println();
        }

        System.out.printf("Overall Summary:%n");
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%s: %d/%d tests passed%n", names[i], passCounts[i], streams.length);
        }
    }
}
