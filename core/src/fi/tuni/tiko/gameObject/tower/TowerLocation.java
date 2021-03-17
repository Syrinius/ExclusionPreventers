package fi.tuni.tiko.gameObject.tower;

import fi.tuni.tiko.gameObject.GameObject;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.Map;
import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;
import fi.tuni.tiko.hud.TowerSelectionPopOutMenu;

/**
 * Listable positions where towers can be built on the map
 * OnTouch functionalities of towers
 * Calls the implementation classes of specific towers for Ontick functionalities
 */
public class TowerLocation extends GameObjectSprite implements GameObject, TouchListener {

    private Tower tower;

    public TowerLocation(MapPosition position, Map map) {
        super(EmptyTower.getInstance().getTexture(), position, map);
        tower = EmptyTower.getInstance();
        Events.AddListener(this);
    }

    public void setTower(Tower toSet) {
        tower = toSet;
        setTexture(tower.getTexture());
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
        if(IsInside(position.ToMapPosition())){
            if (tower == EmptyTower.getInstance()){
                TowerSelectionPopOutMenu.GetInstance(map.getHudElementManager(), new MenuPosition(
                        position.x > ScreenPosition.WIDTH / 2 ? MenuPosition.WIDTH / 4f : MenuPosition.WIDTH * 3 / 4f, MenuPosition.HEIGHT / 2f),
                        this);
            }
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
        return Events.Priority.LOW;
    }

    @Override
    public void destroy() {
        super.destroy();
        Events.RemoveListener(this);
    }
}
