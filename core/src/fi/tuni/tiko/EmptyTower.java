package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;

/**
 * Ontouch functionalities are in TowerLocation class
 * Implements the functionalities of a tower location with no tower in it:
 * Visual cue for player to click on the location
 */
public class EmptyTower implements Tower {

    private static EmptyTower instance;
    private static Texture texture;

    public static EmptyTower getInstance() {
        if (instance == null) instance = new EmptyTower();
        return instance;
    }

    @Override
    public void tick(TowerLocation location) {

    }

    @Override
    public Texture getTexture() {
        if (texture == null) texture = new Texture("empty_tower.png");
        return texture;
    }
}
