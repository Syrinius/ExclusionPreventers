package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class AtkTower implements Tower {

    private static AtkTower instance;
    private static final Texture texture = new Texture("towers/atk.png");
    private static final Texture projectileTexture = new Texture("towers/wip.png");
    private static final float COOLDOWN = 5;
    private static final float RANGE = 6;

    public static AtkTower getInstance() {
        if (instance == null) instance = new AtkTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, Set<StudentContainer> currentTargets) {
        for (StudentContainer currentTarget : currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            switch (level) {
                case 1:
                    return COOLDOWN;
                case 2:
                    return COOLDOWN * 0.8f;
                case 3:
                    return COOLDOWN * 0.6f;
            }
        }
        return 0;
    }

    @Override
    public Texture getTexture(int level) {
        return texture;
    }

    @Override
    public float getRange(int level) {
        switch (level) {
            case 1:
                return RANGE;
            case 2:
                return RANGE * 1.25f;
            case 3:
                return RANGE * 1.5f;
        }
        return 0;
    }

    @Override
    public int getParticipation(int level) {
        switch (level) {
            case 1:
                return 4;
            case 2:
                return 6;
            case 3:
                return 8;
        }
        return 0;
    }
}
