package fi.tuni.tiko.eventSystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import fi.tuni.tiko.hud.HudElement;
import fi.tuni.tiko.ImprovedSprite;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;

/**
 * Simple on-screen joystick for testing stuff
 * won't be in the final game
 */
public class Joystick implements HudElement, TouchListener {
    ImprovedSprite background = new ImprovedSprite(new Texture("joystick_background.png"));
    ImprovedSprite knob = new ImprovedSprite(new Texture("joystick_knob.png"));
    MenuPosition position;

    final List<JoystickListener> joystickListeners = new ArrayList<>();

    public void addJoystickListener(JoystickListener listener) { joystickListeners.add(listener); }

    public Joystick(MenuPosition position) {
        this.position = position;
        background.setScale(50/256f);
        background.setPosition(position.x, position.y);
        knob.setScale(50/256f);
        knob.setPosition(position.x, position.y);
        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        background.draw(batch);
        knob.draw(batch);
    }

    @Override
    public void dispose() {
        Events.RemoveListener(this);
    }

    @Override
    public void onTouchDown(ScreenPosition position, int pointer) {
        onTouchDragged(position, pointer);
    }

    @Override
    public void onTouchUp(ScreenPosition position, int pointer) {
        knob.setPosition(background.getX(), background.getY());
    }

    @Override
    public void onTouchDragged(ScreenPosition screenPosition, int pointer) {
        MenuPosition point = screenPosition.ToMenuPosition();
        if (point.dst2(position) <= 25*25) {
            knob.setPosition(point.x, point.y);
            MenuPosition toReturn = new MenuPosition(point.x - position.x, point.y - position.y);
            for (JoystickListener listener : joystickListeners) {
                listener.joystickMoved(toReturn);
            }
        }
    }
}
