package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.utilities.FancyMath;

public class Student3 implements Student {
    private static final Animation<TextureRegion> animation;
    static {
        Texture texture = new Texture("student_sprites/student3.png");
        animation = FancyMath.getAnimationStrip(texture, 19, 19, 4, 0.25f);
    }

    private static final Student3 instance = new Student3();

    public static Student3 getInstance() {
        return instance;
    }

    @Override
    public float getSpeed() {
        return 0.04f;
    }

    @Override
    public void tick(StudentContainer container) {

    }

    @Override
    public TextureRegion getTextureRegion(float time) {
        return animation.getKeyFrame(time, true);
    }
}