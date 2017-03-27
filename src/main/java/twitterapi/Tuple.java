package twitterapi;

public class Tuple<A, B> {

    public A a;
    public B b;

    int hash;
    final int size = 31;


    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int hashCode() {
        hash = 1;
        hash = size * hash + ((a == null) ? 0 : a.hashCode());
        hash = size * hash + ((b == null) ? 0 : b.hashCode());
        return hash;
    }

}
