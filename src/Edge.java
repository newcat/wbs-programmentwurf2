public class Edge {

    public Vector end;
    public int value;

    public Edge(Vector end, int value) {
        this.end = end;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + end.x + "|" + end.y + ")";
    }

}