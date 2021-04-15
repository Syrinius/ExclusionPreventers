package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.utilities.FancyMath;

public class AOEVisual extends GameObjectSprite {
    private float animationTime = 0;
    private float lifetime = 0.5f;
    private static final Texture texture = new Texture("towers/aoe_texture.png");
    private static final Animation<TextureRegion> animation;
    static {
        animation = FancyMath.getAnimationStrip(texture, 192, 192, 3, 0.15f);
    }

    public AOEVisual(MapPosition position, Map map) {
        super(animation.getKeyFrame(0), position, map);
    }

    @Override
    public TYPE getType() {
        return TYPE.CINEMATIC;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if (animationTime > lifetime) destroy();
        setRegion(animation.getKeyFrame(animationTime += deltaTime, false));
    }
}
