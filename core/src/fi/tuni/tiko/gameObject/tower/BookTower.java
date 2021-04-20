package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class BookTower extends Tower {

    private static BookTower instance;
    private static final Texture texture = new Texture("towers/book.png");
    private static final Texture projectileTexture = new Texture("towers/heart.png");
    private static final float[] COOLDOWN = {5, 4, 3};
    private static final Sound bookSound = Gdx.audio.newSound(Gdx.files.internal("sounds/book_sound.mp3"));

    public BookTower() {
        super(new float[]{7, 8, 9}, new int[]{4, 6, 8}, new int[]{20, 40, 80});
    }

    public static BookTower getInstance() {
        if (instance == null) instance = new BookTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        for (StudentContainer currentTarget : data.currentTargets) {
            location.spawnProjectile(projectileTexture, currentTarget);
            bookSound.play();
            return COOLDOWN[data.level - 1];
        }
        return 0;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture;
    }
}