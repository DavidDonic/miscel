package dado.lab;

class HensAndGrains {
    private final int LIMIT = 1000000000;

    public static void main(String[] args) {
        int[] hens = {3, 6, 7};
        int[] grains = {2, 4, 7, 9};
        System.out.println(hensAndGrains(grains, hens));
    }

    public static int hensAndGrains(int[] grains, int[] hens) {
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
        //int longest = getLongest(grains, hens, pfx);
        int longest = getMax1(hens, grains);
        // --- BS for first occ. of valid time ---
        int lft = 0;
        int rht = longest;
        while (lft < rht) {
            int mid = lft + (rht - lft) / 2;
            if (isValid1(hens, grains, mid)) {
                rht = mid;
            } else {
                lft = mid + 1;
            }
        }
        return lft;
    }

    private static boolean isValid(int[] hens, int[] grains, int time, int[] pfx) {
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

    private static int getLongest(int[]gra, int[] hen, int[] pfx) {
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

    private static int firstGreater(int[] arr, int start, int val) {
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

    private static int lastSmaller(int[] arr, int start, int val) {
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

    private static boolean isValid1(int[] hens, int[] grains, int tgt) {
        int unEatenIdx = 0;
        for (int i = 0; i < hens.length; i++) {
            int hen = hens[i];
            int grain = grains[unEatenIdx];
            int timeRemain = tgt;
            if (grain < hen) {
                if (hen - grain > tgt) {
                    return false;
                }
                timeRemain -= (hen - grain);
                while (unEatenIdx < grains.length) {
                    if (grains[unEatenIdx] > hen) {
                        break;
                    }
                    unEatenIdx++;
                }
                hen = grain;//lftMost eaten grain
            }
            if (unEatenIdx == grains.length) {
                return true;
            }
            while (unEatenIdx < grains.length) {
                if (timeRemain < grains[unEatenIdx] - hen) {
                    break;
                }
                unEatenIdx++;
            }
            if (unEatenIdx == grains.length) {
                return true;
            }
        }
        return unEatenIdx == grains.length;
    }

    private static int getMax1(int[] hens, int[] grains) {
        int max = 0;
        int idx = 0;
        int hen = hens[0];
        int henIdx = 0;
        while (idx < grains.length) {
            if (henIdx < hens.length - 1) {
                max = Math.max(max, Math.abs(grains[idx] - hens[henIdx]));
                idx++;
                henIdx++;
            } else if (grains[idx] <= hens[henIdx]){
                max = Math.max(max, hens[henIdx] - grains[idx]);
                hen = grains[idx];
                int remain = (grains[grains.length - 1] > hens[henIdx]) ?
                        grains[grains.length - 1] - hen : 0;
                max = Math.max(max, remain);
                idx = grains.length;
            } else {
                max = Math.max(max, grains[grains.length - 1] - hens[henIdx]);
                idx = grains.length;
            }
        }
        return max;
    }
}
