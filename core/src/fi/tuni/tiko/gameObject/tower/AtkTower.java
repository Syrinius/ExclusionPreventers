package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class AtkTower extends Tower {

    private static AtkTower instance;
    private static final Texture[] texture = {
            new Texture("towers/atk.png"),
            new Texture("towers/atk2.png"),
            new Texture("towers/atk3.png")
    };
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {10, 8.5f, 7};
    private static final Sound atkSound = Gdx.audio.newSound(Gdx.files.internal("sounds/computer_sound.mp3"));


    public AtkTower() {
        super(new float[]{12, 15, 20}, new float[]{10, 13, 16}, new int[]{60, 90, 140});
    }

    public static AtkTower getInstance() {
        if (instance == null) instance = new AtkTower();
        return instance;
    }

    @Override
    public float getParticipation(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return PARTICIPATION[data.level -1] + data.workers * 2;
    }

    @Override
    public float act(TowerLocation location, fi.tuni.tiko.gameObject.tower.TowerData data) {
        for (StudentContainer currentTarget : data.currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            atkSound.play(MainMenu.soundVolume);
            return COOLDOWN[data.level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }
}