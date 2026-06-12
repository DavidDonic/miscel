package dado.lab;

public class MissingTwo {
    public int[] missing2(int[] array) {
        if (array.length == 0) {
            return new int[]{1, 2};
        }


        //consecutive missing: first [i]=i+3
        //[i]=i+2 [j]=i+3
        //case1:no i+2 & i+3 -> last2 missed len+1, len+2
        //case2: i+3 only -> 2 consecutive missing: i+2, i+1
        //case3: both -> i+2 j+3 missing

        int plus2 = firstOcc(array, 2);
        int plus3 = firstOcc(array, 3);


        if (plus2 != -1 && plus3 != -1) {
            return new int[]{plus2 + 1, plus3 + 2};
        } else if (plus3 != -1) {
            return new int[]{plus3 + 1, plus3 + 2};
        } else {
            return new int[]{array.length + 1, array.length + 2};
        }
    }


    private int firstOcc(int[] array, int plus) {
        int lft = 0;
        int rht = array.length - 1;


        while (lft < rht - 1) {
            int mid = lft + (rht - lft) / 2;
            int val = array[mid];
            if (val >= mid + plus) {
                rht = mid;
            } else {
                lft = mid + 1;
            }
        }


        if (array[lft] == lft + plus) {
            return lft;
        }
        return (array[rht] == rht + plus) ? rht : -1;
    }
}
