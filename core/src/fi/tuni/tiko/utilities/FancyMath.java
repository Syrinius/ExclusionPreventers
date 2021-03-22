package fi.tuni.tiko.utilities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class FancyMath {
    public static double angleDegs(Vector2 a, Vector2 b) {
        return Math.atan2(b.y - a.y, b.x - a.x) * MathUtils.radiansToDegrees;
    }
}
