package dado.lab;

import java.util.function.Predicate;

public class ModulePredicate {
    private static final String TYPE = "INTEGER";

    public static void main(String[] args) {
        Predicate<Integer> equals = Predicate.isEqual(1);
        Predicate<Integer> isNonNegative = n -> n >= 0;
        Predicate<Integer> isNegative = isNonNegative.negate();
        Predicate<Integer> isEven = isNonNegative.and(n -> n % 2 == 0);
        Predicate<Integer> large = n -> n > 100;

        // a && b || c    VS   c || a && b
        // (a && b) || c    VS    (c || a) && b -> no priority calculate lft to rht
        Predicate<Integer> evenOrLarge = isNonNegative.and(n -> n % 2 == 0).or(n -> n > 100);
        Predicate<Integer> evenOrLarge1 = large.or(isNonNegative).and(n -> n % 2 == 0);

        System.out.println(evenOrLarge.test(101));//true: false || true = true
        System.out.println(evenOrLarge1.test(101));//false: true && false = false
        System.out.println(equals.test(5));//false



    }
}
