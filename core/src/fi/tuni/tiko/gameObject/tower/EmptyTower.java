package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * Ontouch functionalities are in TowerLocation class
 * Implements the functionalities of a tower location with no tower in it:
 * Visual cue for player to click on the location
 */
public class EmptyTower extends Tower {

    private static EmptyTower instance;
    private static final Texture texture = new Texture("towers/empty_tower.png");

    public EmptyTower() {
        super(new float[]{0}, new int[]{0}, new int[]{0});
    }

    public static EmptyTower getInstance() {
        if (instance == null) instance = new EmptyTower();
        return instance;
    }

    @Override
    public TowerData getNewData() {
        return new TowerData();
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        return -1;
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture;
    }

    @Override
    public float getRange(TowerData data) {
        return 0;
    }

    @Override
    public int getParticipation(TowerData data) {
        return 0;
    }

    @Override
    public int getCost(TowerData data) {
        return 0;
    }

    @Override
    public int getRefund(TowerData data) {
        return 0;
    }
}
