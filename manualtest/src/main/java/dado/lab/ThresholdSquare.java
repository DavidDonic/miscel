package dado.lab;

public class ThresholdSquare {
    public int solution(int[][] mat, int roof) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return -1;
        }


        int max = 0;
        int m = mat.length;
        int n = mat[0].length;
        preSum(mat);


        for (int i = 0; i < m; i++) {
            for (int j = 0;j < n; j++) {
                if (max == Math.min(m, n)) {
                    return max;//already the extreme size
                }
                int len = max + 1;//first candidate to try at new (i,j)
                while (i + len <= m && j + len <= n) {
                    int sum = mat[i + len - 1][j + len - 1];
                    int lft = (j > 0) ? mat[i + len - 1][j - 1] : 0;
                    int up = (i > 0) ? mat[i - 1][j + len - 1] : 0;
                    int diag = (i > 0 && j > 0) ? mat[i - 1][j - 1] : 0;
                    int total = sum - lft - up + diag;
                    if (total <= roof) {
                        max = len;//new len acceptable
                        len++;
                    } else {
                        break;
                    }
                }
                //if (i + len > m && j + len > n) {
                //return max;
                //}can't have earlier stop here later we can have smaller i &/or j
            }
        }
        return max;
    }


    private void preSum(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (i == 0 && j == 0) {
                    continue;
                } else if (i == 0) {
                    mat[i][j] += mat[i][j - 1];
                } else if (j == 0) {
                    mat[i][j] += mat[i - 1][j];
                } else {
                    mat[i][j] += (mat[i - 1][j] + mat[i][j - 1] - mat[i - 1][j - 1]);
                }
            }
        }
    }
}
