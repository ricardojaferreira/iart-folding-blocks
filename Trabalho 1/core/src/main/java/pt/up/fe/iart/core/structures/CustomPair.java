package pt.up.fe.iart.core.structures;

import java.util.Objects;

public class CustomPair<A, B> {

    public final A fst;
    public final B snd;

    public CustomPair(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }

    /**
     *
     * @return
     */
    public String toString() {
        return "Pair[" + fst + "," + snd + "]";
    }

    /**
     *
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        return
                other instanceof CustomPair<?, ?>
                        && Objects.equals(fst, ((CustomPair<?, ?>) other).fst)
                        && Objects.equals(snd, ((CustomPair<?, ?>) other).snd);
    }

    /**
     *
     * @return
     */
    public int hashCode() {
        if (fst == null) {
            return (snd == null) ? 0 : snd.hashCode() + 1;
        } else if (snd == null) {
            return fst.hashCode() + 2;
        } else {
            return fst.hashCode() * 17 + snd.hashCode();
        }
    }

    /**
     *
     * @param a
     * @param b
     * @param <A>
     * @param <B>
     * @return
     */
    public static <A, B> CustomPair<A, B> of(A a, B b) {
        return new CustomPair<>(a, b);
    }
}
