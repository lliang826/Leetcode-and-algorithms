public class P_268_MissingNumber {
    public int missingNumber(int[] nums) {
        int[] count = new int[nums.length + 1];

        for (int num : nums) {
            count[num]++;
        }

        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        
    }
}
