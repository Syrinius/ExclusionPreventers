package fi.tuni.tiko.gameObject.tower;

import java.util.HashSet;
import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

/**
 * The additional non-static variables that all instance of PromoTower must hold
 */
public class PromoData extends TowerData {
    PromoProjectile promo;
    Set<StudentContainer> splashTargets = new HashSet<>();
}
