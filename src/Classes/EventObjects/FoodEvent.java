package Classes.EventObjects;

import Classes.Coordinate2i;

import java.util.EventObject;

public class FoodEvent extends EventObject {
    private final Coordinate2i foodCoordinate;

    public FoodEvent(Object source, Coordinate2i coordinate) {
        super(source);
        foodCoordinate = coordinate;

    }

    public Coordinate2i getFoodCoordinate() {
        return foodCoordinate;
    }
}
