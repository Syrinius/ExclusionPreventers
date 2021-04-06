package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Student {

    float getSpeed();
    int getRequiredParticipation();
    void tick(StudentContainer container);
    TextureRegion getWalkingTextureRegion(float time);
    TextureRegion getCheeringTextureRegion(float time);
}
