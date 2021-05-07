package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.student.StudentContainer;

public class PromoTower extends Tower {

    private static PromoTower instance;
    private static final Texture[] texture = {
            new Texture("towers/promo.png"),
            new Texture("towers/promo2.png"),
            new Texture("towers/promo3.png")
    };
    private static final float[] COOLDOWN = {5, 4, 3};
    private static final float[] PROMO_COOLDOWN = {0.3f, 0.25f, 0.2f};
    private static final int SPLASH_RANGE = 2;

    /**
     * RANGE, PARTICIPATION, COST
     */
    public PromoTower() {
        super(new float[]{18, 24, 40}, new float[]{1, 1, 1}, new int[]{40, 60, 100});
    }

    public static PromoTower getInstance() {
        if (instance == null) instance = new PromoTower();
        return instance;
    }

    @Override
    public TowerData getNewData() {
        return new PromoData();
    }

    @Override
    public float act(TowerLocation location, TowerData data) {
        PromoData promoData = (PromoData)data;
        if(promoData.promo != null) {
            return promoData.promo.act() ? (COOLDOWN[data.level - 1] * 8) / (8 + data.workers) : PROMO_COOLDOWN[data.level - 1];
        } else {
            for (fi.tuni.tiko.gameObject.student.StudentContainer currentTarget : data.currentTargets) {
                promoData.promo = new PromoProjectile(new MapPosition(currentTarget.getTileCenterX(), currentTarget.getTileCenterY()), location.map, location, promoData);
                location.map.getGameObjectManager().invalidate();
                return 0;
            }
            return 0;
        }
    }

    @Override
    public float getParticipation(TowerData data) {
        return PARTICIPATION[data.level -1];
    }

    @Override
    public Texture getTexture(TowerData data) {
        return texture[data.level - 1];
    }

    @Override
    public void checkTarget(TowerLocation location, fi.tuni.tiko.gameObject.student.StudentContainer target, TowerData data) {
        if (((PromoData)data).promo == null) return;
        MapPosition position = ((PromoData)data).promo.getPosition();
        if(position.isInside(target.getTileCenterX(), target.getTileCenterY(), SPLASH_RANGE)) {
            ((PromoData)data).splashTargets.add(target);
        } else ((PromoData)data).splashTargets.remove(target);
    }

    @Override
    public void removeTarget(StudentContainer target, TowerData data) {
        ((PromoData)data).splashTargets.remove(target);
    }
}
