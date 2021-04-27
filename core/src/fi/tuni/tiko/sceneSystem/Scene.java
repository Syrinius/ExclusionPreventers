package fi.tuni.tiko.sceneSystem;

import fi.tuni.tiko.hud.HudElementManager;

/**
 * Implementation for whole screens to add all the elements in each screen
 * Holds reference to the HudElementManager for that scene
 * All classes for different kinds of screens should extend this
 */
public abstract class Scene {

    public HudElementManager hudElementManager;

    public Scene() {
        hudElementManager = new HudElementManager();
    }

    void render() {
        hudElementManager.Render();
    }

    public void dispose() {
        hudElementManager.Dispose();
    }
}
