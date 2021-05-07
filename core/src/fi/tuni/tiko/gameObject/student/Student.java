package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Generic implementation of student game object that all classes of different kinds of students will extend
 */
public interface Student {

    float getSpeed();
    int getRequiredParticipation();
    void tick(StudentContainer container);
    TextureRegion getWalkingTextureRegion(float time);
    TextureRegion getCheeringTextureRegion(float time);
}
