package dado.lab;

import java.util.*;

public class WalkReachability {
    static class Node {
        int x;
        int y;
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public int hashCode() {
            return x * 31 + y * 31 * 31;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (!(obj instanceof Node)) {
                return false;
            } else {
                Node now = (Node)obj;
                return (now.x == x & now.y == y);
            }
        }
    }

    static class ExtendedNode <S> {
        Node node;
        S state;
        ExtendedNode(Node node, S state) {
            this.node = node;
            this.state = state;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (!(obj instanceof ExtendedNode)) {
                return false;
            } else {
                ExtendedNode<?> now = (ExtendedNode<?>)obj;
                return (now.node.equals(node) && now.state.equals(state));
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, state);
        }
    }

    interface Constraint <S> {
       S initState(Node start);
       S nextState(S state, Node from, Node to);
       boolean isGoal(S state, Node now, Node goal);
    }

    public static <S> boolean existConstraintPath(Map<Node, Set<Node>> adjList,
                                                  Node start, Node end,
                                                  Constraint<S> constraint) {
        Set<ExtendedNode<S>> visited = new HashSet<>();
        Deque<ExtendedNode> queue = new ArrayDeque<>();
        ExtendedNode<S> startNode = new ExtendedNode<>(start, constraint.initState(start));
        if (constraint.isGoal(startNode.state, start, end)) {
            return true;
        }
        queue.offerLast(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            ExtendedNode<S> cur = queue.pollFirst();
            if (!adjList.containsKey(cur.node)) continue;
            for (Node next : adjList.get(cur.node)) {
                ExtendedNode<S> nxtExt = new ExtendedNode<>(next, constraint.nextState(cur.state, cur.node, next));
                if (visited.add(nxtExt)) {
                    queue.offerLast(nxtExt);
                    if (constraint.isGoal(nxtExt.state, next, end)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final Constraint<Integer> ODD_STEPS = new Constraint<>() {
        @Override
        public Integer initState(Node start) {
            return 0;
        }

        @Override
        public Integer nextState(Integer step, Node cur, Node to) {
            return step ^ 1;//0 ^ 1 = 1 1 ^ 1 = 0 -> even->odd->even
        }

        @Override
        public boolean isGoal(Integer state, Node cur, Node goal) {
            return (cur.equals(goal) && state % 2 == 1);
        }
    };

    public static void main(String[] args) {
        Node start = new Node(0, 0);
        Map<Node, Set<Node>> adjList = new HashMap<>();
        Set<Node> exp = new HashSet<>();
        exp.add(new Node(0, 1));
        exp.add(new Node(0, -1));
        exp.add(new Node(-1, 0));
        exp.add(new Node(1, 0));
        adjList.put(start, exp);
        System.out.println(existConstraintPath(adjList, start, new Node(0, 1), ODD_STEPS));
    }

}
