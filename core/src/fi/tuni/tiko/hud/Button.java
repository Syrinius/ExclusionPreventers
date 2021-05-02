package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.TouchListener;
import fi.tuni.tiko.utilities.Action;

/**
 * Generic implementation of button to add to various menus
 * Menus should create instances of this class
 */
public class Button extends HudSprite implements TouchListener {

    final fi.tuni.tiko.utilities.Action toExecute;
    private final fi.tuni.tiko.eventSystem.Events.Priority priority;

    public Button(Texture texture, MenuPosition position, float size, fi.tuni.tiko.utilities.Action toExecute) {
        this(texture, position, size, toExecute, Events.Priority.MEDIUM);
    }

    public Button(Texture texture, MenuPosition position, float size, Action toExecute, fi.tuni.tiko.eventSystem.Events.Priority priority) {
        super(texture, position, size);
        this.priority = priority;
        this.toExecute = toExecute;
        Events.AddListener(this);
    }

    @Override
    public void dispose() {
        Events.RemoveListener(this);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (IsInside(position.ToMenuPosition())) {
            toExecute.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchUp(ScreenPosition position, int pointer) {
        return false;
    }

    @Override
    public boolean onTouchDragged(ScreenPosition position, int pointer) {
        return false;
    }

    @Override
    public fi.tuni.tiko.eventSystem.Events.Priority getTouchListenerPriority() {
        return priority;
    }
}
