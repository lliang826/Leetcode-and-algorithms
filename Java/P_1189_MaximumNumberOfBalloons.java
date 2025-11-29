public class P_1189_MaximumNumberOfBalloons {
    /*
    Hash map approach. Iterate through the string and store the frequency of each character into a hashmap (or in this case, an 
    array, because it uses less space). Then, we can check the indices for the characters 'b', 'a', 'l', 'o', 'n'. The tricky 
    part of this problem is realizing that characters 'l' and 'o' are repeating, which means we need twice the frequency for 
    those 2 characters. 
    The minimum frequency for each character (or freq / 2 for 'l' and 'o') is the answer.
    
    Time: O(n), where n is the number of characters in the input string
    Space: O(1), array is fixed size (26 characters in the alphabet)
    */
    public int maxNumberOfBalloons(String text) {
        int[] arr = new int[26];
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            arr[c - 'a']++;
        }
        
        int min = arr['a' - 'a'];
        
        min = Math.min(min, arr['b' - 'a']);
        min = Math.min(min, arr['l' - 'a'] / 2);
        min = Math.min(min, arr['o' - 'a'] / 2);
        min = Math.min(min, arr['n' - 'a']);
        
        return min;
    }
    
    public static void main(String[] args) {
        P_1189_MaximumNumberOfBalloons solver = new P_1189_MaximumNumberOfBalloons();

        Object[][] tests = new Object[][] {
                { "nlaebolko", 1 },
                { "loonbalxballpoon", 2 },
                { "leetcode", 0 },
                { "balloon", 1 },
                { "balloonballoon", 2 },
                { "balon", 0 },
                { "balllllloooon", 1 },
                { "", 0 },
                { "bbaalllloooonn", 2 },
                { "abcdefghijklmnopqrstuvwxyz", 0 },
                { "balloonballoonballoon", 3 },
                { "baoln", 0 },
                { "lloon", 0 },
                { "balloo", 0 },
                { "krhizmmgmcrecekgyljqkldocicziihtgpqwbticmvuyznragqoyrukzopfmjhjjxemsxmrsxuqmnkrzhgvtgdgtykhcglurvppvcwhrpvrhvthmvz", 1 }
        };

        System.out.println("Running tests for P_1189_MaximumNumberOfBalloons.maxNumberOfBalloons\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            String input = (String) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.maxNumberOfBalloons(input);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: input=\"%s\" => expected=%d, actual=%d => %s\n",
                    i + 1, input, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("maxNumberOfBalloons: %d/%d tests passed\n", pass, tests.length);
    }
}
