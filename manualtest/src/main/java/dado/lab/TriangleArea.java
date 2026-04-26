package dado.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    int x;
    int y;
    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
public class TriangleArea {

    public long getArea(List<Integer>x, List<Integer> y) {
        // --- assuming valid 3 points list only ---
        Map<Integer, List<Integer>> byX = new HashMap<>();
        int idx = 0;
        for (int xx : x) {
            List<Integer> group = byX.getOrDefault(xx, new ArrayList<>());
            group.add(idx++);
            byX.put(xx, group);
        }
        int width = 0;
        int base = 0;
        int top = 0;
        for (int xx : x) {
            if (byX.get(xx).size() == 2) {
                width = Math.abs(y.get(byX.get(xx).get(0)) - y.get(byX.get(xx).get(1)));
                base = xx;
            } else {
                top = xx;
            }
        }
        if (width != 0) {
            return (long)width * Math.abs(base - top) / 2;
        }

        idx = 0;
        Map<Integer, List<Integer>>  byY = new HashMap<>();
        for (int yy : y) {
            List<Integer> group = byY.getOrDefault(yy, new ArrayList<>());
            group.add(idx++);
            byY.put(yy, group);
        }

        base = 0;
        width = 0;
        top = 0;
        for (int yy : y) {
            if (byY.get(yy).size() == 2) {
                width = Math.abs(x.get(byY.get(yy).get(0)) - x.get(byY.get(yy).get(1)));
                base = yy;
            } else {
                top = yy;
            }
        }
        return (long)width * Math.abs(base - top) / 2;
    }

}
