package fi.tuni.tiko.sceneSystem;

import fi.tuni.tiko.MapManager;

/**
 * Implementation for map Scenes
 */
public class GameScene extends Scene {

    @Override
    void render() {
        super.render();
        MapManager.render();
    }
}