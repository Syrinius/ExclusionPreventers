package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;

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
