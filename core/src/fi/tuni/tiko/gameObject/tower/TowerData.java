package fi.tuni.tiko.gameObject.tower;

import java.util.HashSet;
import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * Contains all the non-static variables that all instances of towers must have
 * Avoids redundantly writing individual variables as arguments
 */
public class TowerData {

    public int level = 1;
    public int workers = 0;
    public float cooldown = 0;
    public Set<StudentContainer> currentTargets = new HashSet<>();
}
