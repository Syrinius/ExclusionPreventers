package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;

public interface Tower {

    void tick(TowerLocation location);
    Texture getTexture();
}
