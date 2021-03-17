package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

public class CookingTower implements Tower {

    private static CookingTower instance;
    private static final Texture texture = new Texture("cooking_tower.png");

    public static CookingTower getInstance() {
        if (instance == null) instance = new CookingTower();
        return instance;
    }

    @Override
    public void tick(TowerLocation location) {

    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
