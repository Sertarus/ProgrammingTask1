import java.util.*;

public class Polynomial {
    //Поля
    private final int highestDegree;

    private final List<Integer> terms = new ArrayList<>();

    //Конструкторы
    public Polynomial(List<Integer> terms) {
        if (terms.isEmpty()) throw new IllegalArgumentException("Wrong list of terms");
        this.terms.addAll(terms);
        this.highestDegree = terms.size() - 1;
    }

    public Polynomial(int highestDegree) {
        if (highestDegree < 0) throw new IllegalArgumentException("Wrong degree");
        this.highestDegree = highestDegree;
        for (int i = 0; i <= this.highestDegree; i++) terms.add(0);
    }

    //Геттеры
    public Integer getDegree() {
        return highestDegree;
    }

    public List<Integer> getTerms() {
        return terms;
    }

    //Вычисление значения при данном х
    public int value(int x) {
        int result = 0;
        for (int i = 0; i < terms.size(); i++) {
            result += terms.get(i) * Math.pow(x, i);
        }
        return result;
    }

    //Сложение
    public Polynomial plus(Polynomial other) {
        int maxPolynomialDegree = (Math.max(terms.size(), other.terms.size())) - 1;
        int minPolynomialDegree = (Math.min(terms.size(), other.terms.size())) - 1;
        Polynomial result = new Polynomial(maxPolynomialDegree);
        for (int i = 0; i <= minPolynomialDegree; i++) {
            result.terms.set(i, terms.get(i) + other.terms.get(i));
        }
        for (int i = minPolynomialDegree + 1; i <= maxPolynomialDegree; i++) {
            if (terms.size() > other.terms.size()) result.terms.set(i, terms.get(i));
            else result.terms.set(i, other.terms.get(i));
        }
        while (result.terms.get(maxPolynomialDegree).equals(0)) {
            result.terms.remove(maxPolynomialDegree);
            maxPolynomialDegree--;
            if (maxPolynomialDegree == 0) break;
        }
        return new Polynomial(result.terms);
    }

    //Вычитание
    public Polynomial minus(Polynomial other) {
        Polynomial negativeOther = new Polynomial(other.highestDegree);
        for (int i = 0; i < other.terms.size(); i++) {
            negativeOther.terms.set(i, -other.terms.get(i));
        }
        return this.plus(negativeOther);
    }

    //Умножение
    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial(highestDegree + other.highestDegree);
        for (int i = 0; i <= highestDegree; i++) {
            for (int k = 0; k <= other.highestDegree; k++) {
                result.terms.set(i + k, result.terms.get(i + k) + terms.get(i) * other.terms.get(k));
            }
        }
        return result;
    }

    public Polynomial subPolynomial(int subHighestDegree) {
        Polynomial result = new Polynomial(terms);
        while (result.terms.size() > subHighestDegree + 1) {
            result.terms.remove(result.terms.size() - 1);
        }
        return new Polynomial(result.terms);
    }

    //Деление
    public Polynomial divide(Polynomial other) {
        if (highestDegree < other.highestDegree) throw
                new IllegalArgumentException("The degree of a divisible can not be less than the degree of a divisor");
        Polynomial result = new Polynomial(highestDegree - other.highestDegree);
        Polynomial remainder = new Polynomial(terms);
        for (int i = result.highestDegree; i >= 0; i--) {
            if (remainder.terms.get(remainder.highestDegree) % other.terms.get(other.highestDegree) != 0)
                throw new IllegalArgumentException("The result can only have integer coefficients");
            result.terms.set(i, remainder.terms.get(remainder.highestDegree) / other.terms.get(other.highestDegree));
            remainder = remainder.minus(other.multiply(result.subPolynomial(i)));
        }
        return result;
    }

    //Остаток
    public Polynomial remainderOfTheDivision(Polynomial other) {
        if (highestDegree < other.highestDegree) throw
                new IllegalArgumentException("The degree of a divisible can not be less than the degree of a divisor");
        Polynomial result = new Polynomial(highestDegree - other.highestDegree);
        Polynomial remainder = new Polynomial(terms);
        for (int i = result.highestDegree; i >= 0; i--) {
            if (remainder.terms.get(remainder.highestDegree) % other.terms.get(other.highestDegree) != 0)
                throw new IllegalArgumentException("The result can only have integer coefficients");
            result.terms.set(i, remainder.terms.get(remainder.highestDegree) / other.terms.get(other.highestDegree));
            remainder = remainder.minus(other.multiply(result.subPolynomial(i)));
        }
        return remainder;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Polynomial) {
            Polynomial other = (Polynomial) obj;
            return highestDegree ==
                    other.highestDegree && other.terms.containsAll(terms) && terms.containsAll(other.terms);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = terms.size() - 1; i >= 0; i--) {
            if (terms.get(i).equals(0) && (i != 0 || result.length() != 0)) continue;
            StringBuilder termDegree = new StringBuilder();
            for (Character element : String.valueOf(i).toCharArray()) {
                switch (element) {
                    case '0':
                        termDegree.append("\u2070");
                        break;
                    case '1':
                        termDegree.append("\u00b9");
                        break;
                    case '2':
                        termDegree.append("\u00b2");
                        break;
                    case '3':
                        termDegree.append("\u00b3");
                        break;
                    case '4':
                        termDegree.append("\u2074");
                        break;
                    case '5':
                        termDegree.append("\u2075");
                        break;
                    case '6':
                        termDegree.append("\u2076");
                        break;
                    case '7':
                        termDegree.append("\u2077");
                        break;
                    case '8':
                        termDegree.append("\u2078");
                        break;
                    case '9':
                        termDegree.append("\u2079");
                        break;
                }
            }
            if (result.length() > 0 && terms.get(i) > 0) result.append("+");
            if (Math.abs(terms.get(i)) != 1 || i == 0) result.append(terms.get(i));
            else if (terms.get(i).equals(-1)) result.append("-");
            if (i != 0) result.append("x");
            if (i > 1) result.append(termDegree);
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        int result = highestDegree;
        result = result * 17 + terms.hashCode();
        return result;
    }
}