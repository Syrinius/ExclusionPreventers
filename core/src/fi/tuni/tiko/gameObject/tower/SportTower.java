package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class SportTower implements Tower {

    private static SportTower instance;
    private static final Texture texture = new Texture("towers/sport.png");
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {4, 3, 2};
    private static final float[] RANGE = {5, 6, 7};
    private static final int[] PARTICIPATION = {3, 4, 5};
    private static final int[] COST = {25, 45, 100};
    private static final Sound sportSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sport_sound.mp3"));

    public static SportTower getInstance() {
        if (instance == null) instance = new SportTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, int workers, Set<StudentContainer> currentTargets) {
        if (!currentTargets.isEmpty()) {
            int count = 0;
            for (StudentContainer currentTarget : currentTargets) {
                location.spawnProjectile(projectileTexture, currentTarget);
                if (++count > 1) return COOLDOWN[level - 1];
            }
            sportSound.play();
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
    public int getParticipation(int level, int workers) {
        return PARTICIPATION[level -1] + workers;
    }

    @Override
    public int getCost(int level) {
        return COST[level -1];
    }

    @Override
    public int getRefund(int level) {
        int toReturn = 0;
        while (--level >= 0) {
            toReturn += COST[level];
        }
        return (toReturn * 4) / 5;
    }
}