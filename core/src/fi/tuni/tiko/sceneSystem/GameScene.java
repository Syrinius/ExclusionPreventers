package fi.tuni.tiko.sceneSystem;

import fi.tuni.tiko.MapManager;

public class GameScene extends Scene {

    @Override
    void render() {
        super.render();
        MapManager.render();
    }
}