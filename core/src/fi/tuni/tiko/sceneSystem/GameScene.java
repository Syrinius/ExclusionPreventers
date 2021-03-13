package fi.tuni.tiko.sceneSystem;

import fi.tuni.tiko.GameObjectManager;
import fi.tuni.tiko.MapManager;

public class GameScene extends Scene {

    public GameObjectManager gameObjectManager = new GameObjectManager();

    @Override
    void render() {
        super.render();
        MapManager.render();
        gameObjectManager.render();
    }

    @Override
    void dispose() {
        super.dispose();
        gameObjectManager.dispose();
    }
}