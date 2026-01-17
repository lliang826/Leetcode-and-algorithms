public class P_167_TwoSum2InputArrayIsSorted {
    public int[] twoSum(int[] numbers, int target) {
        int left = 1;
        int right = numbers.length;

        while (left < right) {
            int sum = numbers[left - 1] + numbers[right - 1];
            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                break;
            }
        }

        return new int[] { left, right };
    }
    
    public int[] v2(int[] numbers, int target) {
        int left = 1;
        int right = numbers.length;

        while (left < right) {
            int sum = numbers[left - 1] + numbers[right - 1];
            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                return new int[] { left, right };
            }
        }

        return new int[] { -1, -1 };
    }
    
    public static void main(String[] args) {
        
    }
}
