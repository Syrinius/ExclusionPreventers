package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class SportTower implements Tower {

    private static SportTower instance;
    private static final Texture texture = new Texture("towers/sport.png");
    private static final Texture projectileTexture = new Texture("towers/wip.png");
    private static final float[] COOLDOWN = {5, 4, 3};
    private static final float[] RANGE = {6, 7, 8};
    private static final int[] PARTICIPATION = {4, 6, 8};
    private static final int[] COST = {20, 40, 80};

    public static SportTower getInstance() {
        if (instance == null) instance = new SportTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, Set<StudentContainer> currentTargets) {
        for (StudentContainer currentTarget : currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            return COOLDOWN[level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(int level) {
        return texture;
    }

    @Override
    public float getRange(int level) {
        return RANGE[level - 1];
    }

    @Override
    public int getParticipation(int level) {
        return PARTICIPATION[level -1];
    }

    @Override
    public int getCost(int level) {
        return COST[level -1];
    }
}