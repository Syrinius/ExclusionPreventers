package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.map.Map;

/**
 * Projectiles always have a target student that they move towards
 * Handles checking if it hit target and calls the tower the projectile came from, to handle adding participation to the student
 */
public class Projectile extends GameObjectSprite {

    private final fi.tuni.tiko.gameObject.tower.TowerLocation tower;
    private final fi.tuni.tiko.gameObject.student.StudentContainer target;
    private final float SPEED = 3f;
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
        float distance = target.getPosition().sub(getPosition()).len();
        float alpha = Math.min((SPEED * deltaTime)/distance, 1);
        Vector2 newPosition = getPosition().lerp(target.getPosition(), alpha);
        setPosition(newPosition.x, newPosition.y);
        if (alpha == 1) {
            hitTarget();
        }
    }

    public void hitTarget() {
        target.addParticipation(tower);
        destroy();
    }
}
