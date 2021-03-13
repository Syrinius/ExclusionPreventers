package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.Action;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;

public class Button extends ImprovedHudSprite implements HudElement, TouchListener {

    private Action toExecute;

    public Button(Texture texture, MenuPosition position, float size, Action toExecute) {
        super(texture, position, size);
        this.toExecute = toExecute;
        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch);
    }

    @Override
    public void dispose() {
        Events.RemoveListener(this);
    }

    @Override
    public void onTouchDown(ScreenPosition position, int pointer) {
        if (IsInside(position.ToMenuPosition())) toExecute.run();
    }

    @Override
    public void onTouchUp(ScreenPosition position, int pointer) {

    }

    @Override
    public void onTouchDragged(ScreenPosition position, int pointer) {

    }
}
