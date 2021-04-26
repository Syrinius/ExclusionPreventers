package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.utilities.FancyMath;

public class Student2 implements Student {
    private static final Animation<TextureRegion> walkingAnimation;
    private static final Animation<TextureRegion> cheeringAnimation;
    private static final int REQUIREDPARTICIPATION = 20;
    static {
        Texture texture = new Texture("student_sprites/student2.png");
        walkingAnimation = FancyMath.getAnimationStrip(texture, 21, 21, 4, 0.35f);
        cheeringAnimation = FancyMath.getAnimationStrip(texture, 21, 21, 4, 1f);
    }

    private static final Student2 instance = new Student2();

    public static Student2 getInstance() {
        return instance;
    }

    @Override
    public float getSpeed() {
        return 0.4f;
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
