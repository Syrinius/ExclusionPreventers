package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class MusicTower implements Tower {

    private static MusicTower instance;
    private static final Texture texture = new Texture("towers/note_tower.png");
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {10, 8, 6};
    private static final float[] RANGE = {6, 7, 8};
    private static final int[] PARTICIPATION = {3, 4, 5};
    private static final int[] COST = {40, 60, 100};
    private static final Sound musicSound = Gdx.audio.newSound(Gdx.files.internal("sounds/music_sound.mp3"));

    public static MusicTower getInstance() {
        if (instance == null) instance = new MusicTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, int workers, Set<StudentContainer> currentTargets) {
        if (!currentTargets.isEmpty()) {
            for (StudentContainer currentTarget : currentTargets) {
                currentTarget.addParticipation(location);
            }
            location.spawnAOEVisual();
            musicSound.play();
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