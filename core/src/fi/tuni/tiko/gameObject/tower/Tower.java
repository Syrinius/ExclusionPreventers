package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * Specific kinds of towers should all have their own classes that implement this
 */
public abstract class Tower {

    public final float[] RANGE;
    public final float[] PARTICIPATION;
    public final int[] COST;

    public Tower(float[] RANGE, float[] PARTICIPATION, int[] COST) {
        this.RANGE = RANGE;
        this.PARTICIPATION = PARTICIPATION;
        this.COST = COST;
    }

    public fi.tuni.tiko.gameObject.tower.TowerData getNewData() {
        return new fi.tuni.tiko.gameObject.tower.TowerData();
    }

    public abstract float act(TowerLocation location, fi.tuni.tiko.gameObject.tower.TowerData data);

    public abstract Texture getTexture(fi.tuni.tiko.gameObject.tower.TowerData data);

    public float getRange(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return RANGE[data.level - 1];
    }

    public float getParticipation(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return PARTICIPATION[data.level -1] + data.workers;
    }

    public int getCost(fi.tuni.tiko.gameObject.tower.TowerData data) {
        return COST[data.level -1];
    }

    public int getRefund(fi.tuni.tiko.gameObject.tower.TowerData data) {
        int toReturn = 0;
        while (--data.level >= 0) {
            toReturn += COST[data.level];
        }
        return (toReturn * 4) / 5;
    }

    public void checkTarget(TowerLocation location, StudentContainer target, fi.tuni.tiko.gameObject.tower.TowerData data) {

    }

    public void removeTarget(StudentContainer target, TowerData data) {

    }
}
