import java.util.*;

import static java.lang.Math.*;

public class Polynomial {
    /** Список членов многочлена. */
    private final List<Integer> terms = new ArrayList<>();

    /**
     * Создаёт полином из заданного списка одночленов.
     * @param terms список членов полинома
     * @see Polynomial
     * @throws IllegalArgumentException - если список одночленов пуст
     */
    public Polynomial(List<Integer> terms) {
        if (terms.isEmpty()) throw new IllegalArgumentException("Wrong list of terms");
        this.terms.addAll(terms);
    }

    /**
     * Создаёт полином заданной степени с нулевыми коэффициентами.
     * @param highestDegree степень полинома
     * @see  Polynomial
     * @throws IllegalArgumentException если задана отрицательная степень
     */
    public Polynomial(int highestDegree) {
        if (highestDegree < 0) throw new IllegalArgumentException("Wrong degree");
        for (int i = 0; i <= highestDegree; i++) terms.add(0);
    }

    /**
     * Возвращает степень данного полинома.
     * @return степень данного полинома
     */
    public int getDegree() {
        return terms.size() - 1;
    }

    /**
     * Возвращает список членов данного полинома.
     * @return список членов данного полинома
     */
    public List<Integer> getTerms() {
        return terms;
    }

    /**
     * Возвращает значение полинома при заданном целом значении переменной.
     * @param x заданное значение переменной
     * @return значение полинома при заданном целом значении переменной
     */
    public int value(int x) {
        int result = 0;
        for (int i = 0; i < terms.size(); i++) {
            result += terms.get(i) * pow(x, i);
        }
        return result;
    }

    /**
     * Возвращает полином, равный сумме этого и заданного полиномов.
     * @param other прибавляемый полином
     * @return полином, равный сумме этого и заданного полиномов
     */
    public Polynomial plus(Polynomial other) {
        int maxPolynomialDegree = (max(getDegree(), other.getDegree()));
        int minPolynomialDegree = (min(getDegree(), other.getDegree()));
        Polynomial result = new Polynomial(maxPolynomialDegree);
        for (int i = 0; i <= minPolynomialDegree; i++) {
            result.terms.set(i, terms.get(i) + other.terms.get(i));
        }
        if (terms.size() > other.terms.size())
            for (int i = minPolynomialDegree + 1; i <= maxPolynomialDegree; i++) {
                result.terms.set(i, terms.get(i));
            }
        else
            for (int i = minPolynomialDegree + 1; i <= maxPolynomialDegree; i++) {
                result.terms.set(i, other.terms.get(i));
            }
        while (result.terms.get(maxPolynomialDegree).equals(0)) {
            result.terms.remove(maxPolynomialDegree);
            maxPolynomialDegree--;
            if (maxPolynomialDegree == 0) break;
        }
        return new Polynomial(result.terms);
    }

    /**
     * Возвращает полином, равный разности этого и заданного полиномов.
     * @param other вычитаемый многочлен
     * @return полином, равный разности этого и заданного полиномов
     */
    public Polynomial minus(Polynomial other) {
        Polynomial negativeOther = new Polynomial(other.getDegree());
        for (int i = 0; i < other.terms.size(); i++) {
            negativeOther.terms.set(i, -other.terms.get(i));
        }
        return this.plus(negativeOther);
    }

