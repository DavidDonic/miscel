package dado.lab;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

//        Recommendation recommendation = new Recommendation();
//        Map<String, List<String>> similarityGraph = new HashMap<>();
//
//        LocalDate today = LocalDate.now();
//
//        similarityGraph.put("Inception", Arrays.asList("Interstellar", "Shutter Island"));
//        similarityGraph.put("Interstellar", Arrays.asList("Inception", "The Martian" ));
//        similarityGraph.put("Shutter Island", Arrays.asList("Inception", "Gone Girl" ));
//        similarityGraph.put("The Martian", Arrays.asList("Interstellar"));
//        similarityGraph.put("Gone Girl", Arrays.asList("Shutter Island"));
//
//        recommendation.assertEquals("Normal Case (n=3)",
//                recommendation.getSimilar("Interstellar", 3, similarityGraph),
//                Arrays.asList("Inception", "The Martian", "Shutter Island"));
//
//        recommendation.assertEquals("The Closest (n=1)",
//                recommendation.getSimilar("Interstellar", 1, similarityGraph),
//                Arrays.asList("Inception"));
//
//        recommendation.assertEquals("All Similar (n=100)",
//                recommendation.getSimilar("Interstellar", 100, similarityGraph),
//                Arrays.asList("Inception", "The Martian", "Shutter Island", "Gone Girl"));
//
//        System.out.println(today);
        // --- hens - triangle ---
//        HensAndGrains HAG = new HensAndGrains();
//        TriangleArea TG = new TriangleArea();
//        int[] hens = new int[]{3, 6, 7};
//        int[] grains = new int[]{3, 6, 7};
//        List<Integer> x = Arrays.asList(3, 0, 3);
//        List<Integer> y = Arrays.asList(0, 3, 3);
//        //System.out.println(HAG.hensAndGrains(grains, hens));
//        System.out.println(TG.getArea(x, y));
        MinHeightTree MHT = new MinHeightTree();
        int[][] edges = new int[][]{{0, 1}, {0, 2}, {0, 3}, {2, 4}, {0, 5}, {5, 6}, {6, 7}, {2, 8}, {7, 9}};
        System.out.println(MHT.mht(10, edges));
    }
}
