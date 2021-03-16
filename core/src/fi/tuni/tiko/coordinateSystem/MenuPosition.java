package fi.tuni.tiko.coordinateSystem;

import com.badlogic.gdx.math.Vector2;

/**
 * Coordinate system for all hud and menu elements
 * Units arbitrarily chosen to be used for all menus
 * Includes methods for converting between different coordinate systems
 */
public class MenuPosition extends Vector2 {
    public static final float WIDTH = 400;
    public static final float HEIGHT = 200;

    public MenuPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MapPosition ToMapPosition() {
        return new MapPosition(
                (x*MapPosition.VIEWPORT_WIDTH / WIDTH) + (MapPosition.camera.position.x - MapPosition.VIEWPORT_WIDTH / 2),
                (y*MapPosition.VIEWPORT_HEIGHT/ HEIGHT) + (MapPosition.camera.position.y - MapPosition.VIEWPORT_HEIGHT / 2));
    }

    public ScreenPosition ToScreenPosition() {
        return new ScreenPosition((int)(x*ScreenPosition.WIDTH / WIDTH), ScreenPosition.HEIGHT - (int)(y*ScreenPosition.HEIGHT / HEIGHT));
    }
}
