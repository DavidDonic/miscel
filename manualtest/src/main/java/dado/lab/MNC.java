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
        int center = 0;
        int max = 1;
        for (int i = 1; i < mnc.length - 1; i++) {
            if (i < max) {
                mnc[i] = Math.min(max - i, mnc[2 * center - i]);
            } else {
                mnc[i] = 1;
            }
            while (i + mnc[i] < mnc.length && i >= mnc[i] && sor.get(i + mnc[i]) == sor.get(i - mnc[i])) {
                mnc[i]++;//further possible extension if prev result is restricted by boundary
            }
            if (i + mnc[i] > max) {
                //boundary extended
                max = i + mnc[i];
                center = i;
            }
        }
        System.out.println(Arrays.toString(mnc));
        //return mnc;
    }
}
