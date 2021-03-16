package fi.tuni.tiko;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * All objects on the maps should implement this
 */
public interface GameObject {

    enum TYPE { STUDENT, TOWER, PROJECTILE }

    TYPE getType();
    void onTick(float deltaTime);
    void render(SpriteBatch renderer);
    void destroy();
}
