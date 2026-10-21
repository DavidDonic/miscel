package dado.lab;

import java.util.Arrays;

public class MergeStones {
    public static void main(String[] args) {
        //test theory -> [3k][4k] = [7k] not merge to 5k | 2k -> 3 piles
        int[] stones = new int[]{5,4,3,2,1,
                                 5,4,3,2,1,
                                 5,4,3,2,1,
                                 5,4,3,2,1,
                                 5,4,3,2,1,
                                 5,4,3,2,1,
                                 5,4,3,2,1,
                                 5,4};
        System.out.println(merge(stones, 5));
    }

    private static int merge(int[] stones, int k) {
        if (stones == null || stones.length <= 1) return 0;

        int n = stones.length;
        if ((n - 1) % (k - 1) != 0) return -1;

        final int INF = Integer.MAX_VALUE / 4;
        int[][] dp = new int[n][n];
        int[] prefix = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + stones[i - 1];
        }

        for (int len = k; len <= n; len++) {
            for (int lft = 0; lft < n; lft++) {
                int rht = lft + len - 1;
                if (rht >= n) break;

                dp[lft][rht] = INF;
                //how to make sure the selected mid makes no further merge:
                //->lft region length: 1, k, qk-q+1
                //->rht region: len-1 len-k... len - qk + q - 1
                //->lft-1 % k-1 + 1 = (q(k-1)) % (k-1) + 1 = 1 => final pile
                //->rht: (len - 1) - q(k-1) pile -> if further merge remain pile >= k-1
                //-> actually (len-1) - 1 - q(k-1) % (k-1) = (len-2) % (k-1) + 1
                //for every possible region always make sure left merge to 1
                //rht region is always merged to mincost situation
                //->reason: lft is 1 [rht][lft]only mergeable when rht=k-1 -> total is mergeable -> considered at last
                //->[rht] merge to at most k-1 pile -> only chance for further merge
                for (int mid = lft; mid < rht; mid += (k - 1)) {
                    dp[lft][rht] = Math.min(dp[lft][rht],
                            dp[lft][mid] + dp[mid + 1][rht]);
                }
                if ((len - 1) % (k - 1) == 0) dp[lft][rht] += prefix[rht + 1] - prefix[lft];
            }
        }

        System.out.println(dp[0][14]);
        System.out.println(dp[15][34]);
        System.out.println(dp[0][34]);
        return dp[0][n - 1];
    }
}
