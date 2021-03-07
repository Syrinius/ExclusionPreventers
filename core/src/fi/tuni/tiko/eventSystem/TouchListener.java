package fi.tuni.tiko.eventSystem;

import fi.tuni.tiko.coordinateSystem.ScreenPosition;

public interface TouchListener {

    void onTouchDown(ScreenPosition position, int pointer);
    void onTouchUp(ScreenPosition position, int pointer);
    void onTouchDragged(ScreenPosition position, int pointer);
}
