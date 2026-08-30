package dado.lab;

import java.util.Arrays;

public class Maze {
    private static final int WALL = 1;
    private static final int ROAD = 0;
    private static final int[][] NEIBS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(maze(5)));
    }

    private static int[][] maze(int n) {
        // Write your solution here.
        // odd length square mat.
        // color cell to ROAD -> maze
        // ->topLeft=road
        // ->wall = road width = 1 -> 1row/1col
        // ->as much road as possible
        // dfs solution:
        // ->coloring current cell to ROAD, random decide the order to color valid neighbor to road
        // ->validity neighbor:
        //   1.2cell consecutive coloring / step -> parallel road partition by wall
        //     ->no cell neibored cell to be colored are road cell
        // O(T) = O(mn) = O(n^2) O(S) = O(n + n^2) = O(n^2)
        // default setting of mat: val 1 in all cells
        // randomnizer: 4 dir. {-1, 0} up, {1, 0} down, {0, -1}left, {0, 1}rht
        //              shuffle idx 0,1,2,3 -> advance maze in different order
        //                      Math.random() * remained idx number -> order of idx randomnized
        if (n <= 0) {
            return new int[0][0];
        } else if (n == 1) {
            return new int[][]{{0}};
        }

        int[][] maze = new int[n][n];
        for (int[] row : maze) {
            Arrays.fill(row, WALL);
        }
        maze[0][0] = ROAD;
        dfs(0, 0, maze);
        return maze;
    }

    private static void dfs(int row, int col, int[][] maze) {
        int[] dirs = randomizer();
        for (int idx : dirs) {
            int[] neib = NEIBS[idx];
            // if can move 2 steps 2 steps else 1 step
            int row1 = row + neib[0];
            int col1 = col + neib[1];
            if (extend(row1, col1, maze, -neib[0], -neib[1])) {
                maze[row1][col1] = ROAD;
                if (extend(row1 + neib[0], col1 + neib[1], maze, -neib[0], -neib[1])) {
                    maze[row1 + neib[0]][col1 + neib[1]] = ROAD;
                    dfs(row1 + neib[0], col1 + neib[1], maze);
                } else {
                    dfs(row1, col1, maze);
                }
            }
        }
    }

    private static int[] randomizer() {
        int[] base = new int[]{0, 1, 2, 3};
        int size = 4;
        while (size > 1) {
            int idx = (int)(Math.random() * size);
            swap(base, idx, size - 1);
            size--;
        }
        return base;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static boolean isValid(int r, int c, int n) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    private static boolean extend(int row, int col, int[][] maze, int r, int c) {
        if (!isValid(row, col, maze.length) || maze[row][col] == ROAD) {
            return false;
        }
        for (int[] neib : NEIBS) {
            if (neib[0] == r && neib[1] == c) {
                continue;
            }
            int row1 = row + neib[0];
            int col1 = col + neib[1];
            if (!isValid(row1, col1, maze.length)) {
                continue;
            }
            if (maze[row1][col1] == ROAD) {
                return false;
            }
        }
        return true;
    }
}
