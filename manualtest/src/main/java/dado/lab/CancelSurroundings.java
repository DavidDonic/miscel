package dado.lab;

import java.util.*;

public class CancelSurroundings {
    private static final int[][] NEIBS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1},
                                                     {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    public static void main(String[] args) {
        long val = -1L;
        // mat is a 8*8 positive int matrix
        int[][] mat = new int[][]{{133,319,475,144,318,887,256,152},
                                  {941,947,766,137,157,901,858,254},
                                  {928,501,454,642,168,772,89,952},
                                  {260,853,814,132,770,407,928,305},
                                  {560,202,657,493,110,563,982,549},
                                  {650,161,651,829,304,107,770,983},
                                  {732,434,403,605,805,355,415,998},
                                  {873,236,753,679,629,131,262,58}};
        System.out.println(Long.bitCount(val));
        System.out.println(largestSum(mat));
    }

    public static int largestSum(int[][] matrix) {
        // Write your solution here
        // 8 neighbor cancelled -> select 1 val what is the state change
        Map<Integer, long[]> CancelledCells = new HashMap<>();
        getMap(CancelledCells);

        // --- initial state try all possible select order get final sum ---
        int[] max = new int[]{Integer.MIN_VALUE};
        //-1L: 000001 -> 111110 -> 111111
        Map<Long, Integer> calculated = new HashMap<>();
        //pruning: reach current state from higher sum -> neglect

        dfs(-1L, Long.MAX_VALUE, 0, CancelledCells, max, matrix, calculated, new HashMap<>());

        return max[0];
    }

    private static void dfs(long remained, long noLast, int sum, Map<Integer, long[]> cancelled,
                            int[] max, int[][] mat, Map<Long, Integer> checked, Map<Long, Set<Integer>> calculated) {
        if (remained == 0L) {
            max[0] = Math.max(max[0], sum);
            return;
        }

        if (checked.containsKey(remained) && checked.get(remained) >= sum) {
            return;
        } else {
            checked.put(remained, sum);//larger path to current
        }

        if (calculated.containsKey(remained) && calculated.get(remained).contains(sum)) {
            return;
        } else {
            calculated.put(remained, new HashSet<>());
        }

        long ava = remained;
        int flag = 0;
        while (ava != 0L || flag == 63) {
            long lowBit = (flag == 63) ? 0L : (ava & (-ava));
            int idx = (flag == 63)  ? flag : Long.bitCount(lowBit - 1);
            int row = idx / 8;
            int col = idx % 8;
            long mask1 = cancelled.get(idx)[0];
            long mask2 = cancelled.get(idx)[1];
            if (Long.bitCount(ava) == 2 && ava < 0) {
                flag = 63;
                ava = 0;//for last round
            } else {
                ava ^= lowBit;
            }
            // --- next mask ---
            if ((noLast ^ (noLast & mask2)) == 0L && remained < 0L) {
                if (mask1 < 0) {
                    dfs(0, 0, sum + mat[row][col], cancelled, max, mat, checked, calculated);
                } else {
                    dfs(0, 0, sum + mat[row][col] + mat[7][7], cancelled, max, mat, checked, calculated);
                }
            } else {
                long next = (remained ^ (remained & mask1));
                long nextNo = next;
                if (next < 0L) {
                    nextNo = (next ^ (-Long.MAX_VALUE) ^ 1L);
                }
                dfs(next, nextNo, sum + mat[row][col], cancelled, max, mat, checked, calculated);
            }
        }
        calculated.get(remained).add(sum);
    }

    private static void getMap(Map<Integer, long[]> map) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean hasLast = false;
                int idx = i * 8 + j;
                long mask = 0L;
                if (idx != 63) {
                    mask |= (1L << idx);
                } else {
                    hasLast = true;
                }
                for (int[] neib : NEIBS) {
                    int row1 = i + neib[0];
                    int col1 = j + neib[1];
                    int idx1 = row1 * 8 + col1;
                    if (isValid(row1, col1, 8, 8)) {
                        if (idx1 != 63) {
                            mask |= (1L << idx1);
                        } else {
                            hasLast = true;
                        }
                    }
                }
                map.put(idx, new long[]{mask, mask});
                if (hasLast) {
                    long lowBit = (mask & (-mask));
                    mask |= (-Long.MAX_VALUE);
                    if (lowBit != 1L) {
                        mask ^= 1L;//clean extra 1
                    }
                }
                map.get(idx)[0] = mask;
            }
        }
    }

    private static boolean isValid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