    /**
     * Возвращает полином, равный произведению этого и заданного полиномов.
     * @param other полином на который умножается этот полином
     * @return полином, равный произведению этого и заданного полиномов
     */
    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial(getDegree() + other.getDegree());
        for (int i = 0; i <= getDegree(); i++) {
            for (int k = 0; k <= other.getDegree(); k++) {
                result.terms.set(i + k, result.terms.get(i + k) + terms.get(i) * other.terms.get(k));
            }
        }
        return result;
    }

    /**
     * Возвращает полином, который получен из данного путём удаления всех членов со степенью выше заданной.
     * @param subHighestDegree степень получаемого полинома
     * @return полином, который получен из данного путём удаления всех членов со степенью выше заданной
     */
    public Polynomial subPolynomial(int subHighestDegree) {
        Polynomial result = new Polynomial(terms);
        while (result.getDegree() > subHighestDegree) {
            result.terms.remove(result.getDegree());
        }
        return new Polynomial(result.terms);
    }

    /**
     * Возвращает массив полиномов, содержащий результат и остаток от деления данного полинома на заданный.
     * @param other полином - делитель
     * @return массив полиномов, содержащий результат и остаток от деления данного полинома на заданный
     */
    private Polynomial[] divide(Polynomial other) {
        Polynomial result = new Polynomial(getDegree() - other.getDegree());
        Polynomial remainder = new Polynomial(terms);
        for (int i = result.getDegree(); i >= 0; i--) {
            if (remainder.terms.get(remainder.getDegree()) % other.terms.get(other.getDegree()) != 0)
                throw new IllegalArgumentException("The result can only have integer coefficients");
            result.terms.set(i, remainder.terms.get(remainder.getDegree()) / other.terms.get(other.getDegree()));
            remainder = remainder.minus(other.multiply(result.subPolynomial(i)));
        }
        return new Polynomial[]{result, remainder};
    }

    /**
     * Возвращает частное от деления этого полинома на заданный.
     * @param other полином - делитель
     * @return частное от деления этого полинома на заданный
     */
    public Polynomial resultOfTheDivision(Polynomial other) {
        return divide(other)[0];
    }

    /**
     * Возвращает остаток от деления этого полинома на заданный.
     * @param other полином - делитель
     * @return остаток от деления этого полинома на заданный
     */
    public Polynomial remainderOfTheDivision(Polynomial other) {
        return divide(other)[1];
    }

    /**
     * Возвращает true если заданный объект равен этому полиному.
     * {@inheritDoc}
     * @param obj объект, который будет сравниваться с этим полиномом
     * @return true если заданный объект равен этому полиному
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Polynomial) {
            Polynomial other = (Polynomial) obj;
            return getDegree() == other.getDegree() && other.terms.containsAll(terms) && terms.containsAll(other.terms);
        }
        return false;
    }

    /**
     * Возвращает строковое представление этого полинома.
     * {@inheritDoc}
     * @return строковое представление этого полинома
     */
    @Override
    public String toString() {
        Map<Character, Character> superscriptNumbers = new HashMap<>();
        superscriptNumbers.put('0', '\u2070');
        superscriptNumbers.put('1', '\u00b9');
        superscriptNumbers.put('2', '\u00b2');
        superscriptNumbers.put('3', '\u00b3');
        superscriptNumbers.put('4', '\u2074');
        superscriptNumbers.put('5', '\u2075');
        superscriptNumbers.put('6', '\u2076');
        superscriptNumbers.put('7', '\u2077');
        superscriptNumbers.put('8', '\u2078');
        superscriptNumbers.put('9', '\u2079');
        StringBuilder result = new StringBuilder();
        for (int i = getDegree(); i >= 0; i--) {
            if (terms.get(i).equals(0) && (i != 0 || result.length() != 0)) continue;
            StringBuilder termDegree = new StringBuilder();
            for (Character element : String.valueOf(i).toCharArray()) {
                termDegree.append(superscriptNumbers.get(element));
            }
            if (result.length() > 0 && terms.get(i) > 0) result.append("+");
            if (abs(terms.get(i)) != 1 || i == 0) result.append(terms.get(i));
            else if (terms.get(i).equals(-1)) result.append("-");
            if (i != 0) result.append("x");
            if (i > 1) result.append(termDegree);
        }
        return result.toString();
    }

    /**
     * Возвращает хэш-код данного полинома.
     * {@inheritDoc}
     * хэш-код данного полинома
     */
    @Override
    public int hashCode() {
        int result = getDegree();
        result = result * 17 + terms.hashCode();
        return result;
    }
}