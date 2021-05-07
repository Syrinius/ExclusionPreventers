package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

/**
 * Ontouch functionalities are in TowerLocation class
 * Implements the functionalities of a tower location with no tower in it:
 * Visual cue for player to click on the location
 */
public class EmptyTower extends Tower {

    private static EmptyTower instance;
    private static final Texture texture = new Texture("towers/empty_tower.png");

    /**
     * RANGE, PARTICIPATION, COST
     */
    public EmptyTower() {
        super(new float[]{0}, new float[]{0}, new int[]{0});
    }

    public static EmptyTower getInstance() {
        if (instance == null) instance = new EmptyTower();
        return instance;
    }

    @Override
    public fi.tuni.tiko.gameObject.tower.TowerData getNewData() {
        return new fi.tuni.tiko.gameObject.tower.TowerData();
    }

    @Override
    public float act(TowerLocation location, fi.tuni.tiko.gameObject.tower.TowerData data) {
        return -1;
    }

    @Override
    public Texture getTexture(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return texture;
    }

    @Override
    public float getRange(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return 0;
    }

    @Override
    public float getParticipation(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return 0;
    }

    @Override
    public int getCost(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return 0;
    }

    @Override
    public int getRefund(TowerData data) {
        return 0;
    }
}
