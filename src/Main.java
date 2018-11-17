import processing.core.PApplet;

public class Main {

    public static void main(String[] args) {
        String[] processingArgs = {"WbsSketch"};
        WbsSketch mySketch = new WbsSketch();
        Map map = new Map(15, 15, "C:\\Daten\\DHBW\\5. Semester\\Wissensbasierte Systeme\\S004\\S_004_Daten.csv");
        PApplet.runSketch(processingArgs, mySketch);
    }

}
