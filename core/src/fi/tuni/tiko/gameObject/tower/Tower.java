package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * Specific kinds of towers should all have their own classes that implement this
 */
public interface Tower {

    float act(TowerLocation location, int level, Set<StudentContainer> currentTargets);
    Texture getTexture(int level);
    float getRange(int level);
}
