package dado.lab;

import java.util.*;

public class WalkDCGWays {
    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(Arrays.asList(1));
        graph.add(Arrays.asList(2, 3));
        graph.add(new ArrayList<>());
        graph.add(Arrays.asList(4));
        graph.add(Arrays.asList(5));
        graph.add(Arrays.asList(2));//2 walks
        //graph.add(Arrays.asList(1));//INF
        //graph.add(new ArrayList<>());// 1 walk
        System.out.println(dfs(graph, 2, 0, new HashSet<>(), new HashSet<>()));
    }
    public static int dfs(List<List<Integer>> edges, int tgt, int cur, Set<Integer> onPath, Set<Integer> cycleHitten) {
        boolean fromTgt = (cur == tgt) ? true : false;
        onPath.add(cur);
        int sum = (cur == tgt) ? 1 : 0;
        for (int neib : edges.get(cur)) {
            if (onPath.contains(neib)) {
                cycleHitten.add(neib);
            } else {
                int ret = dfs(edges, tgt, neib, onPath, cycleHitten);
                if (ret != 0) {
                    fromTgt = true;
                }
                if (ret == Integer.MAX_VALUE) {
                    return ret;
                } else {
                    sum += ret;
                }
            }
        }
        onPath.remove(cur);
        return (cycleHitten.contains(cur) && fromTgt) ? Integer.MAX_VALUE : sum;
    }
}
