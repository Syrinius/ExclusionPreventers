package fi.tuni.tiko;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;

public class TowerLocation extends GameObjectSprite implements GameObject, TouchListener {

    Tower tower;

    public TowerLocation(MapPosition position, Map map) {
        super(EmptyTower.getInstance().getTexture(), position, map);
        tower = EmptyTower.getInstance();
    }

    @Override
    public TYPE getType() {
        return TYPE.TOWER;
    }

    @Override
    public void onTick(float deltaTime) {
        tower.tick(this);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
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
        return Events.Priority.LOW;
    }
}
