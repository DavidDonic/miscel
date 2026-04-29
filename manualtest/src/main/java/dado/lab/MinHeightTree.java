package dado.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinHeightTree {
    public List<Integer> mht(int n, int[][] edges) {
        // --- rerooting solution ---
        // --- 1.self height (subtree)---
        // --- 2.max(other tree height + 1) (neibs tree) -> current root height
        // --- 3.min height root find -> height[] -> iteration
        // --- definition method --- -> what is the min height root
        // --- longest leaf to leaf path's middle 1(2) node(s)
        if (n < 1) {
            return new ArrayList<>();
        } else if (n == 1) {
            return Arrays.asList(0);
        } else if (edges == null || edges.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> ejs = new ArrayList<>();
        getEdges(edges, ejs, n);

        int[] height = new int[n];
        int[] upperPath = new int[n];
        upperPath[0] = 1;//depends on which is dfs start

        // --- post-order-subtree info ---
        postOrder(ejs, 0, 0, height, upperPath);

        // --- preorder ---
        preOrder(ejs, 0, 0, height, upperPath);

        int min = Integer.MAX_VALUE;
        for (int tall : height) {
            min = Math.min(min, tall);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (height[i] == min) {
                result.add(i);
            }
        }
        System.out.println(Arrays.toString(height));
        System.out.println(Arrays.toString(upperPath));
        return result;
    }

    private void getEdges(int[][] edges, List<List<Integer>> ejs, int n) {
        for (int i = 0; i < n; i++) {
            ejs.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            ejs.get(edge[0]).add(edge[1]);
            ejs.get(edge[1]).add(edge[0]);
        }
    }

    private int postOrder(List<List<Integer>> edges, int cur, int prev, int[] height, int[] upper) {
        int fst = 1;
        int sec = 1;
        for (int neib : edges.get(cur)) {
            if (neib != prev) {
                int ret = postOrder(edges, neib, cur, height, upper) + 1;
                if (cur == 0) {
                    System.out.println(ret);
                }
                upper[neib] = ret;
                if (fst < ret) {
                    sec = fst;
                    fst = ret;
                } else if (sec < ret) {
                    sec = ret;
                }
                height[cur] = Math.max(height[cur], ret);
            }
        }
        if (height[cur] == 0) {
            height[cur]++;
        }
        for (int neib : edges.get(cur)) {
            if (neib != prev) {
                upper[neib] = (upper[neib] == fst) ? sec + 1 : fst + 1;
            }
        }
        //System.out.println(Arrays.toString(upper));
        return height[cur];
    }

    private void preOrder(List<List<Integer>> edges, int cur, int prev, int[] height, int[] upper) {
        // --- height[cur] VS upper VS root to cur path ---
        // height[prev]
        int prevUpper = (prev == cur) ? 0 : upper[prev];
        height[cur] = Math.max(Math.max(upper[cur], height[cur]), prevUpper + 1);
        upper[cur] = Math.max(upper[cur], prevUpper + 1);
        for (int neib : edges.get(cur)) {
            if (neib != prev) {
                preOrder(edges, neib, cur, height, upper);
            }
        }
    }
}
