package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

/**
 * Specific kinds of towers should all have their own classes that implement this
 */
public interface Tower {

    void tick(TowerLocation location);
    Texture getTexture();
}
