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

    public fi.tuni.tiko.coordinateSystem.MapPosition ToMapPosition() {
        return new fi.tuni.tiko.coordinateSystem.MapPosition(
                (x * fi.tuni.tiko.coordinateSystem.MapPosition.VIEWPORT_WIDTH / WIDTH) + (fi.tuni.tiko.coordinateSystem.MapPosition.camera.position.x - fi.tuni.tiko.coordinateSystem.MapPosition.VIEWPORT_WIDTH / 2),
                ((HEIGHT - y) * fi.tuni.tiko.coordinateSystem.MapPosition.VIEWPORT_HEIGHT/ HEIGHT) + (fi.tuni.tiko.coordinateSystem.MapPosition.camera.position.y - MapPosition.VIEWPORT_HEIGHT / 2));
    }

    public fi.tuni.tiko.coordinateSystem.MenuPosition ToMenuPosition() {
        return new fi.tuni.tiko.coordinateSystem.MenuPosition(x* fi.tuni.tiko.coordinateSystem.MenuPosition.WIDTH / WIDTH, (HEIGHT - y) * MenuPosition.HEIGHT / HEIGHT);
    }
}
