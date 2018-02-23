import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialTest {
    @Test
    public void getters() {
        Polynomial p = new Polynomial(Arrays.asList(1, 2, 3));
        assertEquals(java.util.Optional.of(2), java.util.Optional.of(p.getDegree()));
        assertEquals(Arrays.asList(1, 2, 3), p.getTerms());
    }

    @Test
    public void wrongTerms() {
        try {
            new Polynomial(-1);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void equals() {
        assertEquals(new Polynomial(2), new Polynomial(2));
        assertEquals(new Polynomial(Arrays.asList(1, 2, 3)), new Polynomial(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void value() {
        assertEquals(17, new Polynomial(Arrays.asList(1, 2, 3)).value(2));
    }

    @Test
    public void plus() {
        assertEquals(new Polynomial(Arrays.asList(2, 4, -2, 8)),
                new Polynomial(Arrays.asList(1, 2, 3)).plus(new Polynomial(Arrays.asList(1, 2, -5, 8))));
    }

    @Test
    public void minus() {
        assertEquals(new Polynomial(Arrays.asList(0, 0, 8, -8)),
                new Polynomial(Arrays.asList(1, 2, 3)).minus(new Polynomial(Arrays.asList(1, 2, -5, 8))));
    }

    @Test
    public void multiply() {
        assertEquals(new Polynomial(Arrays.asList(1, 4, 2, 4, 1, 24)),
                new Polynomial(Arrays.asList(1, 2, 3)).multiply(new Polynomial(Arrays.asList(1, 2, -5, 8))));
    }

    @Test
    public void divide() {
        assertEquals(new Polynomial(Arrays.asList(4, -3, 1, 2)),
                new Polynomial(Arrays.asList(5, -2, 25, -12, 3, 10)).divide(new Polynomial(Arrays.asList(2, -1, 5))));
    }

    @Test
    public void remainderOfTheDivision() {
        assertEquals(new Polynomial(Arrays.asList(-3, 8)),
                new Polynomial(Arrays.asList(5, -2, 25, -12, 3, 10))
                        .remainderOfTheDivision(new Polynomial(Arrays.asList(2, -1, 5))));
    }
}
