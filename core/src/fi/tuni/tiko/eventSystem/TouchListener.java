package fi.tuni.tiko.eventSystem;

import fi.tuni.tiko.coordinateSystem.ScreenPosition;

/**
 * All elements that use player touch input should implement this
 * Listener for player touch inputs
 * Includes priority system so overlapping elements can consume inputs so only the correct element gets the input
 * Return true to consume the touch event, false otherwise
 */
public interface TouchListener {

    boolean onTouchDown(ScreenPosition position, int pointer);
    boolean onTouchUp(ScreenPosition position, int pointer);
    boolean onTouchDragged(ScreenPosition position, int pointer);
    Events.Priority getTouchListenerPriority();
}
