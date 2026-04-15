package dado.lab;
import java.time.LocalDate;
import java.util.*;

public class Recommendation {
    /*
     * hdfdfdf
     * dfdf
     * @param movieName movie to be searched
     * @param n how many movies returned
     * @return n movies with top-similarity
     */




    public List<String> getSimilar(String movieName, int n,
                           Map<String, List<String>> similiarityGraph)
    {
        // --- edge case handling ---
        if (similiarityGraph.isEmpty() ||
        !similiarityGraph.containsKey(movieName)) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Deque<String> queue = new ArrayDeque<>();
        queue.add(movieName);
        Set<String> visited = new HashSet<>();
        visited.add(movieName);

        while (!queue.isEmpty()) {

            String currentMovie = queue.poll();

            for (String closest : similiarityGraph.get(currentMovie)) {
                if (visited.add(closest)) {
                    result.add(closest);
                    if (n == result.size()) {
                        return result;
                    }
                    queue.offerLast(closest);
                }
            }
        }

        return result;
    }

    // --- tests mock Junit---
    public void assertEquals(String testName, Object actual, Object expected)
    {
        if (Objects.equals(actual, expected)) {
            System.out.println("√ " + testName + " PASSED");
        } else {
            System.out.println("× " + testName + " FAILED");
            System.out.println("      expected: " + expected);
            System.out.println("      actual:   " + actual);
        }
    }
}
