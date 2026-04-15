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
        HensAndGrains HAG = new HensAndGrains();
        int[] hens = new int[]{3, 6, 7};
        int[] grains = new int[]{3, 6, 7};

        System.out.println(HAG.hensAndGrains(grains, hens));
    }
}
