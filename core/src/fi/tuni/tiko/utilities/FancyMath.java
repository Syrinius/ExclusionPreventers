package fi.tuni.tiko.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class FancyMath {
    public static double angleDegs(Vector2 a, Vector2 b) {
        return Math.atan2(b.y - a.y, b.x - a.x) * MathUtils.radiansToDegrees;
    }

    public static Animation<TextureRegion> getAnimationStrip(Texture texture, int width, int height, int numberOfFrames, float frameDuration) {
        return new Animation<>(frameDuration, getTextureRegionArray(texture, width, height, numberOfFrames));
    }

    public static Array<TextureRegion> getTextureRegionArray(Texture texture, int width, int height, int numberOfFrames) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(new TextureRegion(texture, i * width, 0, width, height));
        }
        return frames;
    }
}
