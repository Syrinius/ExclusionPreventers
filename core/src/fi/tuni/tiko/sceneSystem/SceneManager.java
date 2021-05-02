package fi.tuni.tiko.sceneSystem;

/**
 * Handles changing between scenes, such as between different menus
 * Each screen should have it's own specific class
 */
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
