import java.util.Arrays;

public class P_1710_MaximumUnitsOnATruck {
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
}
