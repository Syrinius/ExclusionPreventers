package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

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
    public float getParticipation(TowerData data) {
        return PARTICIPATION[data.level -1] + data.workers * 2;
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        for (StudentContainer currentTarget : data.currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            atkSound.play();
            return COOLDOWN[data.level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }
}