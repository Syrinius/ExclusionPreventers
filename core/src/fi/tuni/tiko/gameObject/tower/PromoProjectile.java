package fi.tuni.tiko.gameObject.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.utilities.FancyMath;

public class PromoProjectile extends GameObjectSprite {

    public final TowerLocation tower;
    public final PromoData data;
    public final static Texture texture = new Texture("towers/aoe_texture.png");
    private float animationTime = 0;
    private float lifetime = 1.6f;
    private static final Animation<TextureRegion> animation;
    static {
        animation = FancyMath.getAnimationStrip(texture, 96, 96, 3, 0.05f);
    }

    public PromoProjectile(MapPosition position, Map map, TowerLocation tower, PromoData data) {
        super(animation.getKeyFrame(0), position, map);
        this.tower = tower;
        this.data = data;
    }

    public boolean act() {
        if (animationTime > lifetime) {
            destroy();
            return true;
        } else {
            for (StudentContainer currentTarget : data.splashTargets.toArray(new StudentContainer[0])) {
                currentTarget.addParticipation(tower);
            }
        }
        return false;
    }

    @Override
    public TYPE getType() {
        return TYPE.PROJECTILE;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        setRegion(animation.getKeyFrame(animationTime += deltaTime, true));
    }

    @Override
    public void destroy() {
        super.destroy();
        data.promo = null;
    }
}
