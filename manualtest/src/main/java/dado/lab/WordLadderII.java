package dado.lab;

import java.util.*;
import java.util.function.Function;

public class WordLadderII {

    public static void main(String[] args) {
        List<String> dict = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        System.out.println(findAllLadders("hit", "cog", dict));
        ShortestPathFunction wordTest = new ShortestPathFunction(Set.of("Haha", "Hehe", "Huhu"));
    }
    public static List<List<String>> findAllLadders(String beginWord, String endWord,
                                             List<String> words) {
        List<List<String>> result = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();

        if (beginWord == null | endWord == null || words == null) {
            return result;
        } else if (beginWord.equals(endWord)) {
            path.add(beginWord);
            result.add(path);
            return result;
        }

        //defer marking solution -> single BFS -> bi-directional BFS
        Map<String, List<String>> parents= new HashMap<>();
        Set<String> visited = new HashSet<>();
        Set<String> ws = new HashSet<>(words);
        ShortestPathFunction getNeibs = new ShortestPathFunction(ws);
        parents.put(beginWord, new ArrayList<>());
        visited.add(beginWord);

        Set<String> fwd = new HashSet<>();
        Set<String> bck = new HashSet<>();
        fwd.add(beginWord);
        bck.add(endWord);
        visited.add(beginWord);
        visited.add(endWord);

        while (!fwd.isEmpty() && !bck.isEmpty()) {
            int dir = (fwd.size() <= bck.size()) ? 1 : -1;
            Set<String> layer = (dir > 0) ? fwd : bck;//cannot hard-swap since dir needs fwd always be initial fwd
            Set<String> opposite = (dir > 0) ? bck : fwd;

            if (oneStepBFS(layer, opposite, visited, parents, dir, getNeibs)) {
                dfs(endWord, beginWord, result, path, parents);
                return result;
            }
        }
        return result;
    }

    private static void dfs(String cur, String begin,
                     List<List<String>> result, LinkedList<String> path,
                     Map<String, List<String>> parents) {
        path.addFirst(cur);
        if (cur.equals(begin)) {
            result.add(new LinkedList<>(path));
            path.poll();
            return;
        }

        for (String parent : parents.get(cur)) {
            dfs(parent, begin, result, path, parents);
        }
        path.poll();
    }

    private static boolean oneStepBFS(Set<String> layer, Set<String> opposite, Set<String> visited,
                                      Map<String, List<String>> parents, int dir,
                                      Function<String, List<String>> neibFnd) {
        boolean foundAll = false;
        Set<String> next = new HashSet<>();
        for (String word : layer) {
            for (String neib : neibFnd.apply(word)) {
                if (!visited.contains(neib) || opposite.contains(neib)) {
                    if (!visited.contains(neib)) next.add(neib);
                    if (opposite.contains(neib)) foundAll = true;
                    if (dir > 0) {
                        parents.putIfAbsent(neib, new ArrayList<>());
                        parents.get(neib).add(word);
                    } else {
                        parents.putIfAbsent(word, new ArrayList<>());
                        parents.get(word).add(neib);
                    }
                }
            }
        }
        visited.addAll(next);
        layer.clear();
        layer.addAll(next);
        return foundAll;
    }
}
