public class Edge {

    Vector end;
    int value;

    Edge(Vector end, int value) {
        this.end = end;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + end.x + "|" + end.y + ")";
    }

}