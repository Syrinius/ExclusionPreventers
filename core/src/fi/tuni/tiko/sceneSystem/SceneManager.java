package fi.tuni.tiko.sceneSystem;

import fi.tuni.tiko.MainGame;

public class SceneManager {

    private static Scene activeScene;

    public static void SetActiveScene(Scene scene) {
        if (activeScene == scene) return;
        if (activeScene != null) activeScene.dispose();
        activeScene = scene;

    }

    public static Scene GetActiveScene() {
        return activeScene;
    }

    public static void Render(){
        if (activeScene == null) return;
        activeScene.render();
    }
}
