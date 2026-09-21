import java.util.Arrays;

public class P_1196_HowManyApplesCanYouPutIntoTheBasket {
    public int maxNumberOfApples(int[] weight) {
        Arrays.sort(weight);

        int sum = 0;
        int count = 0;
        for (int apple : weight) {
            sum += apple;
            if (sum > 5000) {
                return count;
            } else {
                count++;
            }
        }

        return count;
    }
}
