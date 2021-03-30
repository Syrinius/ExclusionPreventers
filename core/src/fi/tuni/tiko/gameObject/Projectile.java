package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.map.Map;

public class Projectile extends GameObjectSprite {

    private final TowerLocation tower;
    private final StudentContainer target;
    private float lifetime = 10;

    public Projectile(Texture texture, MapPosition position, Map map, TowerLocation tower, StudentContainer target) {
        super(texture, position, map);
        this.tower = tower;
        this.target = target;
    }

    @Override
    public TYPE getType() {
        return TYPE.PROJECTILE;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        lifetime -= deltaTime;
        if (lifetime <= 0) destroy();
        //use target.getPosition() to get the position of the student and just getPosition() to get the position of this projectile
    }

    public void hitTarget() {
        target.addParticipation(tower);
        destroy();
    }
}
