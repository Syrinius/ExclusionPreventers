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

    public static boolean isInside(float x0, float x1, float y0, float y1, float range) {
        return Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2) <= Math.pow(range, 2);
    }
}
