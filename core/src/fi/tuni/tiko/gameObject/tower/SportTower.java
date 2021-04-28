package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class SportTower extends Tower {

    private static SportTower instance;
    private static final Texture[] texture = {
            new Texture("towers/sport.png"),
            new Texture("towers/sport2.png"),
            new Texture("towers/sport3.png")
    };
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {4, 3, 2};
    private static final Sound sportSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sport_sound.mp3"));


    public SportTower() {
        super(new float[]{5, 6, 7}, new int[]{3, 4, 5}, new int[]{25, 45, 100});
    }

    public static SportTower getInstance() {
        if (instance == null) instance = new SportTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        if (!data.currentTargets.isEmpty()) {
            int count = 0;
            for (StudentContainer currentTarget : data.currentTargets) {
                location.spawnProjectile(projectileTexture, currentTarget);
                if (++count > 1) return COOLDOWN[data.level - 1];
            }
            sportSound.play(MainMenu.soundVolume);
            return COOLDOWN[data.level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }
}