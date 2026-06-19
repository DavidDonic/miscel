package dado.lab;

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

    class ExtendedNode <S> {
        Node node;
        S state;
    }

    interface Constraint <S> {
       S initState(Node start);
    }


}
