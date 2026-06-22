package dado.lab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WalkInDirectedGraph {
    class Node {
        int id;
        List<Node> children = new ArrayList<>();
        Node(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (!(obj instanceof Node)) {
                return false;
            } else {
                Node now = (Node)obj;
                return (now.id == id);
            }
        }
    }

    public int walk(Node start, Node end, List<Node> graph) {
        Set<Integer> visited = new HashSet<>();
        int[] count = new int[1];
        if (start.id == end.id) {
            count[0]++;
        }
        Set<Integer> revisited = new HashSet<>();
        visited.add(start.id);
        dfs(start, end, graph, visited, revisited, count);
        return (count[0] == -1) ? Integer.MAX_VALUE : count[0];
    }

    private Node dfs(Node cur, Node end, List<Node> graph, Set<Integer> visited, Set<Integer> revisited, int[] count) {
        //dfs along edge
        //if node in revisited and can reach end return inf.
        //if reach end and
        boolean toEnd = false;
        visited.add(cur.id);
        for (Node child : graph.get(cur.id).children) {
            if (visited.contains(child.id)) {//meet cycle
                if (child.equals(end)) {
                    count[0] = -1;
                    return end;
                }
                revisited.add(child.id);
                continue;
            }
            Node ret = dfs(child, end, graph, visited, revisited, count);
            if (ret != null) {
                if (revisited.contains(cur.id)) {//path to end and has cycle
                    count[0] = -1;
                    return ret;
                }
                if (child.equals(end)) {//new way found before go to end
                    count[0]++;
                }
                toEnd = true;
            } else if (count[0] == -1) {
                return ret;
            } else if (toEnd && revisited.contains(cur.id)) {
                count[0] = -1;
                return end;
            }
        }
        return (toEnd) ? end : null;
    }
}
