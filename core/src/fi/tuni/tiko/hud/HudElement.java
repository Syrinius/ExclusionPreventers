package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * All menu and hud elements should implement this
 * Or extend a class that implements this
 */
public interface HudElement {

    void render(SpriteBatch batch);
    void dispose();
}
