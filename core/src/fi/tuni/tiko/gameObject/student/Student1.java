package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Student1 implements Student {

    private static final Animation<TextureRegion> animation;
    static {
        Texture texture = new Texture("student_sprites/student1.png");
        animation = new Animation<TextureRegion>(0.3f,
                new TextureRegion(texture, 0, 0, 30, 30),
                new TextureRegion(texture, 30, 0, 30, 30),
                new TextureRegion(texture, 60, 0, 30, 30),
                new TextureRegion(texture, 90, 0, 30, 30));
    }

    private static final Student1 instance = new Student1();

    public static Student1 getInstance() {
        return instance;
    }

    private Student1() {}

    @Override
    public float getSpeed() {
        return 0.03f;
    }

    @Override
    public void tick(StudentContainer container) {

    }

    @Override
    public TextureRegion getTextureRegion(float time) {
        return animation.getKeyFrame(time, true);
    }
}
