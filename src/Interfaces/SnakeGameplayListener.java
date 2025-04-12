package Interfaces;

import Classes.EventObjects.CollisionEvent;
import Classes.EventObjects.FoodEvent;
import Classes.EventObjects.MoveEvent;

public interface SnakeGameplayListener {
    void consumeMoveEvent(MoveEvent e);

    void consumeFoodEvent(FoodEvent e);

    void consumeCollisionEvent(CollisionEvent e);
}
