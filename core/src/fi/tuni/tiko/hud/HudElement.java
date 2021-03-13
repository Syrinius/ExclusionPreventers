package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface HudElement {

    void render(SpriteBatch batch);
    void dispose();
}
