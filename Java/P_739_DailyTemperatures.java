import java.util.ArrayDeque;
import java.util.Deque;

public class P_739_DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peekLast()]) {
                int index = stack.removeLast();
                ans[index] = i - index;
            }
            stack.addLast(i);
        }

        return ans;
    }

    public static void main(String[] args) {
        
    }
}
