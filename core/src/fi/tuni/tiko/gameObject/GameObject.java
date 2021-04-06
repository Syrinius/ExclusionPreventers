package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * All objects on the maps should implement this
 * Or extend a class that implements this
 */
public interface GameObject {

    enum TYPE { STUDENT, TOWER, PROJECTILE, SYSTEM, CINEMATIC }

    TYPE getType();
    void onTick(float deltaTime, boolean revalidate);
    void render(SpriteBatch renderer);
    void destroy();
    boolean isValid();
}
