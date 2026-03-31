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
    public static void main(String[] args) {

        Recommendation recommendation = new Recommendation();
        Map<String, List<String>> similarityGraph = new HashMap<>();

        LocalDate today = LocalDate.now();

        similarityGraph.put("Inception", Arrays.asList("Interstellar", "Shutter Island"));
        similarityGraph.put("Interstellar", Arrays.asList("Inception", "The Martian" ));
        similarityGraph.put("Shutter Island", Arrays.asList("Inception", "Gone Girl" ));
        similarityGraph.put("The Martian", Arrays.asList("Interstellar"));
        similarityGraph.put("Gone Girl", Arrays.asList("Shutter Island"));

        assertEquals("Normal Case (n=3)",
                recommendation.getSimilar("Interstellar", 3, similarityGraph),
                Arrays.asList("Inception", "The Martian", "Shutter Island"));

        assertEquals("The Closest (n=1)",
                recommendation.getSimilar("Interstellar", 1, similarityGraph),
                Arrays.asList("Inception"));

        assertEquals("All Similar (n=100)",
                recommendation.getSimilar("Interstellar", 100, similarityGraph),
                Arrays.asList("Inception", "The Martian", "Shutter Island", "Gone Girl"));

        System.out.println(today);
    }



    private List<String> getSimilar(String movieName, int n,
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
    private static void assertEquals(String testName, Object actual, Object expected)
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
