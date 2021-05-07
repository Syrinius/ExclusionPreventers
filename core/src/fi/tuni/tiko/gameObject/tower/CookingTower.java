package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class CookingTower extends Tower {

    private static CookingTower instance;
    private static final Texture[] texture = {
            new Texture("towers/cook.png"),
            new Texture("towers/cook2.png"),
            new Texture("towers/cook3.png")
    };
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {5, 4, 3};
    private static final Sound cookSound = Gdx.audio.newSound(Gdx.files.internal("sounds/cook_sound.mp3"));

    /**
     * RANGE, PARTICIPATION, COST
     */
    public CookingTower() {
        super(new float[]{7, 8, 9}, new float[]{4, 6, 8}, new int[]{20, 40, 80});
    }

    public static CookingTower getInstance() {
        if (instance == null) instance = new CookingTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, fi.tuni.tiko.gameObject.tower.TowerData data) {
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
