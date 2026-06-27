package dado.lab;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


public class ShortestPathFunction implements Function<String, List<String>> {


    @Override
    public List<String> apply(String word) {
        List<String> neibs = new ArrayList<>();
        char[] sor = word.toCharArray();
        for (int i = 0; i < sor.length; i++) {
            char ori = sor[i];
            for (char j = 'a'; j <= 'z'; j++) {
                if (j != ori) {
                    sor[i] = j;
                    String now = new String(sor);
                    if (WORDS.contains(now)) {
                        neibs.add(now);
                    }
                }
            }
            sor[i] = ori;
        }
        return neibs;
    }

    private Set<String> WORDS;

    public ShortestPathFunction(Set<String> ws) {
        this.WORDS = ws;//or make a deep copy of ws for immutable concern
    }
}
