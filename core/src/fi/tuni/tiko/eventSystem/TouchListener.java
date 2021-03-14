package fi.tuni.tiko.eventSystem;

import fi.tuni.tiko.coordinateSystem.ScreenPosition;

public interface TouchListener {

    boolean onTouchDown(ScreenPosition position, int pointer);
    boolean onTouchUp(ScreenPosition position, int pointer);
    boolean onTouchDragged(ScreenPosition position, int pointer);
    Events.Priority getTouchListenerPriority();
}
