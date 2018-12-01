import processing.core.PApplet;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class AStar {

    private class ListElement {
        int node;
        int parentNode;
        int value;
        ListElement(int node, int parentNode, int value) {
            this.node = node;
            this.parentNode = parentNode;
            this.value = value;
        }
    }

    private ArrayDeque<ListElement> closedList = new ArrayDeque<>();
    private ArrayList<ListElement> openList = new ArrayList<>();
    private ArrayList<ListElement> path = new ArrayList<>();
    private boolean finished = false;

    private int start;
    private int end;
    private Map map;

    public AStar(Map map, int start, int end) {
        this.map = map;
        this.start = start;
        this.end = end;
        openList.add(new ListElement(start, -1, 0));
    }

    public void step() {

        if (openList.isEmpty())
            return;

        // sort the elements ascending by their value
        openList.sort((el1, el2) -> el2.value - el1.value);

        // take the element with the lowest value, remove it from the "open" list and put it onto the "closed" list
        ListElement el = openList.remove(0);
        if (el.node == end) {
            // we have found a path to the target node
            finished = true;
            return;
        }

        int costCheapestPath = 0;
        for(ListElement l : path){
            costCheapestPath += l.value;
        }

        closedList.push(el);
        List<Edge> edges = map.getConnectionsFrom(el.node);
        for (Edge e : edges) {
            if (!containsField(openList, e.end) && !containsField(closedList, e.end)) {
                openList.add(new ListElement(e.end,el.node,e.value));
                path.add(new ListElement(e.end, el.node,e.value));
            }
            if(containsField(openList,e.end) | containsField(closedList,e.end)){ // && (costCheapestPath + e.value < )
                path.add(new ListElement(e.end, el.node,e.value));
                if(containsField(closedList,e.end)){
                    openList.add(new ListElement(e.end,el.node,e.value));
                    closedList.remove(e.end);
                }
            }
        }

    }

    public void draw(PApplet sketch, TileDrawer drawer) {

        // draw elements in open list
        openList.forEach((el) -> drawer.drawTile(el.node, 255, 255, 255, 10));

        // draw elements in closed list
        closedList.forEach((el) -> drawer.drawTile(el.node, 0, 0, 0, 128));

    }

    public boolean getFinished() {
        return finished;
    }

    private boolean containsField(Iterable<ListElement> i, int x) {
        for (ListElement e : i) {
            if (e.node == x)
                return true;
        }
        return false;
    }

}
