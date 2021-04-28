package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class SupportTower extends Tower {

    private static SupportTower instance;
    private static final Texture[] texture = {
            new Texture("towers/support.png"),
            new Texture("towers/support2.png"),
            new Texture("towers/support3.png")
    };
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {7, 6, 5};
    private static final Sound cookSound = Gdx.audio.newSound(Gdx.files.internal("sounds/cook_sound.mp3"));

    public SupportTower() {
        super(new float[]{7, 8, 9}, new int[]{2, 3, 4}, new int[]{40, 70, 100});
    }

    public static SupportTower getInstance() {
        if (instance == null) instance = new SupportTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        for (StudentContainer currentTarget : data.currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            cookSound.play(MainMenu.soundVolume);
            return COOLDOWN[data.level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }
}
