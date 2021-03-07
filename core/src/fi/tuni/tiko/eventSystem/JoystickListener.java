package fi.tuni.tiko.eventSystem;

import fi.tuni.tiko.coordinateSystem.MenuPosition;

/**
 * companion interface for the Joystick class
 * won't be in the final game
 */
public interface JoystickListener {
    void joystickMoved(MenuPosition position);
}
