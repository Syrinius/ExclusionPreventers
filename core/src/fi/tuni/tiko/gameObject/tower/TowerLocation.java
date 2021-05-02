package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

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
import fi.tuni.tiko.utilities.FancyMath;

/**
 * Listable positions where towers can be built on the map
 * OnTouch functionalities of towers
 * Calls the implementation classes of specific towers for Ontick functionalities
 */
public class TowerLocation extends GameObjectSprite implements TouchListener {

    private static final Texture placeholderTexture = new Texture("towers/empty_tower.png");
    private fi.tuni.tiko.gameObject.tower.Tower tower;
    public MapPosition[] room  = new MapPosition[4];
    private int studentsParticipated = 0;
    private TowerData towerData;

    public void addScore() {
        if (studentsParticipated != 1) {
            GameLogic.addScore(40 / (6 + studentsParticipated));
        }
    }

    public void addStudentCount(int amount) {
        studentsParticipated += amount;
        if (studentsParticipated % 6 == 0 && TowerType.SUPPORT.tower.equals(tower)) {
            GameLogic.addWorkers(1);
        }
    }

    public int getWorkers() {
        return towerData.workers;
    }

    public TowerLocation(MapPosition position, Map map) {
        super(placeholderTexture, position, map);
        setTower(fi.tuni.tiko.gameObject.tower.EmptyTower.getInstance(), 0, true);
        Events.AddListener(this);
        createRoom(position);
    }

    public int getRefund() {
        return tower.getRefund(towerData);
    }

    public void refundWorkers() {
        GameLogic.addWorkers(towerData.workers);
        towerData.workers = 0;
    }

    public void addWorkers(int amount) {
        GameLogic.addWorkers(- amount);
        towerData.workers += amount;
    }

    public int getLevel() {
        return towerData.level;
    }

    public fi.tuni.tiko.gameObject.tower.Tower getTower() {
        return tower;
    }

    public void setTower(fi.tuni.tiko.gameObject.tower.Tower toSet, boolean flushData) {
        setTower(toSet, 1, flushData);
    }

    public void setTower(Tower toSet, int level, boolean flushData) {
        tower = toSet;
        if (flushData) {
            if (towerData != null) refundWorkers();
            towerData = tower.getNewData();
        }
        towerData.level = level;
        setTexture(tower.getTexture(towerData));
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
        return tower.getRange(towerData);
    }

    public float getParticipation() {
        return tower.getParticipation(towerData);
    }

    @Override
    public TYPE getType() {
        return TYPE.TOWER;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if (towerData.cooldown > 0) towerData.cooldown = Math.max(towerData.cooldown - deltaTime, 0);
        else if (towerData.cooldown == 0) towerData.cooldown = tower.act(this, towerData);
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

    public void checkTarget(StudentContainer target) {
        if(!FancyMath.isInside(getX(), target.getTileCenterX(), getY(), target.getTileCenterY(), getRange())) {
            towerData.currentTargets.remove(target);
        } else addTarget(target);
        tower.checkTarget(this, target, towerData);
    }

    public void addTarget(StudentContainer target) {
        towerData.currentTargets.add(target);
    }

    public void removeTarget(StudentContainer target) {
        towerData.currentTargets.remove(target);
        tower.removeTarget(target, towerData);
    }

    public AOEVisual spawnAOEVisual() {
        return new AOEVisual(new MapPosition(getX(), getY()), map);
    }

    public Projectile spawnProjectile(Texture texture, StudentContainer target) {
        return new Projectile(texture, new MapPosition(getX(), getY()), map, this, target);
    }
}
