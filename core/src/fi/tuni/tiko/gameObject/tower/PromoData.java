package fi.tuni.tiko.gameObject.tower;

import java.util.HashSet;
import java.util.Set;

import fi.tuni.tiko.gameObject.student.StudentContainer;

public class PromoData extends TowerData {
    PromoProjectile promo;
    Set<StudentContainer> splashTargets = new HashSet<>();
}
