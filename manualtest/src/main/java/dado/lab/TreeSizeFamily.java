package dado.lab;

public class TreeSizeFamily {

    public static void main(String[] args) {
        System.out.println(parent(11));
    }
    public static int parent(int child) {
        if (child == 2) {
            return 1;
        }

        int left = 1;
        int right = child;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long lastChild = 1 + (mid + 1L) * mid / 2;
            if (lastChild >= child) {
                right = mid;
            } else {
                left = mid + 1;//no need to last 2 when ending search, mid+1 can always let lft=rht
            }
        }

        return left;//some case need to further check left, this problem no need -> answer guaranteed
    }
}
