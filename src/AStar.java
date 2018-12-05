import processing.core.PApplet;

import java.util.*;

public class AStar {

    private final int MAX_WATER_CROSSINGS = 2;
    private final int WATER_FIELD_TYPE = 1;

    private ArrayDeque<ListElement> closedList = new ArrayDeque<>();
    private ArrayList<ListElement> openList = new ArrayList<>();

    private Vector start;
    private Vector end;
    private Map map;

    private int minimumWeight;

    AStar(Map map, Vector start, Vector end) {
        this.map = map;
        this.start = start;
        this.end = end;
        openList.add(new ListElement(start, null, 0, g(start), 0));
        this.minimumWeight = map.getMinimumWeight();
    }

    int step() {

        if (openList.isEmpty())
            return -1;

        // sort the elements ascending by their value
        openList.sort((el1, el2) -> Double.compare(el1.expectedValue, el2.expectedValue));

        // take the element with the lowest value, remove it from the "open" list and put it onto the "closed" list
        ListElement el = openList.remove(0);
        closedList.add(el);

        if (el.node.equals(end)) {
            // we have found a path to the target node
            return 1;
        }

        expandNode(el);
        return 0;

    }

    private void expandNode(ListElement el) {

        // get all fields connected to current node
        List<Edge> successors = map.getConnectionsFrom(el.node);

        for (Edge successor : successors) {
            Vector newNode = successor.end;
            int costToNewNode = successor.value;

            // if the successor is already on the closed list, skip this successor
            if (containsField(closedList, newNode)) {
                continue;
            }

            // check if this node would increase the amount of water crossing
            // over the maximum amount
            final int newWaterCrossings;
            if (isWater(newNode)) {
                if (el.waterCrossings + 1 > MAX_WATER_CROSSINGS) {
                    continue;
                } else {
                    newWaterCrossings = el.waterCrossings + 1;
                }
            } else {
                newWaterCrossings = el.waterCrossings;
            }

            double expectedValue = g(newNode) + el.value + successor.value;

            // check if the node is already on the open list (check for water crossings as well)
            Optional<ListElement> altEl = openList.stream()
                    .filter((x) -> x.node.equals(successor.end) && x.waterCrossings == newWaterCrossings)
                    .findFirst();

            if (!altEl.isPresent() || (altEl.get().value + g(altEl.get().node)) > expectedValue) {

                // we have found a new or better alternative -> put into open list

                // if exists, remove old item first
                altEl.ifPresent(listElement -> openList.remove(listElement));

                ListElement newEl = new ListElement(
                        successor.end, el.node, el.value + successor.value,
                        expectedValue, newWaterCrossings);
                openList.add(newEl);

            }


        }

    }

    private boolean isWater(Vector node) {
        return map.getField(node) == WATER_FIELD_TYPE;
    }

    private double g(Vector node) {
        // multiply with minimumWeight, as this is the minimal cost possible
        return Math.sqrt((end.x - node.x) * (end.x - node.x) + (end.y - node.y) * (end.y - node.y)) * minimumWeight;
    }

    void draw(PApplet sketch, TileDrawer drawer) {

        // draw elements in closed list
        closedList.forEach((el) -> {
            drawer.markTile(el.node, 255, 255, 0, 255);
            drawer.drawText(el.node, Integer.toString(el.waterCrossings));
        });

        // draw elements in open list
        openList.forEach((el) -> {
            drawer.markTile(el.node, 255, 0, 0, 255);
            drawer.drawText(el.node, Integer.toString(el.waterCrossings));
        });

    }

    ArrayDeque<ListElement> getPath() {
        ArrayDeque<ListElement> path = new ArrayDeque<>();

        // find the target node
        path.add(getField(closedList, end));

        while (path.getLast().parentNode != null) {
            path.add(getField(closedList, path.getLast().parentNode));
        }

        return path;
    }

    private boolean containsField(Iterable<ListElement> i, Vector v) {
        for (ListElement e : i) {
            if (e.node.equals(v))
                return true;
        }
        return false;
    }

    private ListElement getField(Iterable<ListElement> i, Vector v) {
        for (ListElement e : i) {
            if (e.node.equals(v))
                return e;
        }
        return null;
    }

}
