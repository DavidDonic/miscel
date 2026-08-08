package dado.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class MST {
    public static void main(String[] args) {
        int[][] graph = new int[][]{{1, 2, 5}, {1, 3, 6}, {2, 3, 1}};
        System.out.println(kruskal(graph, 3));
    }

    private static int kruskal(int[][] connections, int n) {
        //1-indexed cities
        if ((connections == null || connections.length == 0) && n == 0) {
            return 0;
        } else if (connections == null || connections.length == 0) {
            return -1;
        }

        Arrays.sort(connections, (a, b) -> a[2] - b[2]);

        int[] labels = new int[n];
        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
            labels[i] = i;
        }

        int cnt = 1;
        int cost = 0;
        for (int[] path : connections) {
            int from = path[0];
            int to = path[1];
//            int labelTo = find(to - 1, labels);
//            int labelFrom = find(from - 1, labels);
//            if (labelTo == to - 1) {
//                cost += path[2];
//                labels[to - 1] = labelFrom;
//                cnt++;
//            }
            if (visited[to - 1] == 0) {
                visited[to - 1] = 1;
                cnt++;
                cost += path[2];
            }
            if (cnt == n) {
                return cost;
            }
        }
        return -1;
    }

    private static int find(int start, int[] labels) {
        return labels[start] = (labels[start] == start) ? start : find(labels[start], labels);
    }

}
