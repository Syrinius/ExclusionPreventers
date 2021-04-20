package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * Specific kinds of towers should all have their own classes that implement this
 */
public abstract class Tower {

    public final float[] RANGE;
    public final int[] PARTICIPATION;
    public final int[] COST;

    public Tower(float[] RANGE, int[] PARTICIPATION, int[] COST) {
        this.RANGE = RANGE;
        this.PARTICIPATION = PARTICIPATION;
        this.COST = COST;
    }

    public TowerData getNewData() {
        return new TowerData();
    }

    public abstract float act(TowerLocation location, TowerData data);

    public abstract Texture getTexture(TowerData data);

    public float getRange(TowerData data) {
        return RANGE[data.level - 1];
    }

    public int getParticipation(TowerData data) {
        return PARTICIPATION[data.level -1] + data.workers;
    }

    public int getCost(TowerData data) {
        return COST[data.level -1];
    }

    public int getRefund(TowerData data) {
        int toReturn = 0;
        while (--data.level >= 0) {
            toReturn += COST[data.level];
        }
        return (toReturn * 4) / 5;
    }

    public void checkTarget(TowerLocation location, StudentContainer target, TowerData data) {

    }

    public void removeTarget(StudentContainer target, TowerData data) {

    }
}
