public class Edge {

    public int start;
    public int end;
    public int value;

    public Edge(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Edge(int start, int end, int value) {
        this(start, end);
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + start + "|" + end + ")";
    }

}