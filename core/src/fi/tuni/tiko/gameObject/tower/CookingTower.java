package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class CookingTower implements Tower {

    private static CookingTower instance;
    private static final Texture texture = new Texture("cooking_tower.png");
    private static final Texture projectileTexture = new Texture("wip.png");
    private static final float COOLDOWN = 10;

    public static CookingTower getInstance() {
        if (instance == null) instance = new CookingTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, Set<StudentContainer> currentTargets) {
        for (StudentContainer currentTarget : currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            return COOLDOWN;
        }
        return 0;
    }

    @Override
    public Texture getTexture(int level) {
        return texture;
    }

    @Override
    public float getRange(int level) {
        return 6;
    }
}
