public class P_1323_Maximum69Number {
    public int maximum69Number(int num) {
        String s = Integer.toString(num);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '6') {
                String res = s.substring(0, i) + "9" + s.substring(i + 1);
                return Integer.parseInt(res);
            }
        }

        return num;
    }
}
