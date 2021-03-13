package fi.tuni.tiko.sceneSystem;

import fi.tuni.tiko.hud.HudElementManager;

public abstract class Scene {

    public HudElementManager hudElementManager;

    public Scene() {
        hudElementManager = new HudElementManager();
    }

    void render() {
        hudElementManager.Render();
    }

    void dispose() {
        hudElementManager.Dispose();
    }
}
