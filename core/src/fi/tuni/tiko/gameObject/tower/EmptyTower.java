package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * Ontouch functionalities are in TowerLocation class
 * Implements the functionalities of a tower location with no tower in it:
 * Visual cue for player to click on the location
 */
public class EmptyTower implements Tower {

    private static EmptyTower instance;
    private static final Texture texture = new Texture("towers/empty_tower.png");

    public static EmptyTower getInstance() {
        if (instance == null) instance = new EmptyTower();
        return instance;
    }

    @Override
    public float act(TowerLocation location, int level, Set<StudentContainer> currentTargets) {
        return -1;
    }

    @Override
    public Texture getTexture(int level) {
        return texture;
    }

    @Override
    public float getRange(int level) {
        return 0;
    }

    @Override
    public int getParticipation(int level) {
        return 0;
    }
}
