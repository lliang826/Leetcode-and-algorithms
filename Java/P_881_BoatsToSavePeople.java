import java.util.Arrays;

public class P_881_BoatsToSavePeople {
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
}
