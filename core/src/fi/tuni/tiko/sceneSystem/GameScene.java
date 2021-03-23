package fi.tuni.tiko.sceneSystem;


import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.map.MapManager;
import fi.tuni.tiko.utilities.Action;

/**
 * Implementation for map Scenes
 */
public class GameScene extends Scene {

    static final Texture resumeTexture = new Texture("start_wave_button.png");
    static final Texture pauseTexture = new Texture("pause_wave_button.png");

    private final Button playPauseButton;

    public GameScene() {
        playPauseButton = new Button(resumeTexture, new MenuPosition(MenuPosition.WIDTH - 25, MenuPosition.HEIGHT - 25), 25, new Action() {
            @Override
            public void run() {
                Map map = MapManager.getSelectedMap();
                if (map.isPaused()) {
                    playPauseButton.setTexture(pauseTexture);
                    MapManager.getSelectedMap().resume();
                } else {
                    playPauseButton.setTexture(resumeTexture);
                    MapManager.getSelectedMap().pause();
                }
            }
        });
        hudElementManager.AddHudElement(playPauseButton);
    }

    @Override
    void render() {
        MapManager.render();
        super.render();
    }
}