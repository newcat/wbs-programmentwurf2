import processing.core.PApplet;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please specify the input file (defaults to 'S_004_Daten.csv'): ");
        String file = scanner.nextLine();
        if (file.isEmpty()) {
            file = "S_004_Daten.csv";
        }

        // TODO: Sanitize user input
        System.out.println("Please specify the starting x coordinate: ");
        int startX = scanner.nextInt();
        System.out.println("Please specify the starting y coordinate: ");
        int startY = scanner.nextInt();
        System.out.println("Please specify the target x coordinate: ");
        int endX = scanner.nextInt();
        System.out.println("Please specify the target y coordinate: ");
        int endY = scanner.nextInt();

        String[] processingArgs = {"WbsSketch"};
        WbsSketch sketch = new WbsSketch();
        sketch.initialize(file, new Vector(startX, startY), new Vector(endX, endY));
        PApplet.runSketch(processingArgs, sketch);

    }

}
