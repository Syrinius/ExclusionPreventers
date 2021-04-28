package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class MusicTower extends Tower {

    private static MusicTower instance;
    private static final Texture[] texture = {
            new Texture("towers/music.png"),
            new Texture("towers/music2.png"),
            new Texture("towers/music3.png")
    };
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {10, 8, 6};
    private static final float[] RANGE = {6, 7, 8};
    private static final int[] PARTICIPATION = {3, 4, 5};
    private static final int[] COST = {40, 60, 100};
    private static final Sound musicSound = Gdx.audio.newSound(Gdx.files.internal("sounds/music_sound.mp3"));


    public MusicTower() {
        super(new float[]{6, 7, 8}, new int[]{3, 4, 5}, new int[]{40, 60, 100});
    }

    public static MusicTower getInstance() {
        if (instance == null) instance = new MusicTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        if (!data.currentTargets.isEmpty()) {
            for (StudentContainer currentTarget : data.currentTargets) {
                currentTarget.addParticipation(location);
            }
            location.spawnAOEVisual();
            musicSound.play(MainMenu.soundVolume);
            return COOLDOWN[data.level - 1];
        } else return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }
}