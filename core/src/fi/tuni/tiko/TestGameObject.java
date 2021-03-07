package fi.tuni.tiko;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;

/**
 * won't be in the final game
 */
public class TestGameObject implements GameObject, TouchListener {

    public TestGameObject() {
        Events.AddListener(this);
    }

    @Override
    public TYPE getType() {
        return TYPE.TOWER;
    }

    @Override
    public void onTick(float deltaTime) {
        HudElementManager.SetDebugText("DeltaTime: " + deltaTime);
    }

    @Override
    public void render(SpriteBatch renderer) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onTouchDown(ScreenPosition position, int pointer) {
        onTouchDragged(position, pointer);
    }

    @Override
    public void onTouchUp(ScreenPosition position, int pointer) {

    }

    @Override
    public void onTouchDragged(ScreenPosition position, int pointer) {
        HudElementManager.SetDebugText("type: " + MapManager.getSelectedMap().getTileType(position.ToMapPosition()));
    }
}
