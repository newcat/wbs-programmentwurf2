import java.util.ArrayList;

public class ListGraph {

    private class Connection {
        public int node;
        public int distance;

        public Connection(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

    }

    private ArrayList<Connection>[] list;

    @SuppressWarnings("unchecked")
    public void initialize(int nodes) {
        list = new ArrayList[nodes];
        for (int i = 0; i < nodes; i++) {
            list[i] = new ArrayList<Connection>();
        }
    }

    public void addConnection(int from, int to, int distance) {
        list[from].add(new Connection(to, distance));
    }

    public int getDistance(int from, int to) {
        for (Connection c : list[from]) {
            if (c.node == to)
                return c.distance;
        }
        return -1;
    }

    public int getNodeCount() {
        return list.length;
    }

    public ArrayList<Edge> getEdgesFrom(int from) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Connection c : list[from]) {
            edges.add(new Edge(from, c.node, c.distance));
        }
        return edges;
    }

    public ArrayList<Edge> getEdgesTo(int to) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int from = 0; from < list.length; from++) {
            for (Connection c : list[from]) {
                if (c.node == to) {
                    edges.add(new Edge(from, to, c.distance));
                }
            }
        }
        return edges;
    }

}