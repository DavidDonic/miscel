package dado.lab;

class HensAndGrains {
    private final int LIMIT = 1000000000;

    public int hensAndGrains(int[] grains, int[] hens) {
        // --- get the min timet that hens eat all grains ---
        if (grains == null || grains.length == 0) {
            return 0;
        }
        // --- aux. array set up ---
        int[] pfx = new int[grains.length];
        for (int i = 1; i < grains.length; i++) {
            pfx[i] += (grains[i] - grains[i - 1]);
        }
        // --- corner case 2 ---
        if (hens.length == 1) {
            return getLongest(grains, hens, pfx);
        }
        // --- upper bound decision ---
        int longest = getLongest(grains, hens, pfx);
        // --- BS for first occ. of valid time ---
        int lft = 0;
        int rht = longest;
        while (lft < rht - 1) {
            int mid = lft + (rht - lft) / 2;
            if (isValid(hens, grains, mid, pfx)) {
                rht = mid;
            } else {
                lft = mid + 1;
            }
        }
        return (isValid(hens, grains, lft, pfx)) ? lft : rht;
    }

    private boolean isValid(int[] hens, int[] grains, int time, int[] pfx) {
        boolean[] visited = new boolean[grains.length];
        int firstUn = 0;

        // --- no overlapped hens' paths -> no lft uneat of the hen ---
        // --- purpose: get the rht most idx of eaable grain for each hen under restriction above
        for (int hen : hens) {
            if (grains[firstUn] >= hen) {
                while (firstUn < grains.length) {
                    if (grains[firstUn] > hen + time) {
                        break;
                    }
                    firstUn++;
                }
            } else {
                //TODO: possible optimizatioh
                int lastSmall = lastSmaller(grains, firstUn, hen);
                int remote = -1;
                if (lastSmall == grains.length - 1) {
                    return (hen - grains[lastSmall] + pfx[lastSmall] - pfx[firstUn] <= time);
                } else {
                    int lftMove = hen - grains[lastSmall] + pfx[lastSmall] - pfx[firstUn];
                    int lftFirst = firstGreater(grains, lastSmall, grains[firstUn] + time - lftMove);
                    int rhtFirst = firstGreater(grains, lastSmall, hen + (time - lftMove) / 2);
                    firstUn = Math.max(firstUn, Math.max(lftFirst, rhtFirst));
                }
            }

            if (firstUn >= grains.length) {
                return true;
            }
        }
        return firstUn > grains.length;
    }

    private int getLongest(int[]gra, int[] hen, int[] pfx) {
        // --- regional optimized overall worst case recording ---
        int max = 0;
        for (int he : hen) {
            //first idx with val >= he
            int idx = firstGreater(gra, 0, he);
            int total = 0;
            if (idx == gra.length || idx == 0) {
                //all lft
                total = pfx[gra.length - 1] + Math.abs(he - gra[gra.length - 1]);
            } else {
                total = Math.min(gra[idx] - he + 2 * pfx[gra.length - 1] - pfx[idx],
                                 he - gra[idx - 1] + pfx[idx - 1] + pfx[gra.length - 1]);
            }
            max = Math.max(max, total);
        }
        return max;
    }

    private int firstGreater(int[] arr, int start, int val) {
        int lft = start;
        int rht = arr.length - 1;
        //mid < vsl -> lft=mid+1 else rht=mid
        while (lft < rht - 1) {
            int mid = lft + (rht - lft) / 2;

            if (arr[mid] < val) {
                lft = mid + 1;
            } else {
                rht = mid;
            }
        }

        if (arr[lft] >= val) {
            return lft;
        } else if (arr[rht] >= val) {
            return rht;
        } else {
            return arr.length;
        }
    }

    private int lastSmaller(int[] arr, int start, int val) {
        int lft = start;
        int rht = arr.length - 1;

        while (lft < rht - 1) {
            int mid = lft + (rht - lft) / 2;
            if (arr[mid] > val) {
                rht = mid - 1;
            } else {
                lft = mid;
            }
        }
        if (arr[rht] <= val) {
            return rht;
        } else if (arr[lft] <= val) {
            return lft;
        } else {
            return -1;
        }
    }
}
