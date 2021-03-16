package fi.tuni.tiko.coordinateSystem;

/**
 * Coordinate system for elements that adhere to actual screen pixels, such as player input
 * Not to be used for implemented elements, use the other two coordinate systems instead
 * Includes methods for converting between different coordinate systems
 */
public class ScreenPosition {
    public static int WIDTH;
    public static int HEIGHT;

    public int x;
    public int y;

    public ScreenPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapPosition ToMapPosition() {
        return new MapPosition(
                (x * MapPosition.VIEWPORT_WIDTH / WIDTH) + (MapPosition.camera.position.x - MapPosition.VIEWPORT_WIDTH / 2),
                ((HEIGHT - y) * MapPosition.VIEWPORT_HEIGHT/ HEIGHT) + (MapPosition.camera.position.y - MapPosition.VIEWPORT_HEIGHT / 2));
    }

    public MenuPosition ToMenuPosition() {
        return new MenuPosition(x*MenuPosition.WIDTH / WIDTH, (HEIGHT - y) * MenuPosition.HEIGHT / HEIGHT);
    }
}
