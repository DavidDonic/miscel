package dado.lab;

public class AndroidUnlock {
    public static void main(String[] args) {
        System.out.println(numberOfPatterns(1, 2));
    }

    public static int numberOfPatterns(int m, int n) {
        // Solution goes here
        // dfs for all possible valid walk with m<= keys <= n
        // int[] visited key on current pass
        // int[] cnt update when ever pass include [m,n] keys
        // int keys: distinct key along walks
        // relief visited key before return only when it is first visit now
        if (m > n || n <= 0) {
            return 0;
        } else if (n == 1) {
            return 9;
        }


        int[] visited = new int[9];

        return 4 * (dfs(0, 0, 1, m, n, visited) + dfs(0, 1, 1, m, n, visited))
                  + dfs(1, 1, 1, m, n, visited);
    }


    private static int dfs(int row, int col, int keys, int m, int n, int[] visited) {
        if (keys > n) {
            return 0;
        }

        int cnt = 0;
        if (keys >= m && keys <= n) {
            cnt++;
        }


        visited[row * 3 + col] = 1;
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int total = Math.abs(i) + Math.abs(j);
                if (isValid(row + i, col + j)) {
                    if (visited[3 * (row + i) + col + j] != 1 &&
                            (total % 2 != 0 || visited[3 * (row + i / 2) + col + j / 2] == 1)) {
                        cnt += dfs(row + i, col + j, keys + 1, m, n, visited);
                    }
                }
            }
        }
        visited[row * 3 + col] = 0;
        return cnt;
    }


    private static boolean isValid(int r, int c) {
        return r >= 0 && c >= 0 && r < 3 && c < 3;
    }
}
