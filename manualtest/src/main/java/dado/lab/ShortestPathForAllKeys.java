package dado.lab;

import java.util.*;

public class ShortestPathForAllKeys {
    private static final char START = '@';
    private static final char EMPT = '.';
    private static final char WALL = '#';
    private static final int[][] NEIBS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final long KEYS = ((1L << 26) - 1);
    public static void main(String[] args) {
        String[] grid = new String[]{"@.a..","###.#","b.A.B"};
        System.out.println(shortestPathAllKeys(grid));
    }
    public static int shortestPathAllKeys(String[] grid) {
        //no duplicate keys
        //bit mask: all keys found - bit at (char-'a')==1
        //state representation: cell idx (1D) + "," + "keys hit so far"
        //->can open lock? -> lock-'A' in msk is 1
        //ending mask == all keys
        //long mask: 11111=31 5 + 5 + 26 = 36 < 63
        final int m = grid.length;
        final int n = grid[0].length();
        long start = -1;
        long all = 0L;
        long row = 0L;//must be long for valid move
        for (String rou : grid) {
            long col = 0L;
            for (char cell : rou.toCharArray()) {
                if (cell == START) {
                    start |= (row << 31);
                    start |= (col << 26);
                } else if (isKey(cell)) {
                    long val = (1L << (int)(cell - 'a'));
                    all |= val;
                }
                col++;
            }
            row++;
        }
        //String begin = start + "," + empty;//idx,hitten keys
        final long ALL = all;
        System.out.println(start);

        return shortestPath(start,
                state -> {
                    List<Long> neibs = new ArrayList<>();
                    long msk = ((long)state & KEYS);
                    long rou = (((long)state >> 31) & 31);
                    long col = (((long)state >> 26) & 31);
                    for (int[] neib : NEIBS) {
                        int row1 = (int)rou + neib[0];
                        int col1 = (int)col + neib[1];
                        //long cor = row1 * n + col1;
                        long msk1 = msk;
                        if (isValid(row1, col1, m, n)) {
                            char next = grid[row1].charAt(col1);
                            if (isKey(next)) {
                                long val = (1L << ((int)(next - 'a')));
                                msk1 |= val;
                            } else if (next == WALL) {
                                continue;
                            } else if (next == EMPT || next == START) {
                                msk1 = msk1;
                            } else {
                                int door = (int)(next - 'A');
                                if ((msk1 & (1L << door)) == 0) {
                                    continue;
                                }
                            }
                            msk1 |= ((long)col1 << 26);
                            msk1 |= ((long)row1 << 31);
                            neibs.add(msk1);
                        }
                    }
                    return neibs;
                },
                state -> {
                    long msk = (state & KEYS);
                    return 1 > 0;
                });
    }

    private static <T> int shortestPath(T start, java.util.function.Function<T, List<T>> getNeibs, java.util.function.Predicate<T> isGoal) {
        Set<T> visited = new HashSet<>();
        Deque<T> queue = new ArrayDeque<>();
        int step = 0;
        visited.add(start);
        queue.offerLast(start);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                T cur = queue.pollFirst();
                for (T neib : getNeibs.apply(cur)) {
                    if (isGoal.test(neib)) {
                        return step + 1;
                    }
                    if (visited.add(neib)) {
                        queue.offerLast(neib);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private static boolean isKey(char ch) {
        int diff = (int)(ch - 'a');
        return diff >= 0 && diff < 26;
    }

    private static boolean isValid(long r, long c, int m, int n) {
        return r >= 0 && c >= 0 && r < m && c < n;
    }
}
