package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Student1 implements Student {

    private static final Animation<TextureRegion> animation;
    static {
        Texture texture = new Texture("student_sprites/student1.png");
        animation = new Animation<TextureRegion>(2,
                new TextureRegion(texture, 0, 0, 17, 17),
                new TextureRegion(texture, 17, 0, 17, 17));
    }

    private static final Student1 instance = new Student1();

    public static Student1 getInstance() {
        return instance;
    }

    private Student1() {}

    @Override
    public float getSpeed() {
        return 0.08f;
    }

    @Override
    public void tick(StudentContainer container) {

    }

    @Override
    public TextureRegion getTextureRegion(float time) {
        return animation.getKeyFrame(time, true);
    }
}
