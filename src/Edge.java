public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;
    private int betweenness;

    Edge(int from, int to, double power) {
        v = from;
        w = to;
        weight = power;
        betweenness = 0;
    }

    public int from() {
        return v;
    }

    public int to(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        throw new IllegalArgumentException("Illegal endpoint");
    }

    public double weight() {
        return weight;
    }


    @Override
    public int compareTo(Edge o) {
        return (this.weight > o.weight()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(v) + ", " + Integer.toString(w) + ", " + Double.toString(weight) + ", " + Integer.toString(betweenness) + ")";
    }
}
