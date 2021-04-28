package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.utilities.FancyMath;

public class Student1 implements Student {

    private static final Animation<TextureRegion> walkingAnimation;
    private static final Animation<TextureRegion> cheeringAnimation;
    public final int REQUIREDPARTICIPATION = 16;
    static {
        Texture texture = new Texture("student_sprites/student1.png");
        walkingAnimation = FancyMath.getAnimationStrip(texture, 23, 23, 4, 0.3f);
        cheeringAnimation = FancyMath.getAnimationStrip(texture, 23, 23, 4, 1f);
    }

    private static final Student1 instance = new Student1();

    public static Student1 getInstance() {
        return instance;
    }

    @Override
    public float getSpeed() {
        return 0.6f;
    }

    @Override
    public int getRequiredParticipation() {
        return REQUIREDPARTICIPATION;
    }

    @Override
    public void tick(StudentContainer container) {
    }

    @Override
    public TextureRegion getWalkingTextureRegion(float time) {
        return walkingAnimation.getKeyFrame(time, true);
    }

    @Override
    public TextureRegion getCheeringTextureRegion(float time) {
        return cheeringAnimation.getKeyFrame(time, true);
    }
}
