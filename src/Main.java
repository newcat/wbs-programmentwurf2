import processing.core.PApplet;

import java.text.MessageFormat;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        String[] processingArgs = {"WbsSketch"};
        WbsSketch sketch = new WbsSketch();
        Map map = new Map(15,15,".\\S_004_Daten.csv");

        AStar aStar = new AStar(map,
                new Vector(3, 5),
                new Vector(14, 14));
                /*map.coordinatesToLinear(5, 2),
                map.coordinatesToLinear(8,2));*/
        int x = 0;
        while ((x = aStar.step()) == 0);
        System.out.println(x);

        Stack<ListElement> path = aStar.getPath();
        for (ListElement el : path) {
            System.out.println(MessageFormat.format("({0},{1}): {2} | {3}",
                    el.node.x, el.node.y, el.value, el.waterCrossings));
        }

        PApplet.runSketch(processingArgs, sketch);
    }

}
