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

    public AStar(Map map, Vector start, Vector end) {
        this.map = map;
        this.start = start;
        this.end = end;
        openList.add(new ListElement(start, null, 0, g(start), 0));
    }

    public int step() {

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

            // if the successor is already on the closed list, skip this successor
            if (containsField(closedList, successor.end)) {
                continue;
            }

            // check if this node would increase the amount of water crossing
            // over the maximum amount
            final int newWaterCrossings;
            if (isWater(successor.end)) {
                if (el.waterCrossings + 1 > MAX_WATER_CROSSINGS) {
                    continue;
                } else {
                    newWaterCrossings = el.waterCrossings + 1;
                }
            } else {
                newWaterCrossings = el.waterCrossings;
            }

            double expectedValue = g(successor.end) + el.value + successor.value;

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
        return Math.sqrt(Math.pow(Math.abs(start.x - node.x), 2) + Math.pow(Math.abs(start.y - node.y), 2));
    }

    public void draw(PApplet sketch, TileDrawer drawer) {

        // TODO
        // draw elements in open list
        // openList.forEach((el) -> drawer.drawTile(el.node, 255, 255, 255, 10));

        // draw elements in closed list
        // closedList.forEach((el) -> drawer.drawTile(el.node, 0, 0, 0, 128));

    }

    public Stack<ListElement> getPath() {
        Stack<ListElement> stack = new Stack<>();

        // find the target node
        stack.push(getField(closedList, end));

        while (stack.peek().parentNode != null) {
            stack.push(getField(closedList, stack.peek().parentNode));
        }

        return stack;
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
