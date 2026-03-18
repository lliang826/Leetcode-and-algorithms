public class P_14_LongestCommonPrefix {
    class Solution {
        public String longestCommonPrefix(String[] strs) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < strs[0].length(); i++) {
                char c = strs[0].charAt(i);
                sb.append(c);
            }

            for (int i = 1; i < strs.length; i++) {
                for (int j = 0; j < strs[i].length(); j++) {
                    char c = strs[i].charAt(j);
                    if (j < sb.length() && sb.charAt(j) != c) {
                        sb.setLength(j);
                        break;
                    }
                }

                int extraLength = sb.length() - strs[i].length();
                while (extraLength > 0) {
                    sb.deleteCharAt(sb.length() - extraLength);
                    extraLength--;
                }
            }

            return sb.toString();
        }
    }
}
