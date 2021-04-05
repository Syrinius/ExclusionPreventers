package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.utilities.FancyMath;

public class Student3 implements Student {
    private static final Animation<TextureRegion> walkingAnimation;
    private static final Animation<TextureRegion> cheeringAnimation;
    private static final int REQUIREDPARTICIPATION = 8;
    static {
        Texture texture = new Texture("student_sprites/student3.png");
        walkingAnimation = FancyMath.getAnimationStrip(texture, 19, 19, 4, 0.25f);
        cheeringAnimation = FancyMath.getAnimationStrip(texture, 19, 19, 4, 1f);
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