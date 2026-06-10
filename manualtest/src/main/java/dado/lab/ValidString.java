package dado.lab;

import java.util.HashSet;
import java.util.Set;

public class ValidString {
    public String valid(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        Set<Integer> removed = new HashSet<>();
        int lft = 0;
        int rht = 0;
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (cur == '(') {
                lft++;
            } else if (cur == ')') {
                if (lft == rht) {
                    removed.add(i);
                } else {
                    rht++;
                }
            } else if (!isLower(cur)) {
                removed.add(i);
            } else {
                continue;
            }
        }

        lft = 0;
        rht = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char cur = str.charAt(i);
            if (cur == lft) {
                if (lft == rht) {
                    removed.add(i);
                } else {
                    lft++;
                }
            } else if (cur == ')') {
                if (!removed.contains(i)) {
                    rht++;
                }
            }
        }

        char[] res = str.toCharArray();
        int i = 0;
        int j = 0;
        while (j < str.length()) {
            if (removed.contains(j)) {
                j++;
            } else {
                res[i++] = res[j++];
            }
        }
        return new String(res, 0, i);
    }

    private boolean isLower(char ch) {
        return (ch - 'a' >= 0 && ch - 'a' < 26);
    }
}
