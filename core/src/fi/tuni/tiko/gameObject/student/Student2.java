package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.utilities.FancyMath;

public class Student2 implements Student {
    private static final Animation<TextureRegion> animation;
    static {
        Texture texture = new Texture("student_sprites/student2.png");
        animation = FancyMath.getAnimationStrip(texture, 21, 21, 4, 0.35f);
    }

    private static final Student2 instance = new Student2();

    public static Student2 getInstance() {
        return instance;
    }

    @Override
    public float getSpeed() {
        return 0.02f;
    }

    @Override
    public void tick(StudentContainer container) {

    }

    @Override
    public TextureRegion getTextureRegion(float time) {
        return animation.getKeyFrame(time, true);
    }
}
