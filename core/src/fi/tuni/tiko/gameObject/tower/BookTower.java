package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class BookTower extends Tower {

    private static BookTower instance;
    private static final Texture[] texture = {
            new Texture("towers/book.png"),
            new Texture("towers/book2.png"),
            new Texture("towers/book3.png")
    };
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {3.5f, 3, 2.5f};
    private static final Sound bookSound = Gdx.audio.newSound(Gdx.files.internal("sounds/book_sound.mp3"));

    /**
     * RANGE, PARTICIPATION, COST
     */
    public BookTower() {
        super(new float[]{6, 7, 8}, new float[]{3, 4, 5}, new int[]{25, 50, 80});
    }

    public static BookTower getInstance() {
        if (instance == null) instance = new BookTower();
        return instance;
    }

    @Override
    public float getParticipation(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return PARTICIPATION[data.level -1] + data.workers / 2f;
    }

    @Override
    public float act(TowerLocation location, fi.tuni.tiko.gameObject.tower.TowerData data) {
        for (StudentContainer currentTarget : data.currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            bookSound.play(MainMenu.soundVolume);
            return COOLDOWN[data.level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }
}