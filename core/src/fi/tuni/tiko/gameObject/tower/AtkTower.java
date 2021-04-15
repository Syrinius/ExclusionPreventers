package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class AtkTower implements Tower {

    private static AtkTower instance;
    private static final Texture texture = new Texture("towers/atk.png");
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {5, 4, 3};
    private static final float[] RANGE = {7, 8, 9};
    private static final int[] PARTICIPATION = {4, 6, 8};
    private static final int[] COST = {20, 40, 80};
    private static final Sound atkSound = Gdx.audio.newSound(Gdx.files.internal("sounds/computer_sound.mp3"));

    public static AtkTower getInstance() {
        if (instance == null) instance = new AtkTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, int workers, Set<StudentContainer> currentTargets) {
        for (StudentContainer currentTarget : currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            atkSound.play();
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