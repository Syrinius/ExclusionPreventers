package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashSet;
import java.util.Set;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.gameObject.AOEVisual;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.gameObject.Projectile;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.hud.TowerImprovementPopOutMenu;
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
    public MapPosition[] room  = new MapPosition[4];
    private int workers = 0;

    public int getWorkers() {
        return workers;
    }

    public TowerLocation(MapPosition position, Map map) {
        super(EmptyTower.getInstance().getTexture(0), position, map);
        setTower(EmptyTower.getInstance(), 0, true);
        Events.AddListener(this);
        createRoom(position);
    }

    public int getRefund() {
        return tower.getRefund(level);
    }

    public void refundWorkers() {
        GameLogic.addWorkers(workers);
        workers = 0;
    }

    public void addWorkers(int amount) {
        GameLogic.addWorkers(- amount);
        workers += amount;
    }

    public int getLevel() {
        return level;
    }

    public Tower getTower() {
        return tower;
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

    public MapPosition getCheeringSpawnPosition() {
        MapPosition position = new MapPosition(0, 0);
        do {
            position.set((float)(Math.random() * (room[0].x - room[1].x) + room[1].x),
                        (float)(Math.random() * (room[0].y - room[1].y) + room[1].y));
        } while (position.x <= room[2].x && position.x >= room[3].x && position.y <= room[2].y && position.y >= room[3].y);
        return position;
    }

    public void createRoom(MapPosition position) {
        MapPosition currentPosition = position.clone();
        do {
            currentPosition.add(1, 0);
        } while (map.getTileType(currentPosition).equals("room"));
        currentPosition.add(-1, 0);
        do {
            currentPosition.add(0, 1);
        } while (map.getTileType(currentPosition).equals("room"));
        currentPosition.add(0.7f, -0.3f);
        room[0] = currentPosition;

        currentPosition = position.clone();
        do {
            currentPosition.add(-1, 0);
        } while (map.getTileType(currentPosition).equals("room"));
        currentPosition.add(1, 0);
        do {
            currentPosition.add(0, -1);
        } while (map.getTileType(currentPosition).equals("room"));
        currentPosition.add(0.3f, 1.3f);
        room[1] = currentPosition;

        currentPosition = position.clone();
        currentPosition.add(1, 1);
        room[2] = currentPosition;

        currentPosition = position.clone();
        currentPosition.add(-1, -1);
        room[3] = currentPosition;
    }

    public float getRange() {
        return tower.getRange(level);
    }

    public int getParticipation() {
        return tower.getParticipation(level, workers);
    }

    @Override
    public TYPE getType() {
        return TYPE.TOWER;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if (cooldown > 0) cooldown = Math.max(cooldown - deltaTime, 0);
        else if (cooldown == 0) cooldown = tower.act(this, level, workers, currentTargets);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if(IsInside(position.ToMapPosition())){
            if (tower == EmptyTower.getInstance()){
                TowerSelectionPopOutMenu.GetInstance(map.getHudElementManager(), new MenuPosition(
                        position.x > ScreenPosition.WIDTH / 2 ? MenuPosition.WIDTH / 4f : MenuPosition.WIDTH * 3 / 4f, MenuPosition.HEIGHT / 2f),
                        this);
            } else {
                TowerImprovementPopOutMenu.GetInstance(map.getHudElementManager(), new MenuPosition(
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

    public AOEVisual spawnAOEVisual() {
        return new AOEVisual(new MapPosition(getX(), getY()), map);
    }

    public Projectile spawnProjectile(Texture texture, StudentContainer target) {
        return new Projectile(texture, new MapPosition(getX(), getY()), map, this, target);
    }
}
