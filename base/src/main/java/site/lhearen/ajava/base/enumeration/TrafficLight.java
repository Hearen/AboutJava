package site.lhearen.ajava.base.enumeration;

import static site.lhearen.ajava.base.enumeration.Signal.*;
import static java.lang.System.out;



public class TrafficLight {
    Signal color = RED;

    public static void main(String[] args) {
        TrafficLight t = new TrafficLight();
        for (int i = 0; i < 7; i++) {
            out.println(t);
            t.change();
        }
    }

    public void change() {
        switch (color) {
            // Note that you don't have to say Signal.RED
            // in the case statement:
            case RED:
                color = GREEN;
                break;
            case GREEN:
                color = YELLOW;
                break;
            case YELLOW:
                color = RED;
                break;
        }
    }

    public String toString() {
        return "The traffic light is " + color;
    }
}

