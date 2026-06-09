package dado.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MNC {
    public void mnc(String str) {
        List<Character> sor = new ArrayList<>();
        sor.add('#');
        for (int i = 0; i < str.length(); i++) {
            sor.add(str.charAt(i));
            sor.add('#');
        }

        // #a#b#c# -> 7 -> odd
        // #a#a#a#a# -> 9 -> odd
        int[] mnc = new int[sor.size()];
        mnc[0] = 1;
        mnc[mnc.length - 1] = 1;
        for (int i = 1; i < mnc.length - 1; i++) {
            int prev = mnc[i - 1];
            int max = i - 1 + prev;
            if (max < i && i > prev) {
                mnc[i] = Math.min(max - i, mnc[i - 2]);
            } else {
                int len = 0;
                int cur = i;
                while (2 * i - cur >= 0 && cur < mnc.length) {
                    if (sor.get(cur) == sor.get(2 * i - cur)) {
                        len++;
                    } else {
                        break;
                    }
                    cur++;
                }
                mnc[i] = len;
            }
        }
        System.out.println(Arrays.toString(mnc));
    }
}
