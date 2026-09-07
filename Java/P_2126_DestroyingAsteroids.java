import java.util.Arrays;

public class P_2126_DestroyingAsteroids {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        long total = mass;

        for (int i : asteroids) {
            if (total >= i) {
                total += i;
            } else {
                return false;
            }
        }

        return true;
    }
}
