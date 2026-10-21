package dado.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectCoinsTree {
    public static void main(String[] args) {
        int[][] edges = new int[][]{{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};
        int[] coins = new int[]{0, 0, 0, 1, 1, 0, 0, 1};
        System.out.println(shortest(coins, edges));
    }

    private static int shortest(int[] coins, int[][] edges) {
        //preorder updating, upper parent>upper cur greater than 2? -> += 2
        //far of prev: sibling and upper included

        int n = coins.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] far = new int[n];
        int[] sub = new int[n];
        int[] sibling = new int[n];

        postOrder(graph, coins, far, sub, sibling, -1, 0);
        System.out.println(Arrays.toString(sub));
        System.out.println(Arrays.toString(sibling));
        System.out.println(Arrays.toString(far));
        preOrder(graph, coins, far, sub, sibling, -1, 0);

        int min = Integer.MAX_VALUE;
        for (int res : sub) min = Math.min(res, min);

        return min;
    }

    private static int postOrder(List<List<Integer>> graph, int[] coins, int[] far, int[] sub, int[] sibling,
                                 int prev, int cur) {
        if (graph.get(cur).size() == 1 && prev >= 0) return coins[cur];

        int deep = 0;
        int dia1 = 0;

        for (int neib : graph.get(cur)) {
            if (neib == prev) continue;

            int ret = postOrder(graph, coins, far, sub, sibling, cur, neib);

            if (ret > 2) sub[cur] += sub[neib] + 2;
            if (ret >= 2) far[cur]++;
            if (ret <= 1) {
                if (ret == 1) dia1++;
                sibling[neib] = -2;
            }
            deep = Math.max(deep, ret);
        }

        if (deep >= 2) far[cur] = 1;

        for (int neib : graph.get(cur)) {
            if (neib == prev) continue;

            //sibling and far of neib
            far[neib] = (sibling[neib] < 0) ? far[cur] + dia1 : far[cur] + dia1 - 1;
            far[neib] -= (sibling[neib] < 0 && coins[neib] == 1) ? 1 : 0;

            if (sub[cur] == 0 && far[cur] == 0) {
                sibling[neib] = 0;
            } else if (sub[cur] == 0) {
                sibling[neib] = (far[cur] == 1 && sibling[neib] == 0) ? 0 : 2;
            } else {
                sibling[neib] = (sibling[neib] >= 0 && sub[cur] == 2 + sub[neib]) ? 0 : sub[cur] - sub[neib] - sibling[neib];
            }
        }

        if (prev < 0) far[cur] = 0;

        return (deep == 0 && coins[cur] == 0) ? 0 : deep + 1;
    }

    private static void preOrder(List<List<Integer>> graph, int[] coins, int[] far, int[] sub, int[] sibling, int prev, int cur) {
        int upper = (prev < 0) ? 0 : sibling[prev];//higher to prev
        //prev need to be visited or not
        if (prev >= 0 && (far[prev] > 0 || upper > 0)) {
            //visited prev before?
            if (sibling[cur] == 0) upper += 2;
        }
        sub[cur] += (sibling[cur] + upper);
        sibling[cur] += upper;
        far[cur] += (prev >= 0) ? far[prev] : 0;
        //far[cur] += (prev >= 0 && coins[prev] == 1 && far[prev] == 0) ? 1 : 0;

        for (int neib : graph.get(cur)) {
            if (neib == prev) continue;
            if (prev >= 0 && far[prev] == 0 && coins[prev] == 1) far[neib]++;
            preOrder(graph, coins, far, sub, sibling, cur, neib);
        }

    }
}
