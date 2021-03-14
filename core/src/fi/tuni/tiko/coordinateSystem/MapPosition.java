package fi.tuni.tiko.coordinateSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class MapPosition extends Vector2 {

    public static final float SCALE = 1/16f;
    public static final float VIEWPORT_WIDTH = 40;
    public static final float VIEWPORT_HEIGHT = 20;
    public static final int SIZE_X = 60;
    public static final int SIZE_Y = 30;
    public static OrthographicCamera camera;

    public static void Initialize() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    public MapPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public ScreenPosition ToScreenPosition() {
        return new ScreenPosition(
                (int)((x - (camera.position.x - VIEWPORT_WIDTH / 2)) * ScreenPosition.WIDTH / VIEWPORT_WIDTH),
                ScreenPosition.HEIGHT - (int)((y - (camera.position.y - VIEWPORT_HEIGHT / 2)) * ScreenPosition.HEIGHT /VIEWPORT_HEIGHT));
    }

    public MenuPosition ToMenuPosition() {
        return new MenuPosition(
                ((x - (camera.position.x - VIEWPORT_WIDTH / 2)) * MenuPosition.WIDTH / VIEWPORT_WIDTH),
                ((y - (camera.position.y - VIEWPORT_HEIGHT / 2)) * MenuPosition.HEIGHT /VIEWPORT_HEIGHT));
    }
}
