package fi.tuni.tiko.gameObject.tower;

import java.util.HashSet;
import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class TowerData {

    public int level = 0;
    public int workers = 0;
    public float cooldown = 0;
    public Set<StudentContainer> currentTargets = new HashSet<>();
}
