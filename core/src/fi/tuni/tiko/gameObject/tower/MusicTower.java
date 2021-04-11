package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class MusicTower implements Tower {

    private static MusicTower instance;
    private static final Texture texture = new Texture("towers/note_tower.png");
    private static final Texture projectileTexture = new Texture("towers/wip.png");
    private static final float[] COOLDOWN = {8, 6, 4};
    private static final float[] RANGE = {6, 7, 8};
    private static final int[] PARTICIPATION = {3, 4, 5};
    private static final int[] COST = {30, 50, 100};

    public static MusicTower getInstance() {
        if (instance == null) instance = new MusicTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, Set<StudentContainer> currentTargets) {
        if (!currentTargets.isEmpty()) {
            for (StudentContainer currentTarget : currentTargets) {
                currentTarget.addParticipation(location);
            }
            return COOLDOWN[level - 1];
        } else return 0;
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