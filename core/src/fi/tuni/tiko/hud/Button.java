package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.Action;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;

public class Button extends HudSprite implements HudElement, TouchListener {

    private final Action toExecute;
    private final Events.Priority priority;

    public Button(Texture texture, MenuPosition position, float size, Action toExecute) {
        this(texture, position, size, toExecute, Events.Priority.MEDIUM);
    }

    public Button(Texture texture, MenuPosition position, float size, Action toExecute, Events.Priority priority) {
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
    public Events.Priority getTouchListenerPriority() {
        return priority;
    }
}
