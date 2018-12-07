import java.util.*;

class AStar {

    private final int MAX_WATER_CROSSINGS = 2;
    private final int WATER_FIELD_TYPE = 1;

    private ArrayList<ListElement> closedList = new ArrayList<>();
    private ArrayList<ListElement> openList = new ArrayList<>();

    private Vector end;
    private Map map;

    private int minimumWeight;

    AStar(Map map, Vector start, Vector end) {
        this.map = map;
        this.end = end;
        openList.add(new ListElement(start, null, 0, g(start), 0));
        this.minimumWeight = map.getMinimumWeight();
    }

    int step() {

        if (openList.isEmpty())
            return -1;

        // sort the elements ascending by their value
        openList.sort(Comparator.comparingDouble(el -> el.expectedValue));

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

            double expectedValue = g(newNode) + el.value + costToNewNode;

            // check if the node is already on the open list (check for water crossings as well)
            Optional<ListElement> altEl = openList.stream()
                    .filter((x) -> x.node.equals(successor.end) && x.waterCrossings == newWaterCrossings)
                    .findFirst();

            if (!altEl.isPresent() || (altEl.get().value + g(altEl.get().node)) > expectedValue) {

                // we have found a new or better alternative -> put into open list

                // if exists, remove old item first
                altEl.ifPresent(listElement -> openList.remove(listElement));

                ListElement newEl = new ListElement(
                        successor.end, el.node, el.value + costToNewNode,
                        expectedValue, newWaterCrossings);
                openList.add(newEl);

            }


        }

    }

    private boolean isWater(Vector node) {
        return map.getField(node) == WATER_FIELD_TYPE;
    }

    // heuristics function
    private double g(Vector node) {
        // multiply with minimumWeight, as this is the minimal cost possible
        return Math.sqrt((end.x - node.x) * (end.x - node.x) + (end.y - node.y) * (end.y - node.y)) * minimumWeight;
    }

    void draw(TileDrawer drawer) {

        for (int y = 0; y < map.getDimY(); y++) {
            for (int x = 0; x < map.getDimX(); x++) {

                Vector v = new Vector(x, y);
                int closedListCount = countField(closedList, v);
                int openListCount = countField(openList, v);

                if (closedListCount > 0 && openListCount > 0) {
                    drawer.markTile(v, 255, 128, 0, 255);
                    drawer.drawText(v, "" + closedListCount + "|" + openListCount);
                } else if (closedListCount > 0) {
                    drawer.markTile(v, 255, 255, 0, 255);
                    drawer.drawText(v, "" + closedListCount);
                } else if (openListCount > 0) {
                    drawer.markTile(v, 255, 0, 0, 255);
                    drawer.drawText(v, "" + openListCount);
                }

            }
        }

    }

    /**
     * This method will recreate the found path from the closed list
     * by starting at the end and working backwards to the start
     * The start is the element, which has no parent node
     *
     * @return the list elements representing the path from end to start
     */
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

    private int countField(Iterable<ListElement> i, Vector v) {
        int count = 0;
        for (ListElement e : i) {
            if (e.node.equals(v))
                count++;
        }
        return count;
    }

    private ListElement getField(Iterable<ListElement> i, Vector v) {
        for (ListElement e : i) {
            if (e.node.equals(v))
                return e;
        }
        return null;
    }

}
