package DataTypes;

public class Pair_4<A, B, C, D> {

    private final A first;
    private final B second;
    private final C third;
    private final D forth;

    public Pair_4(A first, B second, C third, D forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }
    public C getThird() { return third; }
    public D getForth(){return forth; }
}
