package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashSet;
import java.util.Set;

import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.gameObject.Projectile;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.map.Map;
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
public class TowerLocation extends GameObjectSprite implements TouchListener {

    private Tower tower;
    private Set<StudentContainer> currentTargets;
    private int level = 0;
    private float cooldown = 0;

    public TowerLocation(MapPosition position, Map map) {
        super(EmptyTower.getInstance().getTexture(0), position, map);
        setTower(EmptyTower.getInstance(), 0, true);
        Events.AddListener(this);
    }

    public void setTower(Tower toSet, boolean flushData) {
        setTower(toSet, 1, flushData);
    }

    public void setTower(Tower toSet, int level, boolean flushData) {
        tower = toSet;
        this.level = level;
        setTexture(tower.getTexture(level));
        if (flushData) {
            currentTargets = new HashSet<>();
            cooldown = 0;
        }
    }

    public float getRange() {
        return tower.getRange(level);
    }

    public int getParticipation() {
        return tower.getParticipation(level);
    }

    @Override
    public TYPE getType() {
        return TYPE.TOWER;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if (cooldown > 0) cooldown = Math.max(cooldown - deltaTime, 0);
        else if (cooldown == 0) cooldown = tower.act(this, level, currentTargets);
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

    public void addTarget(StudentContainer studentContainer) {
        currentTargets.add(studentContainer);
    }

    public void removeTarget(StudentContainer studentContainer) {
        currentTargets.remove(studentContainer);
    }

    public Projectile spawnProjectile(Texture texture, StudentContainer target) {
        return new Projectile(texture, new MapPosition(getX(), getY()), map, this, target);
    }
}
